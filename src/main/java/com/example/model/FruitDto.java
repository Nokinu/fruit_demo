package com.example.model;

public class FruitDto {

    private String name;
    private String season;
    private double carbohydrates;
    private double calories;

    private FruitDto(String name, String season, double carbohydrates, double calories) {
        this.name = name;
        this.season = season;
        this.carbohydrates = carbohydrates;
        this.calories = calories;
    }

    public static FruitDto of(Fruit fruit, FruityVice fruityVice) {
        var carbohydrates = fruityVice == null ? 0 : fruityVice.getNutritions().getCarbohydrates();
        var calories = fruityVice == null ? 0 : fruityVice.getNutritions().getCalories();
        return new FruitDto(fruit.name,
                fruit.season,
                carbohydrates,
                calories);
    }

    public String getName() {
        return name;
    }

    public double getCalories() {
        return calories;
    }

    public double getCarbohydrates() {
        return carbohydrates;
    }

    public String getSeason() {
        return season;
    }
}

