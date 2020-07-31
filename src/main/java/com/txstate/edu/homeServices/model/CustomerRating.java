package com.txstate.edu.homeServices.model;


import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class CustomerRating {

    private long count;
    private long max;
    private long score;
    private int rank;
    private double rating;
    private String review_catg;
    private String name;

    public CustomerRating(long count, String name, String review_catg, double rating) {
        this.count = count;
        this.rating = rating;
        this.review_catg = review_catg;
        this.name = name;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public long getMax() {
        return max;
    }

    public void setMax(long max) {
        this.max = max;
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
