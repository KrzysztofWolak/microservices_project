package com.wolcar.courses.service;

import com.wolcar.courses.domain.Course;
import com.wolcar.courses.domain.CourseRepository;
import com.wolcar.courses.exceptions.CourseException;
import com.wolcar.courses.service.dto.StudentDto;
import com.wolcar.courses.service.dto.StudentServiceClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
public class CourseServiceImplTest {
    @Mock
    private CourseRepository courseRepository;
    @Mock
    private StudentServiceClient studentServiceClient;
    @Mock
    private CourseService courseService;
    @InjectMocks
    private CourseServiceImpl courseServiceImpl;

    private Course course;

    @BeforeEach
    void courseSet() {
        course = new Course();
        course.setCode("code");
        course.setStatus(Course.Status.ACTIVE);
        course.setName("Java2025");
        course.setParticipantsNumber(0L);
        course.setParticipantsLimit(20L);
        course.setStartDate(LocalDate.parse("2024-12-01"));
        course.setEndDate(LocalDate.parse("2025-12-01"));
    }

    private static StudentDto studentDtoSet() {
        StudentDto studentDto = new StudentDto();
        studentDto.setStatus(StudentDto.Status.ACTIVE);
        studentDto.setEmail("mail@mail.com");
        return studentDto;
    }
    //given
    //when
    //then


    @Test
    void getCoursesShouldReturnListOfCoursesByStatus() {
        //given
        given(courseRepository.findAllByStatus(Course.Status.ACTIVE)).willReturn(List.of(course));
        //when
        //then
        assertThat(courseServiceImpl.getCourses(Course.Status.ACTIVE).get(0).getName(), equalTo("Java2025"));
    }

    @Test
    void getCoursesShouldReturnAllCoursesWhenStatusIsNull() {
        //given
        given(courseRepository.findAll()).willReturn(List.of(course));
        //when
        //then
        assertThat(courseServiceImpl.getCourses(null).get(0).getName(), equalTo("Java2025"));
    }

    @Test
    void getCourseShouldReturnCourseByCourseCode() {
        //given
        given(courseRepository.findById("code")).willReturn(Optional.of(course));
        //when
        //then
        assertThat(courseServiceImpl.getCourse("code"), equalTo(course));
        assertThat(courseServiceImpl.getCourse("code").getName(), equalTo("Java2025"));
    }

    @Test
    void getCourseShouldThrowExceptionWhenCourseCodeNotExist() {
        //given
        given(courseRepository.findById("InvalidCode")).willThrow(CourseException.class);
        //when
        //then
        assertThrows(CourseException.class, () -> courseServiceImpl.getCourse("InvalidCode"));
    }

    @Test
    void addCourseTest() {
        //given
        given(courseRepository.save(course)).willReturn(course);
        //when
        //then
        assertThat(courseServiceImpl.addCourse(course).getName(), equalTo("Java2025"));
    }

    @Test
    void deleteCourseTest() {
        //given
        //when
        courseServiceImpl.deleteCourse("code");
        //then
        verify(courseRepository).deleteById("code");
        assertThat(courseRepository.findById("code"), equalTo(Optional.empty()));
    }

    @Test
    void putCourseShouldThrowExceptionWhenCodeIsNotExist() {
        //given
        given(courseRepository.findById("cd")).willThrow(CourseException.class);
        //when
        //then
        assertThrows(CourseException.class, () -> courseServiceImpl.putCourse("cd", course));
    }

    @Test
    void patchCourseShouldThrowExceptionWhenCodeIsNotExist() {
        //given
        given(courseRepository.findById("cd")).willThrow(CourseException.class);
        //when
        //then
        assertThrows(CourseException.class, () -> courseServiceImpl.patchCourse("cd", course));
    }

    @Test
    void addStudentToCourseTest() {
        //given
        StudentDto studentDto = studentDtoSet();
        given(studentServiceClient.getStudentById(12)).willReturn(studentDto);
        given(courseRepository.findById("code")).willReturn(Optional.of(course));
        //when
        courseServiceImpl.addStudentToCourse("code", 12);
        //then
        verify(courseRepository).save(course);
        assertThat(course.getCourseRegistrationList().get(0).getEmail(), equalTo("mail@mail.com"));
    }

    @Test
    void studentValidationShouldThrowExceptionWhenStudentExistOnCourse() {
        //given
        StudentDto studentDto = studentDtoSet();
        given(studentServiceClient.getStudentById(12)).willReturn(studentDto);
        given(courseRepository.findById("code")).willReturn(Optional.of(course));
        //when
        courseServiceImpl.addStudentToCourse("code", 12);
        //then
        assertThrows(CourseException.class,
                () -> courseServiceImpl.studentValidationOnCourse(12, "code"));
    }

    @Test
    void studentValidationShouldThrowExceptionWhenStudentStatusNotActive() {
        //given
        StudentDto studentDto = studentDtoSet();
        given(studentServiceClient.getStudentById(12)).willReturn(studentDto);
        given(courseRepository.findById("code")).willReturn(Optional.of(course));
        //when
        studentDto.setStatus(StudentDto.Status.INACTIVE);
        //then
        assertThrows(CourseException.class,
                () -> courseServiceImpl.studentValidationOnCourse(12, "code"));
    }

    @Test
    void courseValidationShouldThrowExceptionWhenCourseLimitReached() {
        //given
        courseRepository.save(course);
        //when
        course.setParticipantsNumber(21L);
        //then
        assertThrows(CourseException.class, () -> courseServiceImpl.courseValidation(course));
    }

    @Test
    void courseValidationShouldThrowExceptionWhenFullStatusIsIncorrect() {
        //given
        courseRepository.save(course);
        //when
        course.setStatus(Course.Status.FULL);
        //then
        assertThrows(CourseException.class, () -> courseServiceImpl.courseValidation(course));
    }

    @Test
    void courseValidationShouldThrowExceptionWhenEndDateIsIncorrect() {
        //given
        courseRepository.save(course);
        //when
        course.setEndDate(LocalDate.parse("2024-11-01"));
        //then
        assertThrows(CourseException.class, () -> courseServiceImpl.courseValidation(course));
    }
}