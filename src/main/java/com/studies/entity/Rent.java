package com.studies.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the rent database table.
 * 
 */
@Entity
@NamedQuery(name="Rent.findAll", query="SELECT r FROM Rent r")
public class Rent implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private RentPK id;

	@Temporal(TemporalType.DATE)
	@Column(name="due_date")
	private Date dueDate;

	//bi-directional many-to-one association to Book
	@ManyToOne
	private Book book;

	//bi-directional many-to-one association to User
	@ManyToOne
	private User user;

	public Rent() {
	}

	public RentPK getId() {
		return this.id;
	}

	public void setId(RentPK id) {
		this.id = id;
	}

	public Date getDueDate() {
		return this.dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Book getBook() {
		return this.book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}