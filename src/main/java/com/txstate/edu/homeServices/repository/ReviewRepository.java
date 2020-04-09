package com.txstate.edu.homeServices.repository;
import com.txstate.edu.homeServices.model.CustomerFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<CustomerFeedback, Long> {

}