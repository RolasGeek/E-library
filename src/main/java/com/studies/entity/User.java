package com.studies.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the users database table.
 * 
 */
@Entity
@Table(name="users")
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String username;

	private String address;

	private String email;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="expiratio_date")
	private Date expiratioDate;

	private String password;

	private String token;

	@Column(name="user_level")
	private byte userLevel;

	//bi-directional many-to-one association to Rent
	@OneToMany(mappedBy="user")
	@JsonIgnore
	private List<Rent> rents;

	//bi-directional many-to-one association to Sold
	@OneToMany(mappedBy="user")
	@JsonIgnore
	private List<Sold> solds;

	public User() {
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public byte getUserLevel() {
		return this.userLevel;
	}

	public void setUserLevel(byte userLevel) {
		this.userLevel = userLevel;
	}

	public List<Rent> getRents() {
		return this.rents;
	}

	public void setRents(List<Rent> rents) {
		this.rents = rents;
	}

	public Rent addRent(Rent rent) {
		getRents().add(rent);
		rent.setUser(this);

		return rent;
	}

	public Rent removeRent(Rent rent) {
		getRents().remove(rent);
		rent.setUser(null);

		return rent;
	}

	public List<Sold> getSolds() {
		return this.solds;
	}

	public void setSolds(List<Sold> solds) {
		this.solds = solds;
	}

	public Sold addSold(Sold sold) {
		getSolds().add(sold);
		sold.setUser(this);

		return sold;
	}

	public Sold removeSold(Sold sold) {
		getSolds().remove(sold);
		sold.setUser(null);

		return sold;
	}

}