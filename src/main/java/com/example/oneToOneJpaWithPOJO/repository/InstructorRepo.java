package com.example.oneToOneJpaWithPOJO.repository;

import com.example.oneToOneJpaWithPOJO.entity.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstructorRepo extends JpaRepository<Instructor,Integer> {

}
