package com.studies.entity;

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
	private int id;

	private String author;

	@Lob
	private String description;

	private String name;

	@Column(name="quantity_rented")
	private int quantityRented;

	@Column(name="quantity_to_rent")
	private int quantityToRent;

	@Column(name="quantity_to_sell")
	private int quantityToSell;

	private byte rentable;

	private byte sellable;

	//bi-directional many-to-one association to Genre
	@OneToMany(mappedBy="book")
	private List<Genre> genres;

	//bi-directional many-to-one association to Rent
	@OneToMany(mappedBy="book")
	private List<Rent> rents;

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

	public int getQuantityRented() {
		return this.quantityRented;
	}

	public void setQuantityRented(int quantityRented) {
		this.quantityRented = quantityRented;
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

	public byte getRentable() {
		return this.rentable;
	}

	public void setRentable(byte rentable) {
		this.rentable = rentable;
	}

	public byte getSellable() {
		return this.sellable;
	}

	public void setSellable(byte sellable) {
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
	
	public String getFileName(String type) {
		return this.author + " - " + this.name + type;
	}

}