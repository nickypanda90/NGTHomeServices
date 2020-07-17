package com.txstate.edu.homeServices.repository;
import com.txstate.edu.homeServices.model.CustomerFeedback;
import com.txstate.edu.homeServices.model.CustomerRegistration;
import com.txstate.edu.homeServices.model.ServiceCategory;
import com.txstate.edu.homeServices.model.ServiceOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<CustomerFeedback, Long> {

//uncomment for contractor list
//    @Query("SELECT cust from CustomerFeedback cust")
//    List<CustomerFeedback> display_Contractor_List();

    @Query("SELECT auth.name FROM CustomerRegistration auth WHERE auth.business_category = :business_category")
    public String getContractor(String business_category);

}