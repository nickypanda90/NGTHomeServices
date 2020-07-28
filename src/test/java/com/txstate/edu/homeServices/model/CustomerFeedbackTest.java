package com.txstate.edu.homeServices.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerFeedbackTest {

    @Test
    public void getContractor_name() {
        CustomerFeedback cust = new CustomerFeedback();
        cust.setContractor_name("Sam");
        assertTrue(cust.getContractor_name()=="Sam");
    }

    @Test
    void setContractor_name() {
        CustomerFeedback cust = new CustomerFeedback();
        cust.setContractor_name("Sam");
        assertTrue(cust.getContractor_name()=="Sam");
    }

    @Test
    void getRating() {
    }

    @Test
    void setRating() {
    }

    @Test
    void getCust_review_id() {
    }

    @Test
    void setCust_review_id() {
    }

    @Test
    void getReview_catg() {
    }

    @Test
    void setReview_catg() {
    }

    @Test
    void getEmail_id() {
    }

    @Test
    void setEmail_id() {
    }

    @Test
    void getName() {
    }

    @Test
    void setName() {
    }

    @Test
    void getMessage() {
    }

    @Test
    void setMessage() {
    }
}