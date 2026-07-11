package com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.domain;

public class User {
    private Long id;
    private String email;
    private String password;
    private String name;
    private UserType userType;

    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public User(Long id, String email, String password, String name, UserType userType) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.userType = userType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
