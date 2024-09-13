package com.wolcar.students.service;

import com.wolcar.students.domain.Student;

import java.util.List;

public interface StudentService {
    List<Student> getStudents(Student.Status status);

    Student getStudentById(int id);

    Student addStudent (Student student);

    void deleteStudent (int id);

    Student putStudent (int id, Student student);

    Student patchStudent (int id, Student student);

    List<Student> getStudentByName(String name);

    List<Student> getStudentNameNotLike(String name, String surname);
    List<Student> getAll();




}
