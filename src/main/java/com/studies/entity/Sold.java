package com.studies.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the sold database table.
 * 
 */
@Entity
@NamedQuery(name="Sold.findAll", query="SELECT s FROM Sold s")
public class Sold implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY, generator="mySeq")
	private int id;

	@Temporal(TemporalType.DATE)
	@Column(name="date_sold")
	private Date dateSold;

	private double price;

	//bi-directional many-to-one association to Book
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name = "book_id")
	private Book book;

	//bi-directional many-to-one association to User
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name = "user_username")
	private User user;

	public Sold() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDateSold() {
		return this.dateSold;
	}

	public void setDateSold(Date dateSold) {
		this.dateSold = dateSold;
	}

	public double getPrice() {
		return this.price;
	}

	public void setPrice(double price) {
		this.price = price;
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