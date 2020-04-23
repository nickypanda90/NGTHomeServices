package com.txstate.edu.homeServices.model;

public class LoginDetail {


    private String name;
    private String role_id;
    private boolean isAuthenticated;

    public LoginDetail(){}
    public LoginDetail(String name, String role_id, boolean isAuthenticated) {
        this.name = name;
        this.role_id = role_id;
        this.isAuthenticated = isAuthenticated;
    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        isAuthenticated = authenticated;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole_id() {
        return role_id;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }


}
