package com.txstate.edu.homeServices.service;

import com.txstate.edu.homeServices.model.Business;
import com.txstate.edu.homeServices.repository.BusinessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusinessService {

    @Autowired
    private BusinessRepository repository;

    public List<Business> getAllBusinesses() {
        return repository.findAll();
    }
}
