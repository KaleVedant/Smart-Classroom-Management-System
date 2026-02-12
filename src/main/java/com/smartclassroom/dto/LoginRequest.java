package com.smartclassroom.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    private String email;     // or uniqueId later
    private String password;
}
