package com.bezkoder.spring.jpa.h2.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ResponseModel {

    private Boolean IsSuccessful;
    private HttpStatus Status;
    private String Message;
    private Object Data;
}
