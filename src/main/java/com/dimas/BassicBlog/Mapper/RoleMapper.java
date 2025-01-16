package com.dimas.BassicBlog.Mapper;

import com.dimas.BassicBlog.DTO.Role.RoleRequestDTO;
import com.dimas.BassicBlog.DTO.Role.RoleResponseDTO;
import com.dimas.BassicBlog.Entity.Role;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

    public RoleResponseDTO toResponseDTO(Role role) {
        RoleResponseDTO dto = new RoleResponseDTO();
        dto.setId(role.getId());
        dto.setRoleName(role.getRoleName());
        return dto;
    }

    public Role toEntity(RoleRequestDTO dto) {
        Role role = new Role();
        role.setRoleName(dto.getRoleName());
        return role;
    }
}
