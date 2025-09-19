package com.ifood.api.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "restaurants")
public class Restaurant {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Restaurant name is required")
    @Column(nullable = false)
    private String name;
    
    @NotBlank(message = "Restaurant address is required")
    @Column(nullable = false)
    private String address;
    
    @NotBlank(message = "Restaurant phone is required")
    @Column(nullable = false)
    private String phone;
    
    @NotNull(message = "Delivery fee is required")
    @PositiveOrZero(message = "Delivery fee must be positive or zero")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal deliveryFee;
    
    @JsonManagedReference
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MenuItem> menuItems = new ArrayList<>();
    
    // Constructors
    public Restaurant() {}
    
    public Restaurant(String name, String address, String phone, BigDecimal deliveryFee) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.deliveryFee = deliveryFee;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public BigDecimal getDeliveryFee() {
        return deliveryFee;
    }
    
    public void setDeliveryFee(BigDecimal deliveryFee) {
        this.deliveryFee = deliveryFee;
    }
    
    public List<MenuItem> getMenuItems() {
        return menuItems;
    }
    
    public void setMenuItems(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }
}