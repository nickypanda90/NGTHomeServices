package com.txstate.edu.homeServices.service;

import com.txstate.edu.homeServices.model.ServiceOrderEntity;
import com.txstate.edu.homeServices.model.ServicePaymentEntity;
import com.txstate.edu.homeServices.object.ServiceOrder;
import com.txstate.edu.homeServices.object.ServicePayment;
import com.txstate.edu.homeServices.repository.ServiceOrderRepository;
import com.txstate.edu.homeServices.repository.ServicePaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private ServiceOrderRepository serviceRepo;
    @Autowired
    private ServicePaymentRepository paymentRepo;

    public ServiceOrder createServiceOrder(ServiceOrder order) {
        ServiceOrderEntity entity = new ServiceOrderEntity();
        entity.setCustomerId(order.getCustomerId());
        entity.setBusinessId(order.getBusinessId());
        entity.setServiceDateTime(parseServiceDateTime(order.getServiceDateTime()));
        entity.setServiceDescription(order.getServiceDescription());
        entity.setServiceCategory(order.getServiceCategory());

        ServiceOrderEntity savedEntity = serviceRepo.save(entity);
        order.setServiceId(savedEntity.getServiceId());
        return order;
    }

    private LocalDateTime parseServiceDateTime(String serviceDateTime) {
        try {
            return LocalDateTime.parse(serviceDateTime, DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a"));
        } catch (Exception exe) {
            return LocalDateTime.parse(serviceDateTime, DateTimeFormatter.ofPattern("MM/dd/yyyy h:mm a"));
        }
    }

    public ServicePayment savePaymentInfo(ServicePayment payment) {
        ServicePaymentEntity entity = new ServicePaymentEntity();
        entity.setServiceId(payment.getServiceId());
        entity.setCardNumber(payment.getCardNumber());
        entity.setCardExpiryMonth(payment.getCardExpiryMonth());
        entity.setCardExpiryYear(payment.getCardExpiryYear());
        entity.setCardCVV(payment.getCardCVV());

        ServicePaymentEntity savedEntity = paymentRepo.save(entity);
        payment.setPaymentId(savedEntity.getPaymentId());
        return payment;
    }

    public List<ServiceOrder> fetchOrders(Integer businessId) {
        List<ServiceOrderEntity> entities = serviceRepo.findAllByBusinessIdOrderByServiceIdDesc(businessId);
        return entities.stream().map(e -> {
            ServiceOrder order = new ServiceOrder();
            order.setServiceId(e.getServiceId());
            order.setCustomerId(e.getCustomerId());
            order.setBusinessId(e.getBusinessId());
            order.setServiceDateTime(e.getServiceDateTime().toString());
            order.setServiceDescription(e.getServiceDescription());
            order.setServiceCategory(e.getServiceCategory());
            return order;
        }).collect(Collectors.toList());
    }
}
