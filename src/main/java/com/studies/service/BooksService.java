package com.studies.service;

import javax.persistence.EntityManager;

import com.studies.entity.Book;
import com.studies.entityManager.EntityManagerClass;

public class BooksService {
	private static BooksService singleton = null;
	public static BooksService getInstance() {
		if(singleton == null) {
			singleton = new BooksService();
		}
		return singleton;
	}
	
	public boolean insertBook(Book book) {
		EntityManager entityManager = EntityManagerClass.getInstance().getEntityManager();
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(book);
			entityManager.getTransaction().commit();
			entityManager.close();
			return true;
		} catch (Exception ex) {
			return false;
		}
	}
	

}
