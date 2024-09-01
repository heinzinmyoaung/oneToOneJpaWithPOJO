package com.example.oneToOneJpaWithPOJO.service.Impl;

import com.example.oneToOneJpaWithPOJO.entity.Instructor;
import com.example.oneToOneJpaWithPOJO.entity.InstructorDetail;
import com.example.oneToOneJpaWithPOJO.pojo.InstructorDetailPojo;
import com.example.oneToOneJpaWithPOJO.pojo.InstructorPojo;
import com.example.oneToOneJpaWithPOJO.repository.InstructorRepo;
import com.example.oneToOneJpaWithPOJO.response.Basic;
import com.example.oneToOneJpaWithPOJO.service.InstructorService;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.oneToOneJpaWithPOJO.response.ResponseFactory;
//            Pageable pageable = PageRequest.of(0, 10);
//            List<Instructor> instructors = instructorRepo.findAll(pageable).getContent();;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class InstructorServiceImpl implements InstructorService {

    @Autowired
    ResponseFactory responseFactory;
    @Autowired
    InstructorRepo instructorRepo;

    @Override
    public ResponseEntity<Basic> getInstructorAll() {
        try {
            long time = System.currentTimeMillis();         // get currentMiliSecond
            List<Instructor> instructors = instructorRepo.findAll(); //get AllInstructor from DataBase
            String duration = String.valueOf(System.currentTimeMillis() - time)  + "ms";  // get processingTime

            return responseFactory.buildSuccess(HttpStatus.OK, duration, instructors, "SUCCESS", "Success");
        }catch (Exception e){
            log.error("Error: {}, {}", e.getMessage());
            return  responseFactory.buildError(HttpStatus.BAD_REQUEST, "FAIL", "Fail");
        }
    }
    @Override
    public Instructor getInstructorById(int id) {
        return instructorRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }

    @Override
    public ResponseEntity<Basic> createInstructor(InstructorPojo instructorPojo) {

        System.out.println("SFSFSSFSFSFFSF");
        // Manual mapping
        InstructorDetail instructorDetail = new InstructorDetail();
        instructorDetail.setYoutubeChannel(instructorPojo.getInstructorDetail().getYoutubeChannel());
        instructorDetail.setHobby(instructorPojo.getInstructorDetail().getHobby());

        Instructor instructor = new Instructor();
        instructor.setFirstName(instructorPojo.getFirstName());
        instructor.setLastName(instructorPojo.getLastName());
        instructor.setEmail(instructorPojo.getEmail());
        instructor.setInstructorDetail(instructorDetail);
        //

        // auto mapping with using ModelMapper
//        ModelMapper modelMapper = new ModelMapper();
//        Instructor instructor = modelMapper.map(instructorPojo, Instructor.class);
        try {
            long time = System.currentTimeMillis();// get currentMiliSecond
            instructorRepo.save(instructor);  //create new Instructor to DataBase
            String duration = String.valueOf(System.currentTimeMillis() - time)  + "ms";// get processingTime

            return responseFactory.buildSuccess(HttpStatus.OK, duration, instructor, "SUCCESS",
                    "Instructor created unsuccessfully");

        }catch (Exception e){
            log.error("Error: {}, {}",  e.getMessage());

            String getErrorMessages = ((ConstraintViolationException) e).getConstraintViolations().stream()
                    .map( constViolation -> constViolation.getMessage())
                    .collect(Collectors.joining(", "));

            return  responseFactory.buildError(HttpStatus.BAD_REQUEST, "FAIL",
                    getErrorMessages);
        }
    }
    @Override
    public String updateInstructor(int id, Instructor instructor) {
        Optional<Instructor> optionalInstructor = instructorRepo.findById(id);

        if (!optionalInstructor.isPresent()){return "Account not found";}
        // Get the existing instructor
        Instructor existingInstructor = optionalInstructor.get();

        existingInstructor.setFirstName(instructor.getFirstName());
        existingInstructor.setLastName(instructor.getLastName());
        existingInstructor.setEmail(instructor.getEmail());
        existingInstructor.setInstructorDetail(instructor.getInstructorDetail());
        try {
            instructorRepo.save(existingInstructor);
            return "Instructor updated successfully";
        }catch (Exception e){
            log.error("Error: {}, {}", e.getMessage());
            return  "Instructor updated unsuccessfully";
        }
    }
    @Override
    public ResponseEntity<Basic> deleteInstructor(int id) {
        if (instructorRepo.existsById(id)) {
            long time = System.currentTimeMillis(); // get currentMiliSecond
            instructorRepo.deleteById(id); ///remove Instructor from DataBase
            String duration = String.valueOf(System.currentTimeMillis() - time) + "ms"; // get processingTime
            return responseFactory.buildSuccess(HttpStatus.OK,duration,null, "SUCCESS",
                    "Instructor deleted unsuccessfully");
        }else {
            return responseFactory.buildError(HttpStatus.BAD_REQUEST, "FAIL", "Id not found");
        }
    }




}
