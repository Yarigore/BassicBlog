package com.dimas.BassicBlog.Controller;

import com.dimas.BassicBlog.DTO.UsersDTO.UsersRequestDTO;
import com.dimas.BassicBlog.DTO.UsersDTO.UsersResponseDTO;
import com.dimas.BassicBlog.Entity.Role;
import com.dimas.BassicBlog.Entity.Users;
import com.dimas.BassicBlog.Mapper.UsersMapper;
import com.dimas.BassicBlog.Service.RoleService;
import com.dimas.BassicBlog.Service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/user")
@AllArgsConstructor
public class UserController {

    private UserService userService;
    private RoleService roleService;
    private UsersMapper usersMapper;
    private UserService usersService;

    @GetMapping
    public ResponseEntity<List<UsersResponseDTO>> getUsers() {
        List<UsersResponseDTO> users = userService.getUsers()
                .stream()
                .map(usersMapper::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @GetMapping("{id}")
    public ResponseEntity<UsersResponseDTO> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(usersMapper::toResponseDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UsersResponseDTO> createUser(@RequestBody UsersRequestDTO usersRequestDTO) {
        Optional<Role> roleOptional = roleService.getRolebyId(usersRequestDTO.getRoleId());

        if (roleOptional.isPresent()) {
            Role role = roleOptional.get();
            Users users = usersMapper.toEntity(usersRequestDTO, role);

            return usersService.saveUser(users)
                    .map(usersMapper::toResponseDTO)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.badRequest().build());
        }

        return ResponseEntity.badRequest().build();
    }

    @PatchMapping("{id}")
    public ResponseEntity<Users> updateUser(@PathVariable Long id, @RequestBody UsersRequestDTO usersRequestDTO) {

        Optional<Users> userOptional = userService.getUserById(id);

        if (userOptional.isPresent()) {
            Users existingUser = userOptional.get();
            if (usersRequestDTO.getName() != null) {
                existingUser.setName(usersRequestDTO.getName());
            }
            if (usersRequestDTO.getPassword() != null) {
                existingUser.setPassword(usersRequestDTO.getPassword());
            }
            if (usersRequestDTO.getRoleId() != null) {
                Optional<Role> roleOptional = roleService.getRolebyId(usersRequestDTO.getRoleId());
                if (roleOptional.isPresent()) {
                    existingUser.setRole(roleOptional.get());
                } else {
                    return ResponseEntity.badRequest().build();
                }
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
