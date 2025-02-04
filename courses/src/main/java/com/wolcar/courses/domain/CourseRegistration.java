package com.wolcar.courses.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Embeddable
@NoArgsConstructor
public class CourseRegistration {


    @NotBlank
    private String email;

    @NotBlank
    private LocalDate studentJoinDate;

    public CourseRegistration(@NotNull String email) {
        this.email = email;
        studentJoinDate = LocalDate.now();
    }
}
