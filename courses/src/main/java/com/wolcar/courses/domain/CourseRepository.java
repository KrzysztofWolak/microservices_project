package com.wolcar.courses.domain;

import com.wolcar.courses.domain.Course;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CourseRepository extends MongoRepository<Course, String> {

    List<Course> findAllByStatus (Course.Status status);
}
