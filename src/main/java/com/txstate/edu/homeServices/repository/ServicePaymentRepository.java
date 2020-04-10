package com.txstate.edu.homeServices.repository;

import com.txstate.edu.homeServices.model.ServicePaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicePaymentRepository extends JpaRepository<ServicePaymentEntity, Integer> {
}
