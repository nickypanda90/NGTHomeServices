package com.txstate.edu.homeServices.controller;


import com.txstate.edu.homeServices.model.CustomerRegistration;
import com.txstate.edu.homeServices.model.UserAuthenticationResponse;
import com.txstate.edu.homeServices.repository.AuthenticRepository;
import com.txstate.edu.homeServices.repository.CustomerRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/customer/api")//After deployment on web take the IP port(e.g Amazon)
public class CustomerController {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    AuthenticRepository authenticRepository;

    @PostMapping("/signup")
    public CustomerRegistration signupCustomer(@Valid @RequestBody CustomerRegistration customerregistration) {
        CustomerRegistration temp = customerregistration;
        try {

            temp = customerRepository.save(customerregistration);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return temp;
    }

    @PostMapping("/login")
    public UserAuthenticationResponse login( @Valid @RequestBody CustomerRegistration customerregistration) {
        String name;
        UserAuthenticationResponse user = new UserAuthenticationResponse();
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


//    @PostMapping("/forgotpass")
//    public  CustomerRegistration updatepass(@Valid @RequestBody CustomerRegistration customerregistration) {
//        CustomerRegistration pass = customerregistration;
//
//       pass=authenticRepository.save(customerregistration.getEmail_id(),customerregistration.getPassword());
//
//        return pass;
//
//
//    }


}