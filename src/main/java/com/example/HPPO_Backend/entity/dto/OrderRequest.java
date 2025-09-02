package com.example.HPPO_Backend.entity.dto;

import lombok.Data;
import java.util.Date;

@Data
public class OrderRequest {

    private Date date;
    private String address;
    private String shipping;
    private Long cartId;

}
