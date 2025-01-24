package com.dimas.BassicBlog.Controller;

import com.dimas.BassicBlog.DTO.RoleDTO.RoleRequestDTO;
import com.dimas.BassicBlog.DTO.RoleDTO.RoleResponseDTO;
import com.dimas.BassicBlog.Entity.Role;
import com.dimas.BassicBlog.Mapper.RoleMapper;
import com.dimas.BassicBlog.Service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/role")
@AllArgsConstructor
public class RoleController {

    private RoleService roleService;
    private RoleMapper roleMapper;

    @GetMapping
    public ResponseEntity<List<RoleResponseDTO>> getRoles() {
        return roleService.getRoles()
                .map(roles -> {
                    List<RoleResponseDTO> roleDTOs = roles.stream()
                            .map(roleMapper::toResponseDTO)
                            .collect(Collectors.toList());
                    return ResponseEntity.ok(roleDTOs);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleResponseDTO> getRoleById(@PathVariable Long id) {
        return roleService.getRolebyId(id)
                .map(roleMapper::toResponseDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<RoleResponseDTO> createRole(@RequestBody RoleRequestDTO roleRequestDTO) {
        Role role = roleMapper.toEntity(roleRequestDTO);
        return roleService.saveRole(role)
                .map(roleMapper::toResponseDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<RoleResponseDTO> updateRole(@PathVariable Long id, @RequestBody RoleRequestDTO roleRequestDTO) {
        Optional<Role> roleToChange = roleService.getRolebyId(id);

        if (roleToChange.isPresent()) {
            Role existingRole = roleToChange.get();
            if (roleRequestDTO.getRoleName() != null) {
                existingRole.setRoleName(roleRequestDTO.getRoleName());
            }

            return roleService.saveRole(existingRole)
                    .map(roleMapper::toResponseDTO)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.ok().build());
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        boolean isDeleted = roleService.deleteRole(id);
        if (isDeleted) return ResponseEntity.noContent().build();
        else return ResponseEntity.notFound().build();
    }

}
