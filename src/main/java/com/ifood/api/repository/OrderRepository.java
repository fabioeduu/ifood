package com.ifood.api.repository;

import com.ifood.api.model.Order;
import com.ifood.api.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    
    List<Order> findByRestaurantId(Long restaurantId);
    
    List<Order> findByStatus(OrderStatus status);
    
    List<Order> findByCustomerNameContainingIgnoreCase(String customerName);
    
    List<Order> findByRestaurantIdAndStatus(Long restaurantId, OrderStatus status);
}