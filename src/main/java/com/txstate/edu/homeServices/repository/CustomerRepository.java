package com.txstate.edu.homeServices.repository;
import com.txstate.edu.homeServices.model.CustomerRegistration;
import com.txstate.edu.homeServices.model.LoginDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerRegistration, Integer> {


  @Query("SELECT new com.txstate.edu.homeServices.model.LoginDetail(u.name,u.role_id,true) FROM CustomerRegistration u WHERE LOWER(u.email_id) = LOWER(:email_id) and u.password = :password")

// public String findByEmail_idaAndPassword(String email_id,String password);

    LoginDetail findByEmail_idaAndPassword(String email_id,String password);


    @Query("SELECT auth.name FROM CustomerRegistration auth WHERE auth.email_id = :email_id")
    public String findByEmail_id(String email_id);

    //    @Transactional
    @Modifying
    @Query("update  CustomerRegistration p set p.password = :password WHERE LOWER(p.email_id) = LOWER(:email_id)")
     void save(String email_id, String password);


}