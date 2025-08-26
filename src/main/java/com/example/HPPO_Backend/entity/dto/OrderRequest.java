package com.example.HPPO_Backend.entity.dto;

import lombok.Data;
import java.util.Date;

@Data
public class OrderRequest {
    private Double total;
    private Date date;
    private Long userId;
}
