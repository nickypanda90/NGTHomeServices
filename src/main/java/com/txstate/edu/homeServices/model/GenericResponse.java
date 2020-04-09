package com.txstate.edu.homeServices.model;

public class GenericResponse {

    public GenericResponse(String text) {
        this.text = text;
    }

    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
