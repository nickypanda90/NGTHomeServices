package com.txstate.edu.homeServices.controller;


import com.txstate.edu.homeServices.model.CustomerFeedback;
import com.txstate.edu.homeServices.model.CustomerRegistration;
import com.txstate.edu.homeServices.model.UserAuthenticationResponse;
import com.txstate.edu.homeServices.repository.AuthenticRepository;
import com.txstate.edu.homeServices.repository.CustomerRepository;
import com.txstate.edu.homeServices.service.EmailService;
import org.jetbrains.annotations.NotNull;
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
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    AuthenticRepository authenticRepository;

    @Autowired
    private EmailService emailService;


    @PostMapping("/signup")
    public CustomerRegistration signupCustomer(@Valid @RequestBody CustomerRegistration customerregistration, HttpServletRequest request) {
        CustomerRegistration temp = customerregistration;
        try {
            customerregistration.setRole_id("customer");
            temp = customerRepository.save(customerregistration);

            // request.getSession().setAttribute("USER_INFO", temp);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return temp;
    }

    @PostMapping("/login")
    public UserAuthenticationResponse login(@Valid @RequestBody CustomerRegistration customerregistration, HttpServletRequest request) {
        String name;


        UserAuthenticationResponse user = new UserAuthenticationResponse();
        request.getSession().setAttribute("USER_INFO", user);

        name = customerRepository.findByEmail_idaAndPassword(customerregistration.getEmail_id(), customerregistration.getPassword());

        if (name != null) {
            user.setName(name);
            user.setAuthenticated(true);

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
            passwordresetEmail.setSubject("Username Request");
            passwordresetEmail.setText("Please click on the link below:\n" + appUrl
                    + "/examples/forgot-password.html?myToken=" + token);
            emailService.sendEmail(passwordresetEmail);
        }
        return isValid;


    }

//    @Transactional
//    @PostMapping("/forgotpass")
//    public  void updatepass(@Valid @RequestBody CustomerRegistration customerregistration) {
//
//       authenticRepository.save(customerregistration.getEmail_id(),customerregistration.getPassword());
//    }

    @Transactional
    @PostMapping("/forgotpass")
    public CustomerRegistration forgotusername(@Valid @RequestBody CustomerRegistration customerregistration, HttpServletRequest request) {

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