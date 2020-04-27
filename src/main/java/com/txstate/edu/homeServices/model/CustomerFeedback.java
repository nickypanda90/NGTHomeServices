package com.txstate.edu.homeServices.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = "cust_review")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"},
        allowGetters = true)
public class CustomerFeedback {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int cust_review_id;

    private String review_catg;
    private String email_id;
    private String name;
    private String message;

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    private String rating;


    public int getCust_review_id() {
        return cust_review_id;
    }

    public void setCust_review_id(int cust_review_id) {
        this.cust_review_id = cust_review_id;
    }



    public String getReview_catg() {
        return review_catg;
    }

    public void setReview_catg(String review_catg) {
        this.review_catg = review_catg;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }






}
