package com.studies.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the books database table.
 * 
 */
@Entity
@Table(name="books")
@NamedQuery(name="Book.findAll", query="SELECT b FROM Book b")
public class Book implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY, generator="mySeq")
	private int id;

	private String author;

	@Lob
	private String description;

	private String name;

	private double price;

	@Column(name="quantity_to_rent")
	private int quantityToRent;

	@Column(name="quantity_to_sell")
	private int quantityToSell;

	@Column(name="rentable")
	private boolean rentable;

	@Column(name="sellable")
	private boolean sellable;

	//bi-directional many-to-one association to Genre
	@OneToMany(mappedBy="book")
	@JsonIgnore
	private List<Genre> genres;

	//bi-directional many-to-one association to Rent
	@OneToMany(mappedBy="book", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	@JsonIgnore
	private List<Rent> rents;

	//bi-directional many-to-one association to Sold
	@OneToMany(mappedBy="book", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	@JsonIgnore
	private List<Sold> solds;

	public Book() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return this.price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantityToRent() {
		return this.quantityToRent;
	}

	public void setQuantityToRent(int quantityToRent) {
		this.quantityToRent = quantityToRent;
	}

	public int getQuantityToSell() {
		return this.quantityToSell;
	}

	public void setQuantityToSell(int quantityToSell) {
		this.quantityToSell = quantityToSell;
	}

	public boolean getRentable() {
		return this.rentable;
	}

	public void setRentable(boolean rentable) {
		this.rentable = rentable;
	}

	public boolean getSellable() {
		return this.sellable;
	}

	public void setSellable(boolean sellable) {
		this.sellable = sellable;
	}

	public List<Genre> getGenres() {
		return this.genres;
	}

	public void setGenres(List<Genre> genres) {
		this.genres = genres;
	}

	public Genre addGenre(Genre genre) {
		getGenres().add(genre);
		genre.setBook(this);

		return genre;
	}

	public Genre removeGenre(Genre genre) {
		getGenres().remove(genre);
		genre.setBook(null);

		return genre;
	}

	public List<Rent> getRents() {
		return this.rents;
	}

	public void setRents(List<Rent> rents) {
		this.rents = rents;
	}

	public Rent addRent(Rent rent) {
		getRents().add(rent);
		rent.setBook(this);

		return rent;
	}

	public Rent removeRent(Rent rent) {
		getRents().remove(rent);
		rent.setBook(null);

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
		sold.setBook(this);

		return sold;
	}

	public Sold removeSold(Sold sold) {
		getSolds().remove(sold);
		sold.setBook(null);

		return sold;
	}
	public String getFileName(String type) {
		return author + " - " + name + type;
	}

}