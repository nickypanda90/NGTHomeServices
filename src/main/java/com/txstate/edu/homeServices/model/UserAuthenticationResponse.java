package com.txstate.edu.homeServices.model;

public class UserAuthenticationResponse {
    private String name;
    private boolean isAuthenticated;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        isAuthenticated = authenticated;
    }
}
