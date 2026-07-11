package com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.domain;

public class MenuItem {
    private String name;
    private Double price;
    private String description;
    private String image;
    private boolean availableOnlyOnSite;

    public MenuItem(String name, Double price, String description, String image, boolean availableOnlyOnSite) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.image = image;
        this.availableOnlyOnSite = availableOnlyOnSite;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isAvailableOnlyOnSite() {
        return availableOnlyOnSite;
    }

    public void setAvailableOnlyOnSite(boolean availableOnlyOnSite) {
        this.availableOnlyOnSite = availableOnlyOnSite;
    }
}
