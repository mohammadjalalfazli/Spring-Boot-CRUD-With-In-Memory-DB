package com.bezkoder.spring.jpa.h2.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidationModel {
    private Boolean isValid;
    private String Message;
}
