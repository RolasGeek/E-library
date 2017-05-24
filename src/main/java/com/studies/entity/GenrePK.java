package com.studies.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the genre database table.
 * 
 */
@Embeddable
public class GenrePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="book_id", insertable=false, updatable=false)
	private int bookId;

	private String genre;

	public GenrePK() {
	}
	public int getBookId() {
		return this.bookId;
	}
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	public String getGenre() {
		return this.genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof GenrePK)) {
			return false;
		}
		GenrePK castOther = (GenrePK)other;
		return 
			(this.bookId == castOther.bookId)
			&& this.genre.equals(castOther.genre);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.bookId;
		hash = hash * prime + this.genre.hashCode();
		
		return hash;
	}
}