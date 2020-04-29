package com.txstate.edu.homeServices.controller;

import com.txstate.edu.homeServices.model.CustomerRegistration;
import com.txstate.edu.homeServices.object.ServiceOrder;
import com.txstate.edu.homeServices.object.ServicePayment;
import com.txstate.edu.homeServices.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/service")
public class ServiceController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OrderService orderService;

    @GetMapping(value = "/contractor/{contractor_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ServiceOrder> getServices(@PathVariable("contractor_id") Integer contractorId) {
        return orderService.fetchOrders(contractorId);
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ServiceOrder createService(@RequestBody ServiceOrder serviceOrder, HttpServletRequest request) {
        log.debug("Creating service for {}", serviceOrder);
        CustomerRegistration customer = (CustomerRegistration) request.getSession().getAttribute("USER_DETAILS_EXPANDED");
        serviceOrder.setBusinessId(serviceOrder.getCustomerId());
        serviceOrder.setCustomerId(customer.getCustomer_Id());
        return orderService.createServiceOrder(serviceOrder);
    }

    @PostMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ServiceOrder updateServiceStatus(@RequestBody ServiceOrder serviceOrder) {
        log.debug("Updating service status for {}", serviceOrder);
        orderService.updateServiceStatus(serviceOrder);
        return serviceOrder;
    }

    @PostMapping(value = "/payment", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ServicePayment savePaymentInfo(@RequestBody ServicePayment paymentInfo) {
        log.debug("Saving payment info for {}", paymentInfo);
        return orderService.savePaymentInfo(paymentInfo);
    }
}

