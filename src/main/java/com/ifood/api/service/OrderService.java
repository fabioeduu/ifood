package com.ifood.api.service;

import com.ifood.api.model.Order;
import com.ifood.api.model.OrderItem;
import com.ifood.api.model.OrderStatus;
import com.ifood.api.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    
    private final OrderRepository orderRepository;
    
    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
    
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
    
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }
    
    public Order saveOrder(Order order) {
        calculateOrderTotal(order);
        return orderRepository.save(order);
    }
    
    public Order updateOrder(Long id, Order order) {
        order.setId(id);
        calculateOrderTotal(order);
        return orderRepository.save(order);
    }
    
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
    
    public List<Order> getOrdersByRestaurant(Long restaurantId) {
        return orderRepository.findByRestaurantId(restaurantId);
    }
    
    public List<Order> getOrdersByStatus(OrderStatus status) {
        return orderRepository.findByStatus(status);
    }
    
    public Order updateOrderStatus(Long id, OrderStatus status) {
        Optional<Order> orderOpt = orderRepository.findById(id);
        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();
            order.setStatus(status);
            return orderRepository.save(order);
        }
        throw new RuntimeException("Order not found with id: " + id);
    }
    
    public List<Order> searchByCustomerName(String customerName) {
        return orderRepository.findByCustomerNameContainingIgnoreCase(customerName);
    }
    
    private void calculateOrderTotal(Order order) {
        BigDecimal itemsTotal = BigDecimal.ZERO;
        
        if (order.getOrderItems() != null && !order.getOrderItems().isEmpty()) {
            itemsTotal = order.getOrderItems().stream()
                    .map(OrderItem::getSubtotal)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }
        
        BigDecimal deliveryFee = order.getRestaurant() != null && order.getRestaurant().getDeliveryFee() != null ? 
                order.getRestaurant().getDeliveryFee() : BigDecimal.ZERO;
        
        order.setTotalAmount(itemsTotal.add(deliveryFee));
    }
}