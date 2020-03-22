package com.txstate.edu.homeServices.controller;


import com.txstate.edu.homeServices.model.CustomerRegistration;
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

//    @GetMapping("/users")
//    public List<CustomerRegistration> getAllUsers() {
//        return customerRepository.findAll();
//    }

//    @GetMapping("/users/{id}")
//    public CustomerRegistration getUserById(@PathVariable(value = "id") Integer userId) {
//        return customerRepository.findById(userId).orElseThrow(() -> new NullPointerException("test"));
//    }

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


}
