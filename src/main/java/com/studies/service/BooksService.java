package com.studies.service;

import java.util.List;

import javax.management.Query;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.apache.commons.codec.net.QCodec;

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
			entityManager.close();
			return false;
		}
	}
	
	public Book getBook(Integer bookId) {
		EntityManager entityManager = EntityManagerClass.getInstance().getEntityManager();
		Book book = entityManager.find(Book.class, bookId);
		return book;
	}
	
	public List<Book> getAll() {
		EntityManager entityManager = EntityManagerClass.getInstance().getEntityManager();
		try{
			entityManager.getTransaction().begin();
			TypedQuery<Book> q =  entityManager.createNamedQuery("Book.findAll", Book.class);
			List<Book> result = q.getResultList();
			entityManager.getTransaction().commit();
			entityManager.close();
			return result;
		} catch (Exception ex){
			entityManager.close();
			return null;
		}
	}

}
