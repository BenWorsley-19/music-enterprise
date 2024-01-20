package com.worsley.model;

public class Track {
    private final String name;
    private final float price;
    private final int runtimeInMilliseconds;
    private final String genre;

    public Track(String name, float price, int runtimeInMilliseconds, String genre) {
        this.name = name;
        this.price = price;
        this.runtimeInMilliseconds = runtimeInMilliseconds;
        this.genre = genre;
    }
}
