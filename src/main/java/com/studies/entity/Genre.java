package com.studies.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the genre database table.
 * 
 */
@Entity
@NamedQuery(name="Genre.findAll", query="SELECT g FROM Genre g")
public class Genre implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private GenrePK id;

	//bi-directional many-to-one association to Book
	@ManyToOne
	private Book book;

	public Genre() {
	}

	public GenrePK getId() {
		return this.id;
	}

	public void setId(GenrePK id) {
		this.id = id;
	}

	public Book getBook() {
		return this.book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

}