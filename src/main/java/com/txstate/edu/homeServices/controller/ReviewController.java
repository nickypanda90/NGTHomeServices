package com.txstate.edu.homeServices.controller;


import com.txstate.edu.homeServices.model.*;
import com.txstate.edu.homeServices.object.ServiceOrder;
import com.txstate.edu.homeServices.repository.ReviewRepository;
import com.txstate.edu.homeServices.repository.ServiceOrderRepository;
import com.txstate.edu.homeServices.service.EmailService;
import com.txstate.edu.homeServices.service.RankingService;
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
    @Autowired
    RankingService rankingService;

    private Logger log = LoggerFactory.getLogger(this.getClass());


    private LoginDetail loginDetail;

    /**
     * this will show the ratings given by customer
     * @param customerFeedback
     * @param request
     * @return customer ratings
     */
    @PostMapping("/feedback")
    public CustomerFeedback feedback(@Valid @RequestBody CustomerFeedback customerFeedback, HttpServletRequest request) {
        CustomerFeedback user = new CustomerFeedback();
        request.getSession().getAttribute("USER_INFO");

        return reviewRepository.save(customerFeedback);
    }

    /**Display Work order History At Customer End
     * this will
     * @param customer_id
     * @param request
     * @return service work order list details for the that customer
     */

    @GetMapping  ("/servicehistory/{customer_id}")

      public List<ServiceOrderEntity> servicehistory(@PathVariable("customer_id") Integer customer_id, HttpServletRequest request)
    {
        List<ServiceOrderEntity> serviceOrderEntitys;
        serviceOrderEntitys = serviceOrderRepository.findWorkOrder_History(customer_id);
        System.out.println("ID: " + customer_id);
        return serviceOrderEntitys;

    }

    /**
     * this will show userdetails
     * @param request
     * @return user details
     */
    @GetMapping("/getuserdetails")
    public CustomerRegistration getUser(HttpServletRequest request) {

        CustomerRegistration user = new CustomerRegistration();

        user= (CustomerRegistration) request.getSession().getAttribute("USER_DETAILS_EXPANDED");


        return user;
    }

    /**
     * this will show the list of contractor along with their ranking
     * @param request
     * @return contractor ranking
     */
//    @GetMapping("/getcontractorlist")
//    public List<CustomerRating> displaycontractorlist(HttpServletRequest request)
//    {
//        return rankingService.getContractorRanking();
//    }

    /**
     * this will show the list of contractor along with their ranking
     * @param request
     * @return contractor ranking
     */
    @PostMapping("/getcontractorlist")
    public List<CustomerRating> displaycontractorlist(@RequestBody RatingRequest ratingRequest,HttpServletRequest request)
    {
        return rankingService.getContractorRanking(ratingRequest);
    }


    /**
     * this will dispaly contractor name on the basis of category
     * @param customerRegistration
     * @param request
     * @return contractor name on the basis of service category selected
     */
    @PostMapping("/getcontractorname")
    public List<String> getContractorName(@Valid @RequestBody CustomerRegistration customerRegistration, HttpServletRequest request) {

       return reviewRepository.getContractors(customerRegistration.getBusiness_category());

    }
}