package com.wolcar.courses.service;

import com.wolcar.courses.domain.Course;

import java.util.List;

public interface CourseService {
    List<Course> getCourses(Course.Status status);

    Course getCourse(String courseCode);

    Course addCourse(Course course);

    void deleteCourse(String code);

    Course putCourse(String code, Course course);

    Course patchCourse(String code, Course course);

    void addStudentToCourse(String courseCode, int studentId);

}
