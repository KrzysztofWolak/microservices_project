package com.wolcar.courses.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Course {
    public Course() {
    }

    public enum Status {
        ACTIVE, INACTIVE, FULL
    }

    @Id
    private String code;
    @NotBlank
    private String name;

    private String descriptions;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @NotNull
    @Future
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    @NotNull
    @Min(0)
    private Long participantsNumber;
    @NotNull
    @Min(0)
    private Long participantsLimit;
    @NotNull
    private Status status;

    @Setter(AccessLevel.NONE)
    @ElementCollection
    private List<CourseRegistration> courseRegistrationList = new ArrayList<>();

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    private void incrementParticipantsNumber() {
        participantsNumber++;
        if (participantsNumber == participantsLimit) {
            setStatus(Status.FULL);
        }
    }

    public void registerStudent(String email) {
        courseRegistrationList.add(new CourseRegistration(email));
        incrementParticipantsNumber();
    }
}
