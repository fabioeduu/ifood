package com.ifood.api.controller;

import com.ifood.api.model.MenuItem;
import com.ifood.api.service.MenuItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/menu-items")
@CrossOrigin(origins = "*")
public class MenuItemController {
    
    private final MenuItemService menuItemService;
    
    @Autowired
    public MenuItemController(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }
    
    @GetMapping
    public ResponseEntity<List<MenuItem>> getAllMenuItems() {
        List<MenuItem> menuItems = menuItemService.getAllMenuItems();
        return ResponseEntity.ok(menuItems);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<MenuItem> getMenuItemById(@PathVariable Long id) {
        Optional<MenuItem> menuItem = menuItemService.getMenuItemById(id);
        return menuItem.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<MenuItem> createMenuItem(@Valid @RequestBody MenuItem menuItem) {
        MenuItem savedMenuItem = menuItemService.saveMenuItem(menuItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMenuItem);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<MenuItem> updateMenuItem(@PathVariable Long id, 
                                                  @Valid @RequestBody MenuItem menuItem) {
        if (!menuItemService.getMenuItemById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        MenuItem updatedMenuItem = menuItemService.updateMenuItem(id, menuItem);
        return ResponseEntity.ok(updatedMenuItem);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenuItem(@PathVariable Long id) {
        if (!menuItemService.getMenuItemById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        menuItemService.deleteMenuItem(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<MenuItem>> getMenuItemsByRestaurant(@PathVariable Long restaurantId,
                                                                  @RequestParam(defaultValue = "false") boolean availableOnly) {
        List<MenuItem> menuItems;
        if (availableOnly) {
            menuItems = menuItemService.getAvailableMenuItemsByRestaurant(restaurantId);
        } else {
            menuItems = menuItemService.getMenuItemsByRestaurant(restaurantId);
        }
        return ResponseEntity.ok(menuItems);
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<MenuItem>> searchMenuItems(@RequestParam String name) {
        List<MenuItem> menuItems = menuItemService.searchByName(name);
        return ResponseEntity.ok(menuItems);
    }
}