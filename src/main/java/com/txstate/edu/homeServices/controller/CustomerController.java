package com.txstate.edu.homeServices.controller;


import com.txstate.edu.homeServices.model.CustomerLogin;
import com.txstate.edu.homeServices.model.CustomerRegistration;
import com.txstate.edu.homeServices.repository.AuthenticRepository;
import com.txstate.edu.homeServices.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/customer/api")//After deployment on web take the IP port(e.g Amazon)
public class CustomerController {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    AuthenticRepository authenticRepository;


    @PostMapping("/signup")
    public CustomerRegistration signupCustomer(@Valid @RequestBody CustomerRegistration customerregistration) {
        CustomerRegistration temp =customerregistration;
        try{

            temp= customerRepository.save(customerregistration);


        }
        catch (Exception e){
            e.printStackTrace();
        }
        return temp;
    }

    @PostMapping("/login")
    public boolean signupCustomer(@Valid @RequestBody CustomerLogin customerlogin) {
        if(customerRepository.authenticate(customerlogin.getEmail_id(),customerlogin.getPassword()).size()>0)
            return true;
        else
            return false;

    }

    @PostMapping("/authenticateemailid")
   public boolean authenticEmailid(@Valid @RequestBody CustomerRegistration customerregistration) {
            if(authenticRepository.authenticate(customerregistration.getEmail_id()).size()>0)

           return true;

       else
            return false;

  }



}
