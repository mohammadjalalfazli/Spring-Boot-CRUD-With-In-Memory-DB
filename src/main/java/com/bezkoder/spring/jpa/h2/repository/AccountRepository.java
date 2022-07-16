package com.bezkoder.spring.jpa.h2.repository;

import java.util.List;

import com.bezkoder.spring.jpa.h2.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByEmail(String email);

    List<Account> findByFirstNameContaining(String title);

    List<Account> findAccountByFirstNameAndLastNameAndEmail(String firstName, String lastName, String email);
}
