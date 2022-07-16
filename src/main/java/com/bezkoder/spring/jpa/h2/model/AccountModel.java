package com.bezkoder.spring.jpa.h2.model;

 import com.bezkoder.spring.jpa.h2.entity.Account;
import lombok.Builder;
import lombok.Data;

import java.sql.Date;


@Data
@Builder
public class AccountModel  {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Date dateOfBirth;

    public  static AccountModel Map(Account entity){
       return AccountModel.builder()
               .id(entity.getId())
               .firstName(entity.getFirstName())
               .lastName(entity.getLastName())
               .email(entity.getEmail())
               .dateOfBirth(entity.getDateOfBirth())
               .build();
    }
}
