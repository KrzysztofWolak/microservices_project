package com.wolcar.courses.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CourseExceptionHandler {


    @ExceptionHandler(value = CourseException.class)
    public ResponseEntity<ErrorInfo> handlerException(CourseException e) {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        if (CourseError.COURSE_NOT_FOUND.equals(e.getCourseError())) httpStatus = HttpStatus.NOT_FOUND;
        else if (CourseError.COURSE_LIMIT_REACHED.equals(e.getCourseError())) httpStatus = HttpStatus.FORBIDDEN;
        else if (CourseError.INCORRECT_FULL_STATUS.equals(e.getCourseError())) httpStatus = HttpStatus.CONFLICT;
        else if (CourseError.INCORRECT_END_DATE.equals(e.getCourseError())) httpStatus = HttpStatus.BAD_REQUEST;
        else if (CourseError.STUDENT_STATUS_NOT_ACTIVE.equals(e.getCourseError())) httpStatus = HttpStatus.BAD_REQUEST;
        else if (CourseError.STUDENT_NOT_FOUND.equals(e.getCourseError())) httpStatus = HttpStatus.NOT_FOUND;
        else if (CourseError.COURSE_STATUS_FULL.equals(e.getCourseError())) httpStatus = HttpStatus.CONFLICT;
        else if (CourseError.STUDENT_EXIST_ON_COURSE.equals(e.getCourseError())) httpStatus = HttpStatus.CONFLICT;
        else if (CourseError.COURSE_STATUS_NOT_ACTIVE.equals(e.getCourseError())) httpStatus = HttpStatus.CONFLICT;
        return ResponseEntity.status(httpStatus).body(new ErrorInfo(e.getCourseError().getMessage()));
    }

}
