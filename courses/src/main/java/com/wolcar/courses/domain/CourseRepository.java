package com.wolcar.courses.domain;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CourseRepository extends JpaRepository<Course, String> {

    List<Course> findAllByStatus (Course.Status status);
}
