package com.example.HPPO_Backend.entity.dto;

import lombok.Data;
<<<<<<< codex/create-controllers,-services,-repositories-for-new-entities-4mwprd

@Data
public class OrderRequest {
    private String address;
    private String shipping;
    private Long cartId;
=======
import java.util.Date;

@Data
public class OrderRequest {
    private Double total;
    private Date date;
>>>>>>> main
    private Long userId;
}
