package com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.database.entity;

import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.domain.TypeKitchen;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "restaurant")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    @Enumerated(EnumType.STRING)
    private TypeKitchen typeKitchen;
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RestaurantScheduleSlotEntity> scheduleSlots;
    private Long owner;
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MenuItemEntity> menuItems;
}
