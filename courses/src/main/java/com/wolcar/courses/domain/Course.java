package com.wolcar.courses.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Document
@Getter
@Setter
public class Course {
    public Course() {}

    public enum Status {
        ACTIVE, INACTIVE, FULL
    }

    @Id
    private String code;
    @NotBlank
    private String name;

    private String descriptions;

    @NotNull
    @Future
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

    private List<CourseRegistration> courseRegistrationList = new ArrayList<>();

    public void incrementParticipantsNumber() {
        participantsNumber++;
        if (participantsNumber == participantsLimit)
            setStatus(Course.Status.FULL);
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
