package com.example.oneToOneJpaWithPOJO.pojo;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class InstructorPojo {

    private String firstName;

    @NotNull(message = "last_name is required")
    @Size(min = 2, max = 30, message = "Last name must be between 2 and 50 characters")
    private String lastName;

    @Email(message = "Email should be valid")
    private String email;

    @NotNull(message = "instructor detail is required")
    private InstructorDetailPojo instructorDetail;
}





