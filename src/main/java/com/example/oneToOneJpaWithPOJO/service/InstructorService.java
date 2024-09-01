package com.example.oneToOneJpaWithPOJO.service;

import com.example.oneToOneJpaWithPOJO.entity.Instructor;
import com.example.oneToOneJpaWithPOJO.pojo.InstructorPojo;
import com.example.oneToOneJpaWithPOJO.response.Basic;
import org.springframework.http.ResponseEntity;

public interface InstructorService {

//    List<Instructor> getInstructorAll();
    ResponseEntity<Basic> getInstructorAll();

    Instructor getInstructorById(int id);

//    String createInstructor(Instructor instructor);
    ResponseEntity<Basic> createInstructor(InstructorPojo instructorPojo);
    String updateInstructor(int id, Instructor instructor);

    ResponseEntity<Basic> deleteInstructor(int id);
}









