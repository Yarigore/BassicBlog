package com.dimas.BassicBlog.Mapper;

import com.dimas.BassicBlog.DTO.UsersDTO.UsersRequestDTO;
import com.dimas.BassicBlog.DTO.UsersDTO.UsersResponseDTO;
import com.dimas.BassicBlog.Entity.Role;
import com.dimas.BassicBlog.Entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsersMapper {

    @Autowired
    private RoleMapper roleMapper;

    public UsersResponseDTO toResponseDTO(Users users){
        UsersResponseDTO dto = new UsersResponseDTO();
        dto.setId(users.getId());
        dto.setName(users.getName());
        dto.setRole(roleMapper.toResponseDTO(users.getRole()));
        return dto;
    }

    public Users toEntity(UsersRequestDTO usersRequestDTO, Role role){
        Users users = new Users();
        users.setName(usersRequestDTO.getName());
        users.setPassword(usersRequestDTO.getPassword());
        users.setRole(role);
        return users;
    }

}
