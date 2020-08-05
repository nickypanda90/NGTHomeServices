package com.txstate.edu.homeServices.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.txstate.edu.homeServices.model.DisplayUIServices;
import com.txstate.edu.homeServices.model.ServiceCategory;
import com.txstate.edu.homeServices.repository.ServiceListRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ServiceDisplay.class)
class ServiceDisplayTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ServiceListRepository serviceListRepository;

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testDisplayServices() throws Exception {
        DisplayUIServices serviceOne = new DisplayUIServices();
        serviceOne.setService_Type("masonary");

        ServiceCategory serviceCategory1 = new ServiceCategory();
        serviceCategory1.setService_Type("masonary");
        List<ServiceCategory> serviceCategories = new ArrayList<>();
        serviceCategories.add(serviceCategory1);

        when(serviceListRepository.display("masonary")).thenReturn(serviceCategories);
        this.mockMvc.perform(post("/customer/api/displayServices")
                .content(asJsonString(serviceOne))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("masonary")));
    }

}
