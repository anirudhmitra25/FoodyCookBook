package com.example.foodycookbook;

public class Favs {

    private String Dish_name;

    public Favs(){

    }

    public Favs(String name) {
        Dish_name = name;
    }

    public String getName() {
        return Dish_name;
    }

    public void setName(String name) {
        Dish_name = name;
    }
}
