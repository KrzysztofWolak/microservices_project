package com.wolcar.students.service;

import com.wolcar.students.domain.Student;
import com.wolcar.students.domain.StudentRepository;
import com.wolcar.students.exception.StudentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    Student student;

    @BeforeEach
    void setStudent() {
        student = new Student();
        student.setStatus(Student.Status.ACTIVE);
        student.setName("Robert");
        student.setSurname("DeNiro");
        student.setCity("Hollywood");
        student.setEmail("mail@mail.com");
        student.setId(12);
    }

    @Mock
    StudentRepository studentRepository;
    @InjectMocks
    StudentServiceImpl studentServiceImpl;

    @Test
    void getAllShouldReturnAllStudentsList() {
        //given
        given(studentRepository.findAll()).willReturn(List.of(student));
        //when
        //then
        assertThat(studentServiceImpl.getAll().size(), equalTo(1));
    }

    @Test
    void getStudentsShouldReturnStudentsListByStatus() {
        //given
        given(studentRepository.findAllByStatus(Student.Status.ACTIVE)).willReturn(List.of(student));
        //when
        //then
        assertThat(studentServiceImpl.getStudents(Student.Status.ACTIVE).size(), equalTo(1));
    }

    @Test
    void addStudentShouldThrowExceptionWhenEmailAlreadyExist() {
        //given
        given(studentRepository.existsByEmail("mail@mail.com")).willThrow(StudentException.class);
        //when
        //then
        assertThrows(StudentException.class, () -> studentServiceImpl.addStudent(student));
    }

    @Test
    void addStudentShouldSaveStudent() {
        //given
        given(studentRepository.save(student)).willReturn(student);
        //when
        studentServiceImpl.addStudent(student);
        //then
        verify(studentRepository).save(student);
        assertThat(studentServiceImpl.addStudent(student).getCity(), equalTo("Hollywood"));
    }

    @Test
    void getStudentByIdTest() {
        //given
        given(studentRepository.findById(12)).willReturn(Optional.of(student));
        //when
        Student resultStudent = studentServiceImpl.getStudentById(12);
        //then
        assertThat(resultStudent.getSurname(), equalTo("DeNiro"));
    }

    @Test
    void getStudentByIdShouldThrowExceptionWhenStudentNotExist() {
        //given
        given(studentRepository.findById(11)).willThrow(StudentException.class);
        //when
        //then
        assertThrows(StudentException.class, () -> studentServiceImpl.getStudentById(11));
    }

    @Test
    void getStudentByIdShouldThrowExceptionWhenStudentStatusIsInactive() {
        //given
        given(studentRepository.findById(12)).willThrow(StudentException.class);
        //when
        student.setStatus(Student.Status.INACTIVE);
        //then
        assertThrows(StudentException.class, () -> studentServiceImpl.getStudentById(12));
    }

    @Test
    void deleteStudentShouldSetStudentStatusToInactive() {
        //given
        given(studentRepository.findById(12)).willReturn(Optional.of(student));
        //when
        studentServiceImpl.deleteStudent(12);
        //then
        verify(studentRepository).save(student);
        assertThat(student.getStatus(), equalTo(Student.Status.INACTIVE));
    }
    @Test
    void deleteStudentShouldThrowExceptionWhenStudentNotExist() {
        //given
        given(studentRepository.findById(10)).willThrow(StudentException.class);
        //when
        //then
        assertThrows(StudentException.class, ()-> studentServiceImpl.deleteStudent(10));
    }
}
