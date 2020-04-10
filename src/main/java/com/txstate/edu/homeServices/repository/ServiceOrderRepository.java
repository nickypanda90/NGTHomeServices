package com.txstate.edu.homeServices.repository;

import com.txstate.edu.homeServices.model.ServiceOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceOrderRepository extends JpaRepository<ServiceOrderEntity, Integer> {
}
