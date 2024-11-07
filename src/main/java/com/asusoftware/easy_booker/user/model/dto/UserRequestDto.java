package com.asusoftware.easy_booker.user.model.dto;

import com.asusoftware.easy_booker.user.model.UserRole;
import lombok.Data;

@Data
public class UserRequestDto {
    private String username;
    private String password;
    private String email;
    private UserRole role; // e.g., ROLE_USER, ROLE_ADMIN
}
