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
    public UserAuthenticationResponse login( @Valid @RequestBody CustomerRegistration customerregistration,HttpServletRequest request) {
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
    public boolean authenticEmailid(@Valid @RequestBody CustomerRegistration customerregistration) {
        if (authenticRepository.authenticate(customerregistration.getEmail_id()).size() > 0)
            return true;
        else
            return false;

    }

    @Transactional
    @PostMapping("/forgotpass")
    public  void updatepass(@Valid @RequestBody CustomerRegistration customerregistration) {

       authenticRepository.save(customerregistration.getEmail_id(),customerregistration.getPassword());
    }

    @PostMapping("/forgotusername")
    public String forgotusername(@Valid @RequestBody CustomerRegistration customerregistration, HttpServletRequest request) {


        String name;


//please change the port number as per your localhost
        String appUrl = request.getScheme() + "://" + request.getServerName()+":8080";
        SimpleMailMessage forgotusername = new SimpleMailMessage();
        forgotusername.setFrom("support@demo.com");
        forgotusername.setTo(customerregistration.getEmail_id());
        forgotusername.setSubject("Username Request");
        forgotusername.setText("Please click on the link below:\n" + appUrl
                + "/customer/api/forgotusername");

        name=  customerRepository.findByEmail_id(customerregistration.getEmail_id());

        emailService.sendEmail(forgotusername);

        return name;
    }


}