package com.bezkoder.spring.jpa.h2.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.var;

import com.bezkoder.spring.jpa.h2.model.ResponseModel;
import com.bezkoder.spring.jpa.h2.model.ValidationModel;
import com.bezkoder.spring.jpa.h2.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import com.bezkoder.spring.jpa.h2.entity.Account;
import javax.persistence.EntityNotFoundException;


@CrossOrigin(origins = "http://localhost:8082")
@RestController
@RequestMapping("/api")
public class AccountController {

	@Autowired
	AccountService accountService;

	public AccountController(AccountService accountService){ this.accountService = accountService;}

    // This function is used for find an account by provided id
    @GetMapping("/findById/{id}")
    public ResponseModel findAccountById(@PathVariable("id") long id) {
        ResponseModel responseModel = new ResponseModel();
        try {
            Optional<Account> account = accountService.FindById(id);
            responseModel.setData(account);
            responseModel.setIsSuccessful(true);
            responseModel.setStatus (HttpStatus.OK);
            if(account.isPresent()) {
                responseModel.setMessage("Account Successfully Founded!");
            }
            else{
                throw new EntityNotFoundException("Account With Provided Id Is Not Exist!");
            }
        } catch (Exception e) {
            responseModel.setIsSuccessful(false);
            responseModel.setStatus(HttpStatus.NOT_FOUND);
            responseModel.setMessage(e.toString());
        }
        return responseModel;
    }

    // This function is used for Create Or Edit the one Account
    @PostMapping("/save")
    public ResponseModel createOrEditAccount(@RequestBody Account request) {
         ResponseModel responseModel = new ResponseModel();
            try {
                if(request.getId() == 0){
                    ValidationModel validate = accountService.isValid(request);
                    if(!validate.getIsValid()){
                        throw new RuntimeException(validate.getMessage());
                    }
                }
                Account Data = accountService.CreateOrEdit(request);
                responseModel.setIsSuccessful(true);
                responseModel.setStatus (HttpStatus.CREATED);
                responseModel.setMessage("Account Successfully Created!");
                responseModel.setData(Data);

             }
            catch (Exception e) {
                responseModel = new ResponseModel();
                responseModel.setIsSuccessful(false);
                responseModel.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
                responseModel.setMessage(e.toString());
            }
        return responseModel;
    }

    // This function is used for create multiple Account
    @PostMapping("/saveAll")
    public ResponseModel createMultipleAccount(@RequestBody List<Account> request) {
	    System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhh");
        ResponseModel responseModel = new ResponseModel();
        try {
            for (Account accountItem : request) {
                ValidationModel validate = accountService.isValid(accountItem);
                if(!validate.getIsValid()){
                    throw new RuntimeException(validate.getMessage());
                }
            }
            List<Account> accountList = new ArrayList<>();
            accountList = accountService.CreateAll(request);
            responseModel.setIsSuccessful(true);
            responseModel.setStatus (HttpStatus.CREATED);
            responseModel.setMessage("All Accounts Successfully Created!");
            responseModel.setData(accountList);

        }
        catch (Exception e) {
            responseModel = new ResponseModel();
            responseModel.setIsSuccessful(false);
            responseModel.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            responseModel.setMessage(e.toString());
        }
        return responseModel;
    }

    // This function is used for list all account
    @GetMapping("/list")
    public ResponseModel getAllAccount() {
        ResponseModel responseModel = new ResponseModel();
        try {
            var list = accountService.GetList();
            responseModel.setIsSuccessful(true);
            responseModel.setStatus (HttpStatus.OK);
            responseModel.setMessage("All Accounts Successfully Retrieved!");
            responseModel.setData(list);
            if(list.size() == 0){
                responseModel.setMessage("There Is No Account Available Right Now!");
                responseModel.setStatus(HttpStatus.NO_CONTENT);
            }

        } catch (Exception e) {

            responseModel.setIsSuccessful(false);
            responseModel.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            responseModel.setMessage(e.toString());
        }
        return responseModel;
    }


    // This function is used for delete an account by provided Id
    @DeleteMapping("/deleteById/{id}")
    public ResponseModel deleteAccountById(@PathVariable("id") long id) {
        ResponseModel responseModel = new ResponseModel();
        try {
            responseModel.setIsSuccessful(true);
            responseModel.setStatus (HttpStatus.OK);
            responseModel.setMessage("Account Successfully Deleted!");
            responseModel.setData(accountService.Delete(id));

        } catch (Exception e) {
            responseModel.setIsSuccessful(false);
            responseModel.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            responseModel.setMessage(e.toString());
        }
        return responseModel;
    }

    // This function is used for search account by provided criteria (FirstName, LastName, Email)
    @GetMapping("/search")
    public ResponseModel searchAccountByCriteria(@RequestBody Account request) {
        ResponseModel responseModel = new ResponseModel();
        try {
            var list = accountService.Search(request);
            responseModel.setIsSuccessful(true);
            responseModel.setStatus (HttpStatus.OK);
            responseModel.setMessage("Accounts Successfully Founded!");
            responseModel.setData(list);
            if(list.size() == 0){
                responseModel.setMessage(" There Is No Accounts With Such Criteria!");
                responseModel.setStatus(HttpStatus.NO_CONTENT);
            }

        } catch (Exception e) {

            responseModel.setIsSuccessful(false);
            responseModel.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            responseModel.setMessage(e.toString());
        }
        return responseModel;
    }
}
