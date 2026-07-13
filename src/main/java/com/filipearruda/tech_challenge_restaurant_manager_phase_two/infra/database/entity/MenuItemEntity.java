package com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.database.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "menu_item")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Double price;
    private String description;
    private String image;
    private boolean availableOnlyOnSite;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private RestaurantEntity restaurant;
}
