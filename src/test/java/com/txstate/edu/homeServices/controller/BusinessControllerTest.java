package com.txstate.edu.homeServices.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.txstate.edu.homeServices.model.CustomerRegistration;
import com.txstate.edu.homeServices.repository.CustomerRepository;
import com.txstate.edu.homeServices.service.EmailService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BusinessController.class)
class BusinessControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerRepository repo;

    @MockBean
    private EmailService emailService;

    @Test
    public void testRegisterBusiness() throws Exception {
        CustomerRegistration contractor = new CustomerRegistration();
        contractor.setCustomer_Id(123);
        when(repo.save(contractor)).thenReturn(contractor);
        this.mockMvc.perform(post("/business/api/signup")
                .content(asJsonString(contractor))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("contractor")));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testGetServices()throws Exception {
        CustomerRegistration contractorOne = new CustomerRegistration();
        contractorOne.setBusiness_category("masonary");
        CustomerRegistration contractorTwo = new CustomerRegistration();
        contractorTwo.setCustomer_Id(222);

        when(repo.findByBusiness_category(Mockito.anyString())).thenReturn(Arrays.asList(contractorOne, contractorTwo));
        this.mockMvc.perform(get("/business/api/category/{category}", "lawn mower")
                .content(asJsonString(Mockito.anyString()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("222")));
    }
}