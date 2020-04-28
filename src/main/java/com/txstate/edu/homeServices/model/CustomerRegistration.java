package com.txstate.edu.homeServices.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user_details")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"},
        allowGetters = true)
public class CustomerRegistration {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer customer_Id;

    private String name;
    private String address;
    private String email_id;
    private String phone_no;
    private String password;
    private String role_id;
    private String reset_token;
    private String business_category;

    public String getBusiness_category() {
        return business_category;
    }

    public void setBusiness_category(String business_category) {
        this.business_category = business_category;
    }

    @Transient
    private String passwordConfirm;


    private String token;

    private Date token_ts;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getToken_ts() {
        return token_ts;
    }

    public void setToken_ts(Date token_ts) {
        this.token_ts = token_ts;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getReset_token() {
        return reset_token;
    }

    public void setReset_token(String reset_token) {
        this.reset_token = reset_token;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getRole_id() {
        return role_id;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getCustomer_Id() {
        return customer_Id;
    }

    public void setCustomer_Id(Integer customer_Id) {
        this.customer_Id = customer_Id;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    @Override
    public String toString() {
        return "CustomerRegistration{" +
                "customer_Id=" + customer_Id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", email_id='" + email_id + '\'' +
                ", phone_no='" + phone_no + '\'' +
                ", password='" + password + '\'' +
                ", role_id='" + role_id + '\'' +
                ", reset_token='" + reset_token + '\'' +
                ", business_category='" + business_category + '\'' +
                ", passwordConfirm='" + passwordConfirm + '\'' +
                ", token='" + token + '\'' +
                ", token_ts=" + token_ts +
                '}';
    }
}
