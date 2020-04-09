package com.txstate.edu.homeServices.service;

import com.txstate.edu.homeServices.model.BusinessEntity;
import com.txstate.edu.homeServices.model.BusinessRegistration;
import com.txstate.edu.homeServices.repository.BusinessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusinessService {

    @Autowired
    private BusinessRepository repository;

    public List<BusinessEntity> getAllBusinesses() {
        return repository.findAll();
    }

    public BusinessRegistration register(BusinessRegistration registration) {
        BusinessEntity entity = new BusinessEntity();
        entity.setName(registration.getName());
        entity.setAddress(registration.getAddress());
        entity.setEmail(registration.getEmail());
        entity.setPassword(registration.getPassword());
        entity.setCategory(registration.getCategory());
        BusinessEntity savedEntity = repository.save(entity);

        registration.setId(savedEntity.getBusinessId());
        return registration;
    }
}
