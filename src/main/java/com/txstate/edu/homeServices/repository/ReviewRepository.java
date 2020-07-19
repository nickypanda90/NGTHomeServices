package com.txstate.edu.homeServices.repository;
import com.txstate.edu.homeServices.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<CustomerFeedback, Long> {

//Search contractor
//@Query("SELECT new com.txstate.edu.homeServices.model.CustomerRating(count(cu.name),cu.name,cu.review_catg,avg(cu.rating)) FROM CustomerFeedback AS cu GROUP BY cu.name,cu.review_catg")
//List<CustomerRating> display_Contractor_List();

//Contractor name
    @Query("SELECT auth.name FROM CustomerRegistration auth WHERE auth.business_category = :business_category")
    List<String> getContractors(String business_category);

}