package com.wolcar.students.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class StudentExceptionHandler {


    @ExceptionHandler(value = StudentException.class)
    public ResponseEntity<ErrorInfo> handlerException(StudentException e) {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        if (StudentError.STUDENT_NOT_FOUND.equals(e.getStudentError())) httpStatus = HttpStatus.NOT_FOUND;
        else if (StudentError.EMAIL_ALREADY_EXISTS.equals(e.getStudentError())) httpStatus = HttpStatus.CONFLICT;
        else if (StudentError.STUDENT_STATUS_INACTIVE.equals(e.getStudentError())) httpStatus = HttpStatus.BAD_REQUEST;

        return ResponseEntity.status(httpStatus).body(new ErrorInfo(e.getStudentError().getMessage()));
    }

}
