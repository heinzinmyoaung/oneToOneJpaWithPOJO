package com.example.oneToOneJpaWithPOJO.controller;


import com.example.oneToOneJpaWithPOJO.entity.Instructor;
import com.example.oneToOneJpaWithPOJO.pojo.InstructorPojo;
import com.example.oneToOneJpaWithPOJO.response.Basic;
import com.example.oneToOneJpaWithPOJO.service.InstructorService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
public class HomeController {

    @Autowired
    InstructorService instructorService;

    @GetMapping("/")
    public ResponseEntity<Basic> getInstructorAll(){
        return instructorService.getInstructorAll();
    }

    @GetMapping("/test/{message}")
    public ResponseEntity<String> test(
            @PathVariable("message")
            @NotNull(message = "Message cannot be null")
            @Size(min = 3, max = 10, message = "Message size must be between 3 and 10")
            String message) {
        return ResponseEntity.ok("Test response: " + message);
    }

//    @GetMapping("/error")
//    public String throwError() {
//        throw new RuntimeException("This is a fake error for testing purposes.");
//    }

    @GetMapping("/{id}")
    public Instructor getInstructorById(@PathVariable ("id") int id){
        throw new RuntimeException("This is a fake error for testing purposes.");
    }

    @PostMapping("/")
    public ResponseEntity<Basic> createInstructor(@Valid @RequestBody InstructorPojo instructorPojo){
        return instructorService.createInstructor(instructorPojo);
    }

    @PutMapping("/update")
    public String updateInstructor(@RequestParam(name = "id") int instructorId,
                                   @RequestBody Instructor instructor) {

        return instructorService.updateInstructor(instructorId,instructor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Basic> deleteInstructor(@PathVariable ("id")  int id) {
        return instructorService.deleteInstructor(id);
    }
}

