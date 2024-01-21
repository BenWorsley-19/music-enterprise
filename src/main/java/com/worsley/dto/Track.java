package com.worsley.dto;

public record Track(String name, float price, int runtimeInMilliseconds, String genre) {

    public float discountedPrice(int discountPercentage) {
        return price * (1 - discountPercentage / 100f);
    }
}
