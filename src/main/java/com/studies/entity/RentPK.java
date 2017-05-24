package com.studies.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the rent database table.
 * 
 */
@Embeddable
public class RentPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="user_username", insertable=false, updatable=false)
	private String userUsername;

	@Column(name="book_id", insertable=false, updatable=false)
	private int bookId;

	public RentPK() {
	}
	public String getUserUsername() {
		return this.userUsername;
	}
	public void setUserUsername(String userUsername) {
		this.userUsername = userUsername;
	}
	public int getBookId() {
		return this.bookId;
	}
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof RentPK)) {
			return false;
		}
		RentPK castOther = (RentPK)other;
		return 
			this.userUsername.equals(castOther.userUsername)
			&& (this.bookId == castOther.bookId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.userUsername.hashCode();
		hash = hash * prime + this.bookId;
		
		return hash;
	}
}