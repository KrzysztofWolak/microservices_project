package com.wolcar.courses.service;

import com.wolcar.courses.domain.Course;
import com.wolcar.courses.domain.CourseRegistration;
import com.wolcar.courses.domain.dto.StudentDto;
import com.wolcar.courses.exceptions.CourseError;
import com.wolcar.courses.exceptions.CourseException;
import com.wolcar.courses.repository.CourseRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;


@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final StudentServiceClient studentServiceClient;

    public CourseServiceImpl(CourseRepository courseRepository, StudentServiceClient studentServiceClient) {
        this.courseRepository = courseRepository;
        this.studentServiceClient = studentServiceClient;
    }

    @Override
    public List<Course> getCourses(Course.Status status) {
        if (status != null) {
            return courseRepository.findAllByStatus(status);
        } else {
            return courseRepository.findAll();
        }
    }

    @Override
    public Course getCourse(String courseCode) {
        return courseRepository.findById(courseCode).orElseThrow(()
                -> new CourseException(CourseError.COURSE_NOT_FOUND));
    }

    @Override
    public Course addCourse(Course course) {
        courseValidation(course);
        return courseRepository.save(course);
    }

    @Override
    public void deleteStudent(String code) {
        courseRepository.deleteById(code);
    }

    @Override
    public Course putCourse(String code, Course course) {
        courseValidation(course);
        return courseRepository.findById(code).map(course1 -> {
            course1.setName(course.getName());
            course1.setDescriptions(course.getDescriptions());
            course1.setStartDate(course.getStartDate());
            course1.setEndDate(course.getEndDate());
            course1.setStatus(course.getStatus());
            course1.setParticipantsLimit(course.getParticipantsLimit());
            course1.setParticipantsNumber(course.getParticipantsNumber());
            return courseRepository.save(course1);
        }).orElseThrow(() -> new CourseException(CourseError.COURSE_NOT_FOUND));
    }

    @Override
    public Course patchCourse(String code, Course course) {
        return courseRepository.findById(code).map(course1 -> {
            if (StringUtils.hasText(course.getName())) {
                course1.setName(course.getName());
            }
            if (StringUtils.hasText(course.getDescriptions())) {
                course1.setDescriptions(course.getDescriptions());
            }
            if (StringUtils.hasText(course.getStartDate().toString())) {
                course1.setStartDate(course.getStartDate());
            }
            if (StringUtils.hasText(course.getEndDate().toString())) {
                course1.setEndDate(course.getEndDate());
            }
            if (StringUtils.hasText(course.getStatus().toString())) {
                course1.setStatus(course.getStatus());
            }
            if (StringUtils.hasText(course.getParticipantsLimit().toString())) {
                course1.setParticipantsLimit(course.getParticipantsLimit());
            }
            if (StringUtils.hasText(course.getParticipantsNumber().toString())) {
                course1.setParticipantsNumber(course.getParticipantsNumber());
            }
            courseValidation(course1);
            return courseRepository.save(course1);
        }).orElseThrow(() -> new CourseException(CourseError.COURSE_NOT_FOUND));
    }

    @Override
    public void addStudentToCourse(String courseCode, int studentId) {
        Course course = getCourse(courseCode);
        courseValidationToAddStudent(course);
        StudentDto studentDto = studentServiceClient.getStudentById(studentId);
        System.out.println(studentDto.toString());
        studentValidationOnCourse(studentId, courseCode);
        course.incrementParticipantsNumber();
        course.getCourseRegistrationList().add(new CourseRegistration(studentDto.getEmail()));
        courseRepository.save(course);

    }

    private static void courseValidationToAddStudent(Course course) {
        if (course.getParticipantsNumber().equals(course.getParticipantsLimit()))
            throw new CourseException(CourseError.COURSE_STATUS_FULL);
        if (!course.getStatus().equals(Course.Status.ACTIVE))
            throw new CourseException(CourseError.COURSE_STATUS_NOT_ACTIVE);
    }


    public void studentValidationOnCourse(int studentId, String courseCode) {
        Course course = getCourse(courseCode);
        StudentDto studentDto = studentServiceClient.getStudentById(studentId);
        if (course.getCourseRegistrationList().stream()
                .anyMatch(member -> studentDto.getEmail().equals(member.getEmail())))
            throw new CourseException(CourseError.STUDENT_EXIST_ON_COURSE);
        if (!studentDto.getStatus().equals(StudentDto.Status.ACTIVE))
            throw new CourseException(CourseError.STUDENT_STATUS_NOT_ACTIVE);
    }

    public void courseValidation(Course course) {
        if (course.getParticipantsLimit() < course.getParticipantsNumber())
            throw new CourseException(CourseError.COURSE_LIMIT_REACHED);
        if (course.getStatus().equals(Course.Status.FULL)
                && course.getParticipantsNumber() < course.getParticipantsLimit())
            throw new CourseException(CourseError.INCORRECT_FULL_STATUS);
        if (course.getEndDate().isBefore(course.getStartDate()))
            throw new CourseException(CourseError.INCORRECT_END_DATE);
    }
}
