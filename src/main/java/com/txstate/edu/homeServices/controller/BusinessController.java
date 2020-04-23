package com.txstate.edu.homeServices.controller;

import com.txstate.edu.homeServices.model.CustomerRegistration;
import com.txstate.edu.homeServices.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/business/api")
public class BusinessController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CustomerRepository repository;

    @PostMapping(value = "/signup", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public CustomerRegistration registerBusiness(@Valid @RequestBody CustomerRegistration contractor) {
        log.debug("Registering business for {}", contractor);

        String token = UUID.randomUUID().toString();
//        String appUrl = request.getScheme() + "://" + request.getServerName() + ":8080";
//        SimpleMailMessage registrationmsg = new SimpleMailMessage();
//        registrationmsg.setFrom("support@demo.com");
//        registrationmsg.setTo(customerregistration.getEmail_id());
//        registrationmsg.setSubject("Username Request");
//        registrationmsg.setText("You have successfully registered:\n" + appUrl
//                + "/pages/registration/login-page.html?myToken=" + token);
//        emailService.sendEmail(registrationmsg);

        contractor.setRole_id("contractor");
        return repository.save(contractor);
    }
}
