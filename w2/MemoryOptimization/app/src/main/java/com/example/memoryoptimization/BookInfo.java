package com.example.memoryoptimization;

public class BookInfo {

    private int number;
    private float price;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "BookInfo{" +
                "number=" + number +
                ", price=" + price +
                '}';
    }
}
