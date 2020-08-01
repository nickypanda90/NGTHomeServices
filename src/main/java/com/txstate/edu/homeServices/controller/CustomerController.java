package com.txstate.edu.homeServices.controller;


import com.txstate.edu.homeServices.model.CustomerRegistration;
import com.txstate.edu.homeServices.model.LoginDetail;
import com.txstate.edu.homeServices.repository.AuthenticRepository;
import com.txstate.edu.homeServices.repository.CustomerRepository;
import com.txstate.edu.homeServices.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.mail.SimpleMailMessage;

@RestController
@RequestMapping("/customer/api")//After deployment on web take the IP port(e.g Amazon)
public class CustomerController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    AuthenticRepository authenticRepository;

    @Autowired
    private EmailService emailService;


    @PostMapping("/signup")
    public CustomerRegistration signupCustomer(@Valid @RequestBody CustomerRegistration customerregistration, HttpServletRequest request) {
        log.debug("Registering customer {}", customerregistration);
        customerregistration.setRole_id("customer");
        customerRepository.save(customerregistration);

        String token = UUID.randomUUID().toString();
        String appUrl = request.getScheme() + "://" + request.getServerName() + ":8080";
        SimpleMailMessage registrationmsg = new SimpleMailMessage();
        registrationmsg.setFrom("support@demo.com");
        registrationmsg.setTo(customerregistration.getEmail_id());
        registrationmsg.setSubject("Register Successfully");
        registrationmsg.setText("You have successfully registered:\n" + appUrl
                + "/pages/registration/login-page.html?myToken=" + token);
        emailService.sendEmail(registrationmsg);

        return customerregistration;
    }

    @PostMapping("/login")
    public LoginDetail login(@Valid @RequestBody CustomerRegistration customerregistration, HttpServletRequest request) {

        LoginDetail user = new LoginDetail();
        request.getSession().setAttribute("USER_INFO", user);

        LoginDetail name = customerRepository.findByEmail_idaAndPassword(customerregistration.getEmail_id(), customerregistration.getPassword());

        if (name!=null &&  !name.getName().isEmpty()) {
            user.setRole_id(name.getRole_id());
            user.setName(name.getName());
            user.setAuthenticated(true);
            user.setCustomer_Id(name.getCustomer_Id());
            customerregistration.setName(user.getName());
            customerregistration.setCustomer_Id(user.getCustomer_Id());
            request.getSession().setAttribute("USER_DETAILS_EXPANDED", customerregistration);

        } else
            user.setAuthenticated(false);

        return user;

    }

    @PostMapping("/authenticateemailid")
    public boolean authenticEmailid(@Valid @RequestBody CustomerRegistration customerregistration, HttpServletRequest request) {
        boolean isValid = false;
        if (authenticRepository.authenticate(customerregistration.getEmail_id()).size() > 0) {

            List<CustomerRegistration> customerRegistrations = authenticRepository.authenticate(customerregistration.getEmail_id());
            String token = UUID.randomUUID().toString();
            customerRegistrations.get(0).setReset_token(token);
            customerRegistrations.get(0).setToken_ts(new Date());
            customerRepository.save(customerRegistrations.get(0));
            isValid = true;
            String appUrl = request.getScheme() + "://" + request.getServerName() + ":8080";

            SimpleMailMessage passwordresetEmail = new SimpleMailMessage();
            passwordresetEmail.setFrom("support@demo.com");
            passwordresetEmail.setTo(customerregistration.getEmail_id());
            passwordresetEmail.setSubject("Password Request");
            passwordresetEmail.setText("Please click on the link below:\n" + appUrl
                    + "/pages/reset-password/forgot-password.html?myToken=" + token);
            emailService.sendEmail(passwordresetEmail);
        }
        return isValid;


    }


    @Transactional
    @PostMapping("/forgotpass")
    public CustomerRegistration forgotpass(@Valid @RequestBody CustomerRegistration customerregistration, HttpServletRequest request) {

        //please change the port number as per your localhost
        CustomerRegistration existingCustomer = authenticRepository.getUserByToken(customerregistration.getReset_token());
        if (existingCustomer != null && checkExpiry(existingCustomer.getToken_ts())) {
            existingCustomer.setPassword(customerregistration.getPassword());
            customerRepository.save(existingCustomer);
        }
        else
            existingCustomer= new CustomerRegistration();
        return existingCustomer;
    }


    private boolean checkExpiry(Date tokenTime) {
        return tokenTime.after(new Date(System.currentTimeMillis() - 15 * 60 * 1000)) && tokenTime.before(new Date());
    }


}