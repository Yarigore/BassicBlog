package com.dimas.BassicBlog.DTO.UsersDTO;

import com.dimas.BassicBlog.DTO.RoleDTO.RoleResponseDTO;
import lombok.Data;

@Data
public class UsersResponseDTO {

    private Long id;
    private String name;
    private RoleResponseDTO role;
}
