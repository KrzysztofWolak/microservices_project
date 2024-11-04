package com.wolcar.courses.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentDto {

    @NotBlank
    private String email;

    @NotNull
    private Status status;

    public enum Status {
        ACTIVE,
        INACTIVE
    }
}
