package com.wolcar.courses.controller;

import com.wolcar.courses.domain.Course;
import com.wolcar.courses.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @GetMapping
    public List<Course> getCourses(@RequestParam(required = false) Course.Status status) {
        return courseService.getCourses(status);
    }

    @PostMapping
    public Course addCourse(@Valid @RequestBody Course course) {
        return courseService.addCourse(course);
    }

    @GetMapping("/{code}")
    public Course getCourse(@PathVariable String code) {
        return courseService.getCourse(code);
    }

    @DeleteMapping("/{code}")
    public void deleteCourse(@PathVariable String code) {
        courseService.deleteCourse(code);
    }

    @PutMapping("/{code}")
    public Course editAllCourseArea(@PathVariable String code, @RequestBody Course course) {
        return courseService.putCourse(code, course);
    }

    @PatchMapping("/{code}")
    public Course editCourse(@PathVariable String code, @RequestBody Course course) {
        return courseService.patchCourse(code, course);
    }

    @PostMapping("/{courseCode}/students/{studentId}")
    public ResponseEntity<?> addStudentToCourse(@PathVariable String courseCode, @PathVariable int studentId) {
        courseService.addStudentToCourse(courseCode, studentId);
        return ResponseEntity.ok().build();
    }
}
