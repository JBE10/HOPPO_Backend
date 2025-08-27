package com.example.HPPO_Backend.entity.dto;

import lombok.Data;
import java.util.Date;

@Data
public class OrderRequest {
    private Double total;     // total de la orden
    private Date date;        // fecha de creación
    private String address;   // dirección de envío
    private String shipping;  // método de envío
    private Long cartId;      // carrito asociado
    private Long userId;      // usuario comprador
}
