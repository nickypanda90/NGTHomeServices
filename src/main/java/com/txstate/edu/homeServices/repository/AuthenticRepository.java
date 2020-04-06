package com.txstate.edu.homeServices.repository;

import com.txstate.edu.homeServices.model.CustomerRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthenticRepository extends JpaRepository<CustomerRegistration, Integer> {
    @Query("SELECT auth FROM CustomerRegistration auth WHERE auth.email_id = :email_id")
    public List<CustomerRegistration> authenticate(String email_id);
}

