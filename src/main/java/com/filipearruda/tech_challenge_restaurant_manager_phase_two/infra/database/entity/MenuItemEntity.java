package com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.database.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Double price;
    private String description;
    private String image;
    private boolean availableOnlyOnSite;
}
