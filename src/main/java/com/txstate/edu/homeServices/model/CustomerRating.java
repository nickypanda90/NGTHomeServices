package com.txstate.edu.homeServices.model;

public class CustomerRating {

    private long count;
    private double rating;
    private String review_catg;
    private String name;

    public CustomerRating(long count, String name, String review_catg, double rating) {
        this.count = count;
        this.rating = rating;
        this.review_catg = review_catg;
        this.name = name;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getReview_catg() {
        return review_catg;
    }

    public void setReview_catg(String review_catg) {
        this.review_catg = review_catg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
