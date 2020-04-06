package com.txstate.edu.homeServices.repository;
import com.txstate.edu.homeServices.model.CustomerRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerRegistration, Integer> {

    @Query("SELECT c FROM CustomerRegistration c WHERE LOWER(c.email_id) = LOWER(:email_id) and c.password = :password")
    public List<CustomerRegistration> authenticate(String email_id, String password);
}
