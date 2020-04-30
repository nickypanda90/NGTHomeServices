package com.txstate.edu.homeServices.service;

import com.txstate.edu.homeServices.model.CustomerRegistration;
import com.txstate.edu.homeServices.model.ServiceOrderEntity;
import com.txstate.edu.homeServices.model.ServicePaymentEntity;
import com.txstate.edu.homeServices.object.ServiceOrder;
import com.txstate.edu.homeServices.object.ServicePayment;
import com.txstate.edu.homeServices.repository.CustomerRepository;
import com.txstate.edu.homeServices.repository.ServiceOrderRepository;
import com.txstate.edu.homeServices.repository.ServicePaymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ServiceOrderRepository serviceRepo;
    @Autowired
    private ServicePaymentRepository paymentRepo;
    @Autowired
    private CustomerRepository customerRepo;
    @Autowired
    private EmailService emailService;

    public ServiceOrder createServiceOrder(ServiceOrder order) {
        ServiceOrderEntity entity = new ServiceOrderEntity();
        entity.setCustomerId(order.getCustomerId());
        entity.setBusinessId(order.getBusinessId());
        entity.setServiceDateTime(parseServiceDateTime(order.getServiceDateTime()));
        entity.setServiceDescription(order.getServiceDescription());
        entity.setServiceCategory(order.getServiceCategory());
        entity.setStatus("Pending");

        ServiceOrderEntity savedEntity = serviceRepo.save(entity);
        order.setServiceId(savedEntity.getServiceId());
        order.setStatus("Pending");
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
            order.setStatus(e.getStatus());
            return order;
        }).collect(Collectors.toList());
    }

    @Transactional
    public ServiceOrder updateServiceStatus(ServiceOrder serviceOrder) {
        ServiceOrderEntity entity = serviceRepo.findById(serviceOrder.getServiceId()).get();
        entity.setStatus(serviceOrder.getStatus());
        serviceRepo.save(entity);

        serviceOrder.setCustomerId(entity.getCustomerId());
        serviceOrder.setBusinessId(entity.getBusinessId());
        serviceOrder.setStatus(entity.getStatus());
        serviceOrder.setServiceCategory(entity.getServiceCategory());
        serviceOrder.setServiceDescription(entity.getServiceDescription());
        sendServiceAcceptance(entity);
        log.debug("Updated service status and send acceptance email.");
        return serviceOrder;
    }

    private void sendServiceAcceptance(ServiceOrderEntity entity) {
        log.debug("Sending service acceptance email for service {}", entity.getServiceId());
        try {
            CustomerRegistration customer = customerRepo.findById(entity.getCustomerId()).get();
            CustomerRegistration contractor = customerRepo.findById(entity.getBusinessId()).get();

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("support@demo.com");
            message.setTo(customer.getEmail_id());
            message.setSubject("Work Order Confirmation");
            String status = entity.getStatus().toLowerCase();
            message.setText("The service work order submitted by you has been " + status + " by provider " + contractor.getName()
                    + " for date " + entity.getServiceDateTime().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")) + " at "
                    + entity.getServiceDateTime().format(DateTimeFormatter.ofPattern("hh:mm a")) + ".");
            emailService.sendEmail(message);
        } catch (Exception exe) {
            log.error("Exception while sending registration email", exe);
        }
    }
}
