package com.wolcar.students.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@SequenceGenerator(name = "seqIdGen", initialValue = 20000, allocationSize = 1)
@Getter
@Setter

public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqIdGen")
    private int id;

    @Enumerated(EnumType.STRING)
    private Student.Status status;

    @NotNull
    public enum Status {
        ACTIVE, INACTIVE
    }

    @NotBlank(message = "To pole musi byc wypełnione")
    private String name;

    @NotBlank(message = "To pole musi byc wypełnione")
    private String surname;

    @NotBlank(message = "To pole musi byc wypełnione")
    private String city;

    @Column(unique = true)
    @Email
    private String email;

}
