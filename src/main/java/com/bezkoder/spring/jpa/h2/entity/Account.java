package com.bezkoder.spring.jpa.h2.entity;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "account")
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Basic
	@Column(name = "id", nullable = false)
	private Long id;

	@Basic
	@Column(name = "first_name", nullable = false)
	private String firstName;

	@Basic
	@Column(name = "last_name", nullable = false)
	private String lastName;

	@Basic
	@Column(name = "email", nullable = false,unique = true)
	private String email;

	@Basic
	@Column(name = "date_of_birth", nullable = false)
	private Date dateOfBirth;

	@Override
	public String toString() {
		return "Account [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", dateOfBirth=" + dateOfBirth + "]";
	}

}
