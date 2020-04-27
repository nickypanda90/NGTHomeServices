package com.txstate.edu.homeServices.controller;



import com.txstate.edu.homeServices.model.CustomerFeedback;
import com.txstate.edu.homeServices.model.CustomerRegistration;
import com.txstate.edu.homeServices.model.LoginDetail;
import com.txstate.edu.homeServices.model.UserAuthenticationResponse;
import com.txstate.edu.homeServices.object.ServiceOrder;
import com.txstate.edu.homeServices.repository.ReviewRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@RestController

@RequestMapping("/customer/api")//After deployment on web take the IP port(e.g Amazon)
public class ReviewController {
    @Autowired
    ReviewRepository reviewRepository;

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @PostMapping("/feedback")
    public CustomerFeedback feedback(@Valid @RequestBody CustomerFeedback customerFeedback, HttpServletRequest request) {
        CustomerFeedback user = new CustomerFeedback();
        request.getSession().getAttribute("USER_INFO");

        return reviewRepository.save(customerFeedback);
    }


    @GetMapping("/getuserdetails")
    public CustomerRegistration getUser(HttpServletRequest request) {

        CustomerRegistration user = new CustomerRegistration();

      user= (CustomerRegistration) request.getSession().getAttribute("USER_DETAILS_EXPANDED");


        return user;
    }
}