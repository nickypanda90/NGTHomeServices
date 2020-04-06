package com.txstate.edu.homeServices.model;

import javax.persistence.Entity;
import javax.persistence.Table;


public class CustomerLogin {

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    private String email_id;
    private String password;



    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



}
