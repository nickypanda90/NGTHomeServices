package com.txstate.edu.homeServices.controller;

import com.txstate.edu.homeServices.object.ServiceOrder;
import com.txstate.edu.homeServices.object.ServicePayment;
import com.txstate.edu.homeServices.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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
    public ServiceOrder createService(@RequestBody ServiceOrder serviceOrder) {
        log.debug("Creating service for {}", serviceOrder);
        return orderService.createServiceOrder(serviceOrder);
    }

    @PostMapping(value = "/payment", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ServicePayment savePaymentInfo(@RequestBody ServicePayment paymentInfo) {
        log.debug("Saving payment info for {}", paymentInfo);
        return orderService.savePaymentInfo(paymentInfo);
    }
}

