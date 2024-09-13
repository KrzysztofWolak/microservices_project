package com.wolcar.students.controller;

import com.wolcar.students.repository.StudentRepository;
import com.wolcar.students.domain.Student;
import com.wolcar.students.service.StudentServiceImpl;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentsController {


    private final StudentServiceImpl studentService;

    @Autowired
    public StudentsController(StudentRepository studentRepository, StudentServiceImpl studentService) {
        this.studentService = studentService;
    }


    @GetMapping
    public List<Student> getStudents(@RequestParam(required = false) Student.Status status) {
        if (status != null)  return studentService.getStudents(status);
        return studentService.getAll();

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Student addStudent(@RequestBody @NotNull Student student) {
        return studentService.addStudent(student);
    }

    @GetMapping("/{id}")
    public Student getStudent(@PathVariable int id) {
        return studentService.getStudentById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable int id) {
         studentService.deleteStudent(id);
    }

    @PutMapping("/{id}")
    public Student editAllAreaStudent(@PathVariable int id, @RequestBody Student student) {
        return studentService.putStudent(id, student);
    }

    @PatchMapping("/{id}")
    public Student editStudent(@PathVariable int id, @RequestBody Student student) {
        return studentService.patchStudent(id,student);
    }

    @GetMapping ("/name")
    public List<Student> findByName (@RequestParam String name) {
        return studentService.getStudentByName(name);
    }

    @GetMapping ("/nameNot")
    public List<Student> findWhereNameNotLike (@RequestParam String surname, @RequestParam String name) {
        return studentService.getStudentNameNotLike(name, surname);
    }
}
