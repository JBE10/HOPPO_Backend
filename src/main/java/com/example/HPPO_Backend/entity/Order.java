package com.example.HPPO_Backend.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Double total;

    @Column
    private Date date;

    @Column
    private Long userId;
//pasantias su eb ka utn
}
