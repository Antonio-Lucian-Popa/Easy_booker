package com.asusoftware.easy_booker.user.model.dto;

import com.asusoftware.easy_booker.user.model.UserRole;
import lombok.Data;

import java.util.UUID;

@Data
public class UserResponseDto {
    private UUID id;
    private String username;
    private String email;
    private UserRole role;
    private boolean isActive;
}
