package com.wolcar.courses.controller;

import com.wolcar.courses.domain.Course;
import com.wolcar.courses.domain.dto.StudentDto;
import com.wolcar.courses.service.CourseService;
import com.wolcar.courses.service.StudentServiceClient;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

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
        courseService.deleteStudent(code);
    }

    @PutMapping("/{code}")
    public Course editAllCourseArea(@PathVariable String code, @RequestBody Course course) {
        return courseService.putCourse(code, course);
    }

    @PatchMapping("/{code}")
    public Course editStudent(@PathVariable String code, @RequestBody Course course) {
        return courseService.patchCourse(code, course);
    }

    @PostMapping("/{courseCode}/student/{studentId}")
    public ResponseEntity<?> addStudentToCourse(@PathVariable String courseCode, @PathVariable int studentId) {
        courseService.addStudentToCourse(courseCode, studentId);
        return ResponseEntity.ok().build();
    }
}
