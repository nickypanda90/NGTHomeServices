package com.txstate.edu.homeServices.repository;

import com.txstate.edu.homeServices.model.CustomerRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AuthenticRepository extends JpaRepository<CustomerRegistration, Integer> {
    @Query("SELECT auth FROM CustomerRegistration auth WHERE auth.email_id = :email_id")
   List<CustomerRegistration> authenticate(String email_id);


//    @Transactional
@Modifying
    @Query("update  CustomerRegistration p set p.password = :password WHERE LOWER(p.email_id) = LOWER(:email_id)")
    void save(String email_id,String password);


}
