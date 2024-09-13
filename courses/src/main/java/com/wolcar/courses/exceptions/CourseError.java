package com.wolcar.courses.exceptions;

public enum CourseError {
    COURSE_NOT_FOUND("Course does not exists"),
    COURSE_LIMIT_REACHED("Course limit has been reached"),
    INCORRECT_FULL_STATUS("Course has empty places"),
    INCORRECT_END_DATE("End date is before start date"),
    STUDENT_STATUS_NOT_ACTIVE("StudentDto STATUS is not ACTIVE"),
    STUDENT_NOT_FOUND("StudentDto is not found"),
    COURSE_STATUS_FULL("Course has not empty places"),
    COURSE_STATUS_NOT_ACTIVE("Course has not ACTIVE Status"),
    STUDENT_EXIST_ON_COURSE("StudentDto already present in this course");


    private String message;

    CourseError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}