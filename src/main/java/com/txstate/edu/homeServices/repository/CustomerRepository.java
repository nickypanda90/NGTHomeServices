package com.txstate.edu.homeServices.repository;
import com.txstate.edu.homeServices.model.CustomerRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerRegistration, Integer> {


  @Query("SELECT u.name FROM CustomerRegistration u WHERE LOWER(u.email_id) = LOWER(:email_id) and u.password = :password")

 public String findByEmail_idaAndPassword(String email_id,String password);

    @Query("SELECT auth.name FROM CustomerRegistration auth WHERE auth.email_id = :email_id")
    public String findByEmail_id(String email_id);

}