package com.wolcar.courses.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.LOCAL_DATE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CourseTest {

   private Course course;
    @BeforeEach
    void courseSet() {
        course = new Course();
        course.setParticipantsNumber(0L);
        course.setParticipantsLimit(20L);
    }
    @Test
    void participantsNumberShouldBeIncrementAfterCheckParticipantsLimit() {
        //given
        //when
        course.registerStudent("email@email.com");
        //then
        assertThat(course.getParticipantsNumber(), equalTo(1L));
    }

    @Test
    void courseStatusShouldBeChangeToFullWhenParticipantsLimitWillBeReached() {
        //given
        //when
        course.setParticipantsNumber(19L);
        course.registerStudent("email@email.com");
        //then
        assertThat(course.getStatus(), is(Course.Status.FULL));
    }
    @Test
    void registerStudentShouldAddStudentToRegistrationList() {
        //given
        //when
        course.registerStudent("mail@mail.com");
        //then
        assertThat(course.getCourseRegistrationList().get(0).getEmail(), equalTo("mail@mail.com"));
        assertThat(course.getCourseRegistrationList().get(0).getStudentJoinDate(), instanceOf(LocalDate.class));
    }


}
