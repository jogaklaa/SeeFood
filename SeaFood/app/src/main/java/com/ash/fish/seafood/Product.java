package com.ash.fish.seafood;



public class Product {
    private String imageUrl;
    private double negative;
    private double positive;



    public Product(String imageUrl, double negative, double positive) {
        this.imageUrl = imageUrl;
        this.negative = negative;
        this.positive = positive;

    }

    public String getImageUrl() {
        return imageUrl;
    }

    public double getNegative() {
        return negative;
    }

    public double getPositive() {
        return positive;
    }

}