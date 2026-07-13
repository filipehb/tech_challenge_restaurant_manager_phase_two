package com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.domain;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    private String name;
    private String address;
    private TypeKitchen typeKitchen;
    private RestaurantSchedule restaurantSchedule;
    private Long owner;
    private List<MenuItem> menuItems;

    public Restaurant(String name, String address, TypeKitchen typeKitchen, RestaurantSchedule restaurantSchedule, Long owner) {
        this.name = name;
        this.address = address;
        this.typeKitchen = typeKitchen;
        this.restaurantSchedule = restaurantSchedule;
        this.owner = owner;
        this.menuItems = new ArrayList<>();
    }

    public Restaurant(String name, String address, TypeKitchen typeKitchen, RestaurantSchedule restaurantSchedule, Long owner, List<MenuItem> menuItems) {
        this.name = name;
        this.address = address;
        this.typeKitchen = typeKitchen;
        this.restaurantSchedule = restaurantSchedule;
        this.owner = owner;
        this.menuItems = menuItems != null ? menuItems : new ArrayList<>();
    }

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(List<MenuItem> menuItems) {
        this.menuItems = menuItems != null ? menuItems : new ArrayList<>();
    }

    public void addMenuItem(MenuItem menuItem) {
        if (menuItems == null) {
            menuItems = new ArrayList<>();
        }
        boolean alreadyAssociated = menuItems.stream()
                .anyMatch(existing -> existing.getId() != null && existing.getId().equals(menuItem.getId()));
        if (!alreadyAssociated) {
            menuItems.add(menuItem);
        }
    }

    public Long getOwner() {
        return owner;
    }

    public void setOwner(Long owner) {
        this.owner = owner;
    }

    public RestaurantSchedule getRestaurantSchedule() {
        return restaurantSchedule;
    }

    public void setRestaurantSchedule(RestaurantSchedule restaurantSchedule) {
        this.restaurantSchedule = restaurantSchedule;
    }

    public TypeKitchen getTypeKitchen() {
        return typeKitchen;
    }

    public void setTypeKitchen(TypeKitchen typeKitchen) {
        this.typeKitchen = typeKitchen;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
