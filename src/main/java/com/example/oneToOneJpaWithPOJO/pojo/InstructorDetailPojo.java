package com.example.oneToOneJpaWithPOJO.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class InstructorDetailPojo {

    private String youtubeChannel;
    private String hobby;
}
