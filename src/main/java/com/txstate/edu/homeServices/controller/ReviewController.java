package com.txstate.edu.homeServices.controller;


import com.txstate.edu.homeServices.model.*;
import com.txstate.edu.homeServices.object.ServiceOrder;
import com.txstate.edu.homeServices.repository.ReviewRepository;
import com.txstate.edu.homeServices.repository.ServiceOrderRepository;
import com.txstate.edu.homeServices.service.EmailService;
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
    @Autowired
    ServiceOrderRepository serviceOrderRepository;

    private Logger log = LoggerFactory.getLogger(this.getClass());


    private LoginDetail loginDetail;

    @PostMapping("/feedback")
    public CustomerFeedback feedback(@Valid @RequestBody CustomerFeedback customerFeedback, HttpServletRequest request) {
        CustomerFeedback user = new CustomerFeedback();
        request.getSession().getAttribute("USER_INFO");

        return reviewRepository.save(customerFeedback);
    }
    /* Display Work order History At Customer End */
    @GetMapping  ("/servicehistory/{customer_id}")

    public List<ServiceOrderEntity> servicehistory(@PathVariable("customer_id") Integer customer_id, HttpServletRequest request)
    {
        List<ServiceOrderEntity> serviceOrderEntitys;
        serviceOrderEntitys = serviceOrderRepository.findWorkOrder_History(customer_id);
        System.out.println("ID: " + customer_id);
        return serviceOrderEntitys;

    }

    @GetMapping("/getuserdetails")
    public CustomerRegistration getUser(HttpServletRequest request) {

        CustomerRegistration user = new CustomerRegistration();

        user= (CustomerRegistration) request.getSession().getAttribute("USER_DETAILS_EXPANDED");


        return user;
    }

    /* comment this one before commit- Service for getting list of contractor */
//    @GetMapping("/getcontractorlist")
//    public List<CustomerFeedback> displaycontractorlist(HttpServletRequest request)
//    {
//        return reviewRepository.display_Contractor_List();
//
//    }

    /* service for displaying contractor name on the basis of category */
    @PostMapping("/getcontractorname")
    public String getContractorName(@Valid @RequestBody CustomerRegistration customerRegistration, HttpServletRequest request) {

        return reviewRepository.getContractors(customerRegistration.getBusiness_category());
    }
}   