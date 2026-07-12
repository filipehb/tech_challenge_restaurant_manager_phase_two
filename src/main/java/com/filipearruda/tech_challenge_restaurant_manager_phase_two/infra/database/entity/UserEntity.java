package com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.database.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_restaurant")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String name;
    @OneToOne(fetch = FetchType.LAZY)
    private UserTypeEntity userTypeEntity;
}
