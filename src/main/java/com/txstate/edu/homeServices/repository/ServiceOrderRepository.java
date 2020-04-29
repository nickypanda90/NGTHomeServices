package com.txstate.edu.homeServices.repository;

import com.txstate.edu.homeServices.model.ServiceOrderEntity;
import com.txstate.edu.homeServices.object.ServiceOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceOrderRepository extends JpaRepository<ServiceOrderEntity, Integer> {

    List<ServiceOrderEntity> findAllByBusinessIdOrderByServiceIdDesc(Integer businessId);

    @Modifying
    @Query("update ServiceOrderEntity s set s.status = :#{#order.status} WHERE s.serviceId = :#{#order.serviceId}")
    void updateStatus(ServiceOrder order);
}
