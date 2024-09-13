package com.wolcar.students.exception;

public enum StudentError {
    STUDENT_NOT_FOUND ("Student does not exists"),
    EMAIL_ALREADY_EXISTS ("Student email already exists"),
    STUDENT_STATUS_INACTIVE ("Student status is INACTIVE");


    private String message;

    StudentError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
