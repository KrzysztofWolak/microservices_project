package com.wolcar.students.repository;

import com.wolcar.students.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {


    List<Student> findAll();

    List<Student> findByName(String name);

    List<Student> findBySurnameAndNameIsNotLike(String surname, String Name);

    boolean existsByEmail(String email);

    List<Student> findAllByStatus(Student.Status status);
}
