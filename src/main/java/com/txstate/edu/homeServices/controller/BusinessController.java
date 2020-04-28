package com.txstate.edu.homeServices.controller;

import com.txstate.edu.homeServices.model.CustomerRegistration;
import com.txstate.edu.homeServices.repository.CustomerRepository;
import com.txstate.edu.homeServices.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/business/api")
public class BusinessController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CustomerRepository repository;

    @Autowired
    private EmailService emailService;

    @PostMapping(value = "/signup", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public CustomerRegistration registerBusiness(@Valid @RequestBody CustomerRegistration contractor, HttpServletRequest request) {
        log.debug("Registering business for {}", contractor);
        contractor.setRole_id("contractor");
        repository.save(contractor);
        sendRegistrationEmail(contractor, request);
        return contractor;
    }

    private void sendRegistrationEmail(CustomerRegistration contractor, HttpServletRequest request) {
        log.debug("Sending registration email to {}", contractor.getEmail_id());
        try {
            String token = UUID.randomUUID().toString();
            String appUrl = request.getScheme() + "://" + request.getServerName() + ":8080";
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("support@demo.com");
            message.setTo(contractor.getEmail_id());
            message.setSubject("Username Request");
            message.setText("You have successfully registered:\n" + appUrl
                    + "/pages/registration/login-page.html?myToken=" + token);
            emailService.sendEmail(message);
        } catch (Exception exe) {
            log.error("Exception while sending registration email", exe);
        }
    }

    @GetMapping(value = "/category/{category}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CustomerRegistration> getServices(@PathVariable("category") String category) {
        return repository.findByBusiness_category(category);
    }
}
