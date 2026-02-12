package com.smartclassroom.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MaterialRequest {

    private Long classId;
    private String title;
    private String description;
    private String materialUrl;
}
