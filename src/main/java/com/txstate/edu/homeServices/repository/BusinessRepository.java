package com.txstate.edu.homeServices.repository;

import com.txstate.edu.homeServices.model.BusinessEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessRepository extends JpaRepository<BusinessEntity, Integer> {

}
