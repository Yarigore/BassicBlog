package com.dimas.BassicBlog.Controller;

import com.dimas.BassicBlog.Entity.Role;
import com.dimas.BassicBlog.Service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping
    public ResponseEntity<List<Role>> getRoles() {
        return roleService.getRoles()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> getRoleById(@RequestParam Long id) {
        return roleService.getRolebyId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Role> createRole(@RequestBody Role role) {
        return roleService.saveRole(role)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Role> updateRole(@PathVariable Long id, @RequestBody Role role) {
        Optional<Role> roleToChange = roleService.findRoleById(id);

        if (roleToChange.isPresent()) {
            Role existingRole = roleToChange.get();

            if (role.getRoleName() != null) {
                existingRole.setRoleName(role.getRoleName());
            }

            return roleService.saveRole(existingRole)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.ok().build());
        }

        return ResponseEntity.notFound().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Role> deleteRole(@PathVariable Long id) {
        return roleService.deleteRole(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
