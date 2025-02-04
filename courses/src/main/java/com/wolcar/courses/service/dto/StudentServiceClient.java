package com.wolcar.courses.service.dto;

import com.wolcar.courses.service.dto.StudentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "STUDENT-SERVICE")  // Spring openfeign annot
public interface StudentServiceClient {

    @GetMapping("/students")
    List<StudentDto> getStudents();

    @GetMapping("/students/{id}")
    StudentDto getStudentById(@PathVariable int id);
}
