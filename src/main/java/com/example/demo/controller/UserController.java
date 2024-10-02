package com.example.demo.controller;
import com.example.demo.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private List<User> Users = new ArrayList<>();
    private int currentId = 1;

    // Create
    @PostMapping
    public ResponseEntity<User> createProduct(@RequestBody User product) {
        product.setId(currentId++);
        Users.add(product);
        return ResponseEntity.ok(product);
    }

    // Read (Get all products)
    @GetMapping
    public ResponseEntity<List<User>> getAllProducts() {
        return ResponseEntity.ok(Users);
    }

    // Read (Get product by ID)
    @GetMapping("/{id}")
    public ResponseEntity<Object> getProductById(@PathVariable int id) {
        Optional<User> product = Users.stream().filter(p -> p.getId() == id).findFirst();
        if (product.isPresent()) {
            return ResponseEntity.ok(product.get());
        } else {
            return ResponseEntity.status(404).body("User with ID " + id + " not found");
        }
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable int id, @RequestBody User updatedProduct) {
        Optional<User> productOpt = Users.stream().filter(p -> p.getId() == id).findFirst();
        if (productOpt.isPresent()) {
            User product = productOpt.get();
            product.setName(updatedProduct.getName());
            product.setEmail(updatedProduct.getEmail());
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.status(404).body("User with ID " + id + " not found");
        }
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable int id) {
        boolean removed = Users.removeIf(p -> p.getId() == id);
        if (removed) {
            return ResponseEntity.ok("User with ID " + id + " was successfully deleted.");
        } else {
            return ResponseEntity.status(404).body("User with ID " + id + " not found");
        }
    }
}
