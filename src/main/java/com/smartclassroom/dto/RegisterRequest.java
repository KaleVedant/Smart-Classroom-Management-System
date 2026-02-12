package com.smartclassroom.dto;

import com.smartclassroom.entity.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {

    private String name;
    private String email;
    private String uniqueId;
    private String password;
    private Role role;
}
