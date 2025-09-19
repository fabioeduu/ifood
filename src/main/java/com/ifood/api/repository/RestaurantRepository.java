package com.ifood.api.repository;

import com.ifood.api.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    
    List<Restaurant> findByNameContainingIgnoreCase(String name);
    
    List<Restaurant> findByAddressContainingIgnoreCase(String address);
}