package com.example.HPPO_Backend.entity.dto;

import com.example.HPPO_Backend.entity.Order;
import com.example.HPPO_Backend.entity.OrderItem;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class OrderResponse {
    private Long id;
    private String address;
    private String shipping;
    private Double total;
    private LocalDateTime orderDate;
    private String status;
    private List<OrderItemResponse> items;

    public OrderResponse() {}

    public static OrderResponse fromOrder(Order order) {
        OrderResponse dto = new OrderResponse();
        dto.id = order.getId();
        dto.address = order.getAddress();
        dto.shipping = order.getShipping();
        dto.total = order.getTotal();
        dto.orderDate = order.getOrderDate();
        dto.status = order.getStatus() != null ? order.getStatus().name() : null;

        dto.items = order.getItems().stream()
                .map(OrderResponse::mapItem)
                .collect(Collectors.toList());

        return dto;
    }

    private static OrderItemResponse mapItem(OrderItem item) {
        return new OrderItemResponse(
                item.getProduct().getId(),
                item.getProduct().getName(),
                item.getQuantity(),
                item.getPrice(),
                item.getProduct().getDiscount()
        );
    }

    public Long getId() { return id; }
    public String getAddress() { return address; }
    public String getShipping() { return shipping; }
    public Double getTotal() { return total; }
    public LocalDateTime getOrderDate() { return orderDate; }
    public String getStatus() { return status; }
    public List<OrderItemResponse> getItems() { return items; }

    public void setId(Long id) { this.id = id; }
    public void setAddress(String address) { this.address = address; }
    public void setShipping(String shipping) { this.shipping = shipping; }
    public void setTotal(Double total) { this.total = total; }
    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }
    public void setStatus(String status) { this.status = status; }
    public void setItems(List<OrderItemResponse> items) { this.items = items; }
}
