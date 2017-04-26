package com.studies.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the users database table.
 * 
 */
@Entity
@NamedQuery(name="Users.findAll", query="SELECT u FROM Users u")
public class Users implements Serializable {
	private static final long serialVersionUID = 1L;


	private String email;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="expiratio_date")
	private Date expiratioDate;

	@Id
	private String username;

	private String password;

	private String token;

	public Users() {
	}



	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getExpiratioDate() {
		return this.expiratioDate;
	}

	public void setExpiratioDate(Date expiratioDate) {
		this.expiratioDate = expiratioDate;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}