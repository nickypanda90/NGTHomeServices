package com.txstate.edu.homeServices.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.txstate.edu.homeServices.model.LoginDetail;
import com.txstate.edu.homeServices.repository.CustomerRepository;
import com.txstate.edu.homeServices.repository.AuthenticRepository;
import com.txstate.edu.homeServices.model.CustomerRegistration;
import com.txstate.edu.homeServices.service.EmailService;
import net.bytebuddy.implementation.bind.MethodDelegationBinder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.stereotype.Repository;
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
@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    protected MockHttpSession session;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerRepository repo;

    @MockBean
    private AuthenticRepository authenticRepository;

    @MockBean
    private EmailService emailService;

    @Test
    void testSignUpCustomer() throws Exception {
        CustomerRegistration customer = new CustomerRegistration();
        customer.setCustomer_Id(111);
        when(repo.save(customer)).thenReturn(customer);
        this.mockMvc.perform(post("/customer/api/signup")
                .content(asJsonString(customer))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("customer")));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testLogin() throws Exception  {
        CustomerRegistration customer = new CustomerRegistration();
        customer.setCustomer_Id(112);
        customer.setRole_id("customer");
        customer.setEmail_id("jain.tripti16@gmail.com");
        customer.setPassword("testtest");

        session = new MockHttpSession();
        LoginDetail user = new LoginDetail();
        session.setAttribute("USER_INFO", user);
        LoginDetail name = new LoginDetail();

        when(repo.findByEmail_idaAndPassword(customer.getEmail_id(), customer.getPassword())).thenReturn(name);
        name.setCustomer_Id(112);
        name.setName("test");
        name.setRole_id("customer");
        name.setAuthenticated(true);

        System.out.println(asJsonString(name));
        this.mockMvc.perform(post("/customer/api/login")
                .content(String.valueOf((customer)))
                .contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());

    }
//
//    @Test
//    void testAuthenticEmailid() {
//    }
//
//    @Test
//    void testForgotpass() {
//    }
}