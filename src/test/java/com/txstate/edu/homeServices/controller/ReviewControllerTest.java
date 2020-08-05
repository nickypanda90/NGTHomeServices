package com.txstate.edu.homeServices.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.txstate.edu.homeServices.model.*;
import com.txstate.edu.homeServices.repository.ReviewRepository;
import com.txstate.edu.homeServices.repository.ServiceOrderRepository;
import com.txstate.edu.homeServices.service.RankingService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ReviewController.class)
class ReviewControllerTest {

    @MockBean
    private ReviewRepository reviewrepo;

    @MockBean
    ServiceOrderRepository serviceOrderRepository;

    @MockBean
    RankingService rankingService;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    List<String> mockList;

    @Test
    void testCustomerFeedback() throws Exception {
        CustomerFeedback CustomerFeedback1 = new CustomerFeedback();
        CustomerFeedback1.setContractor_name("test masonry");
        when(reviewrepo.save(CustomerFeedback1)).thenReturn(CustomerFeedback1);
        this.mockMvc.perform(post("/customer/api//feedback")
                .content(asJsonString(CustomerFeedback1))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void servicehistory() throws Exception {
        List<ServiceOrderEntity> serviceOrderEntitys= 	Arrays.asList( new ServiceOrderEntity());
        serviceOrderEntitys = serviceOrderRepository.findWorkOrder_History(137);
        when(serviceOrderEntitys).thenReturn(serviceOrderEntitys);
        mockMvc.perform(get("/customer/api//servicehistory/137")
                .content(asJsonString(serviceOrderEntitys))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void testgetUser() throws Exception {
        MockHttpSession session = new MockHttpSession();
        CustomerRegistration customerregistration1 = new CustomerRegistration();
        customerregistration1.setEmail_id("customerregistration1");
        customerregistration1.setPassword("123");
        session.setAttribute("user", customerregistration1);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/customer/api/getuserdetails")
                .session(session);

        this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status()
                        .isOk());
    }


    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testdisplaycontractorlist() throws Exception {
        RatingRequest rating =new RatingRequest();
        rating.setCustomerReviewW((float) 0.7);
        rating.setRatingW((float) 0.3);
        List<CustomerRating> customerRatings =
                Arrays.asList(
                        new CustomerRating(1, "Test1", "Cat1", 4.5));


        when(rankingService.getContractorRanking(rating)).thenReturn((customerRatings));

        this.mockMvc.perform(post("/customer/api/getcontractorlist")
                .content(asJsonString(rating))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testgetContractorName() throws Exception {
        CustomerRegistration contractorName = new CustomerRegistration();
        contractorName.setBusiness_category("masonry");
        when(reviewrepo.getContractors(contractorName.getBusiness_category())).thenReturn((anyList()));
        this.mockMvc.perform(post("/customer/api/getcontractorname")
                .content(asJsonString(contractorName))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());
    }
}