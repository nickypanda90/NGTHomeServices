package com.txstate.edu.homeServices.repository;
import com.txstate.edu.homeServices.model.CustomerRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerRegistration, Integer> {

}
