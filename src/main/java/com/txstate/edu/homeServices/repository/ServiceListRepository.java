package com.txstate.edu.homeServices.repository;


import com.txstate.edu.homeServices.model.CustomerRegistration;
import com.txstate.edu.homeServices.model.DisplayUIServices;
import com.txstate.edu.homeServices.model.ServiceCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceListRepository extends JpaRepository<ServiceCategory, Integer> {



    @Query("SELECT p FROM ServiceCategory p WHERE p.Service_Type LIKE %:Service_Type%")
    List<ServiceCategory> display(@Param("Service_Type") String Service_Type );

//    @Query("SELECT m FROM ServiceCategory m WHERE m.Service_Type LIKE ?1%")
//    List<ServiceCategory> display(String Service_Type);

//    @Query("SELECT m FROM ServiceCategory m where m.Service_Type LIKE %: Service_Type%")
//    public String display(String Service_Type);

//    @Query("SELECT m FROM ServiceCategory m where m.Service_Type like '%[%'")
//    public String display(String Service_Type);

}
