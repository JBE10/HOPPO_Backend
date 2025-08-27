package com.example.HPPO_Backend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String lastName;

<<<<<<< codex/create-controllers,-services,-repositories-for-new-entities-4mwprd
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
=======
    @Column(name = "role_id")
    private Long roleId;
>>>>>>> main
}
