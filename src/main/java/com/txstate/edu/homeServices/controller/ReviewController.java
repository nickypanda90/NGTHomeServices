package com.txstate.edu.homeServices.controller;



import com.txstate.edu.homeServices.model.CustomerFeedback;
import com.txstate.edu.homeServices.model.CustomerRegistration;
import com.txstate.edu.homeServices.model.UserAuthenticationResponse;
import com.txstate.edu.homeServices.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController

@RequestMapping("/customer/api")//After deployment on web take the IP port(e.g Amazon)
public class ReviewController {
    @Autowired
    ReviewRepository reviewRepository;


//
//    @PostMapping("/feedback")
//    public CustomerFeedback feedback(@Valid @RequestBody CustomerFeedback customerFeedback, HttpServletRequest request) {
//        UserAuthenticationResponse cust = (UserAuthenticationResponse) request.getSession().getAttribute("USER_INFO");
//        System.out.println(cust.getName());
//        return reviewRepository.save(customerFeedback);
//    }


    @PostMapping("/feedback")
    public CustomerFeedback feedback(@Valid @RequestBody CustomerFeedback customerFeedback) {
        return reviewRepository.save(customerFeedback);
    }

 }