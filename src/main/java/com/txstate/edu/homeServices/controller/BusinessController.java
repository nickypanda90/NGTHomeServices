package com.txstate.edu.homeServices.controller;

import com.txstate.edu.homeServices.model.BusinessEntity;
import com.txstate.edu.homeServices.object.BusinessRegistration;
import com.txstate.edu.homeServices.service.BusinessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/business")
public class BusinessController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BusinessService service;

    @GetMapping
    public List<BusinessEntity> getBusinesses() {
        return service.getAllBusinesses();
    }

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public BusinessRegistration registerBusiness(@RequestBody BusinessRegistration registration) {
        log.debug("Registering business for {}", registration);
        return service.register(registration);
    }
}
