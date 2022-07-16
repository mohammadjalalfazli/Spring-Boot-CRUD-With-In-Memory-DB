package com.bezkoder.spring.jpa.h2.service;


import com.bezkoder.spring.jpa.h2.entity.Account;
import com.bezkoder.spring.jpa.h2.model.ValidationModel;
import com.bezkoder.spring.jpa.h2.repository.AccountRepository;
import lombok.var;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.persistence.UniqueConstraint;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    private String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    // Find Item According to the Provided Id
    public Optional<Account> FindById(long id){
        return accountRepository.findById(id);
    }

    // Return all Item
    public List<Account> GetList(){
        return accountRepository.findAll();
    }

    // Create if the given model dos'nt have Id and edit when provided Id
    public Account CreateOrEdit(Account request){

        Account account = accountRepository.save(new Account(
                request.getId(),
                request.getFirstName(),
                request.getLastName(),
                request.getEmail(),
                request.getDateOfBirth()));
            return  account;
    }

    // Create if the given model dos'nt have Id and edit when provided Id
    public List<Account> CreateAll(List<Account> request){
        List<Account> accounts = accountRepository.saveAll(request);
        return  accounts;
    }

    // Delete the Item According to the provided Id
    public Optional<Account> Delete(long id){

        Optional<Account> account = accountRepository.findById(id);
        if(account.isPresent()){
            accountRepository.deleteById(id);
        }
        else{
            throw new EntityNotFoundException("Account with provided Id is not exist");
        }
        return account;
    }


    // Search All Item by the Search Creteria Rather than Id
    public List<Account> Search(Account request){

        List<Account> accounts = accountRepository.findAll();
        List<Account> res = new ArrayList<>();

        for (Account x :accounts )
        {
            res.add(x);
        }

        for (Account account :accounts)
        {
            if(request.getFirstName() != "" && !account.getFirstName().contains(request.getFirstName()) ){
                res.remove(account);
                continue;
            }
            if(request.getLastName() != "" && !account.getLastName().contains( request.getLastName())){
                res.remove(account);
                continue;
            }
            if(request.getEmail() != "" && !account.getEmail().contains(request.getEmail()) ){
                res.remove(account);
                continue;
            }
        }
        return res;
    }


    // Check Validation of Request
    public ValidationModel isValid(Account request){
        ValidationModel response= new ValidationModel(){};
        response.setIsValid(true);

        String Message ="";
        if(request.getFirstName() == "" || request.getFirstName() == null){
            Message += "First Name is Invalid";
            response.setIsValid(false);
        }
        if(request.getLastName() =="" || request.getLastName() == null){
            Message += "Last Name is Invalid";
            response.setIsValid(false);
        }
        if(ValidateEmail(request.getEmail())){
            Message += "The Email Address Provided Is Not Valid";
            response.setIsValid(false);
        }
        if(request.getDateOfBirth().after( Date.valueOf(LocalDate.now()))){
            Message += "Date Of Birth is Invalid";
            response.setIsValid(false);
        }

        response.setMessage(Message);
        return  response;
    }

    // Check Email Duplication
    public Boolean ValidateEmail(String email){
        Account account = accountRepository.findByEmail(email);
        if(isValidEmail(email)){
            if(account == null){
                return false;
            }
        }
        return true;
    }

    // Check Valid Email
    public Boolean isValidEmail(String email){
        return Pattern.compile(regexPattern).matcher(email).matches();
    }
}
