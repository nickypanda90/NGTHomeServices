package com.txstate.edu.homeServices.controller;

import com.txstate.edu.homeServices.model.Business;
import com.txstate.edu.homeServices.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/business")
public class BusinessController {

    @Autowired
    private BusinessService service;

    @GetMapping
    public List<Business> getBusinesses() {
        return service.getAllBusinesses();
    }
}
