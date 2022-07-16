package com.bezkoder.spring.jpa.h2;

import com.bezkoder.spring.jpa.h2.controller.AccountController;
import com.bezkoder.spring.jpa.h2.entity.Account;
import com.bezkoder.spring.jpa.h2.service.AccountService;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class SpringBootJpaH2ApplicationTests {


	public AccountController accountController;
	public AccountService accountService;
	public testAccountData testAccountData;
	public Account accountModel = new Account();
	@Autowired
	public SpringBootJpaH2ApplicationTests(
			AccountService accountService,
			testAccountData testAccountData,
			AccountController accountController){
		this.accountService = accountService;
		this.testAccountData = testAccountData;
		this.accountController = accountController;

		testAccountData.makeList();

		accountModel.setId(Long.valueOf(1));
		accountModel.setFirstName("Ahmad Khan");
		accountModel.setLastName("Ahmadi");
		accountModel.setEmail("ahmad@gmail.com");
		accountModel.setDateOfBirth(Date.valueOf("2000-01-01"));
	}

	@Test
	void contextLoads() {
	}

	@Test
	@Order(1)
	void testCreateMultipleAccount() {
		assertEquals(true, accountController.createMultipleAccount(testAccountData.accounts).getIsSuccessful(),"incorrect");
	}

	@Test
	@Order(2)
	void testCreateOrEditAccount() {
		assertEquals(true, accountController.createOrEditAccount(accountModel).getIsSuccessful(),"incorrect");
	}

	@Test
	@Order(3)
	void testIsValidEmail() {
		String emailAddress = "ahmadgmail.com";
		assertEquals(false,accountService.isValidEmail(emailAddress),"incorrect");
	}

	@Test
	@Order(4)
	void testCheckEmailDuplicate() {
		String emailAddress = "ahmad@gmail.com";
		assertEquals(true,accountService.isValidEmail(emailAddress),"incorrect");
	}

	@Test
	@Order(5)
	void testValidationModel() {
		assertEquals(true,accountService.isValid(accountModel).getIsValid(),"incorrect");
	}

	@Test
	@Order(6)
	void testGetAllAccount() {
		assertEquals(true,accountController.getAllAccount().getIsSuccessful(),"incorrect");
	}

	@Test
	@Order(7)
	void testFindAccountById() {
		assertEquals(false,accountController.findAccountById(10).getIsSuccessful(),"incorrect");
	}

	@Test
	@Order(8)
	void testDeleteAccountById() {
		assertEquals(false,accountController.deleteAccountById(10).getIsSuccessful(),"incorrect");
	}

	@Test
	@Order(9)
	void testSearchAccountByCriteria() {
		assertEquals(true,accountController.searchAccountByCriteria(accountModel).getIsSuccessful(),"incorrect");
	}
}
