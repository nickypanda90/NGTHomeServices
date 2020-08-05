package com.txstate.edu.homeServices.controller;

import com.txstate.edu.homeServices.model.CustomerRegistration;
import com.txstate.edu.homeServices.model.DisplayUIServices;
import com.txstate.edu.homeServices.model.LoginDetail;
import com.txstate.edu.homeServices.model.ServiceCategory;
import com.txstate.edu.homeServices.repository.ServiceListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/customer/api")
public class ServiceDisplay {
    @Autowired
    ServiceListRepository serviceListrepository;


    /**
     * this will display all service category
     * @param requestServices requesting for service list to display
     * @return service category list
     */
    @PostMapping("/displayServices")
    public List<ServiceCategory> displayServices(@Valid @RequestBody DisplayUIServices requestServices) {

        List<ServiceCategory> category = serviceListrepository.display(requestServices.getService_Type());

        return category;
    }






}