package com.dimas.BassicBlog.DTO.UsersDTO;

import lombok.Data;

@Data
public class UsersRequestDTO {

    private String name;
    private String password;
    private Long roleId;

}
