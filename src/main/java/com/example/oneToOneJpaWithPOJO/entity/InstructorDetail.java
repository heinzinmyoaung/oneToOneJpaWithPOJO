package com.example.oneToOneJpaWithPOJO.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "instructor_detail")
@Getter
@Setter
@NoArgsConstructor
public class InstructorDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "youtube_channel", length = 128)
    private String youtubeChannel;

    @Column(name = "hobby", length = 45)
    private String hobby;
}


