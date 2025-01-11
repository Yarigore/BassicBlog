package com.dimas.BassicBlog.Controller;

import com.dimas.BassicBlog.Entity.Users;
import com.dimas.BassicBlog.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<Users>> getUsers() {
        return userService.getUsers()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("{id}")
    public ResponseEntity<Users> getUserById(@PathVariable Long id){
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Users> createUser(@RequestBody Users user) {
        return userService.saveUser(user)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @PatchMapping("{id}")
    public ResponseEntity<Users> updateUser(@PathVariable Long id, @RequestBody Users user) {

        Optional<Users> userOptional = userService.getUserById(id);

        if (userOptional.isPresent()){
            Users existingUser = userOptional.get();
            if (user.getName() != null){
                existingUser.setName(user.getName());
            }
            if (user.getPassword() != null){
                existingUser.setPassword(user.getPassword());
            }
            if (user.getRole() != null){
                existingUser.setRole(user.getRole());
            }

            Optional<Users> updatedTag = userService.saveUser(existingUser);

            return updatedTag
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.status(500).build());
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Users> deleteUser(@PathVariable Long id) {
        boolean isDeleted = userService.deleteUser(id);
        if (isDeleted) return ResponseEntity.noContent().build();
        else return ResponseEntity.notFound().build();
    }

}
