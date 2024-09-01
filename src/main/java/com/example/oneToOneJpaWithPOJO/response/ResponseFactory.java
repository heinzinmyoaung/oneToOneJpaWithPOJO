package com.example.oneToOneJpaWithPOJO.response;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class ResponseFactory {

    public ResponseEntity<Basic> buildError(HttpStatus status, String errorCode,
                                            String message) {

        Basic basic = new Basic(false,null, errorCode, message,null);
        return ResponseEntity.status(status).body(basic);
    }

    public ResponseEntity<Basic> buildSuccess(HttpStatus status,String processingTime,
                                              Object result, String errorCode,
                                              String message) {

        Basic basic = new Basic(true,processingTime,errorCode, message,result);

        return ResponseEntity.status(status).body(basic);
    }
}