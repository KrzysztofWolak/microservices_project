package com.wolcar.students.service;

import com.wolcar.students.domain.Student;
import com.wolcar.students.exception.StudentError;
import com.wolcar.students.exception.StudentException;
import com.wolcar.students.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Override
    public List<Student> getAll() {
        return studentRepository.findAll();
    }

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    private void validateEmail(Student student) {
        if (studentRepository.existsByEmail(student.getEmail()))
            throw new StudentException(StudentError.EMAIL_ALREADY_EXISTS);
    }

    @Override
    public List<Student> getStudents(Student.Status status) {
        return studentRepository.findAllByStatus(status);
    }

    @Override
    public Student addStudent(Student student) {
        validateEmail(student);
        return studentRepository.save(student);
    }

    @Override
    public Student getStudentById(int id) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new StudentException(StudentError.STUDENT_NOT_FOUND));
        if (student.getStatus() == Student.Status.INACTIVE) {
            throw new StudentException(StudentError.STUDENT_STATUS_INACTIVE);
        }
        return student;

    }

    @Override
    public void deleteStudent(int id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentException(StudentError.STUDENT_NOT_FOUND));

        student.setStatus(Student.Status.INACTIVE);
        studentRepository.save(student);

    }

    @Override
    public Student putStudent(int id, Student student) {
        return studentRepository.findById(id).map(student1 -> {
            if (studentRepository.existsByEmail(student.getEmail()) && (!student1.getEmail().equals(student.getEmail())))
                throw new StudentException(StudentError.EMAIL_ALREADY_EXISTS);
            student1.setName(student.getName());
            student1.setSurname(student.getSurname());
            student1.setCity(student.getCity());
            student1.setEmail(student.getEmail());
            student1.setStatus(student.getStatus());
            return studentRepository.save(student1);
        }).orElseThrow(() -> new StudentException(StudentError.STUDENT_NOT_FOUND));
    }


    @Override
    public Student patchStudent(int id, Student student) {
        return studentRepository.findById(id).map(student1 -> {
            if (StringUtils.hasText(student.getName())) {
                student1.setName(student.getName());
            }
            if (StringUtils.hasText(student.getSurname())) {
                student1.setSurname(student.getSurname());
            }
            if (StringUtils.hasText(student.getCity())) {
                student1.setCity(student.getCity());
            }
            if (StringUtils.hasText(student.getStatus().toString())) {
                student1.setStatus(student.getStatus());
            }
            return studentRepository.save(student1);
        }).orElseThrow(() -> new StudentException(StudentError.STUDENT_NOT_FOUND));
    }


    @Override
    public List<Student> getStudentByName(String name) {
        return studentRepository.findByName(name);
    }

    @Override
    public List<Student> getStudentNameNotLike(String name, String surname) {
        return studentRepository.findBySurnameAndNameIsNotLike(name, surname);
    }

}
