package com.studies.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
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
		EntityManager entityManager = EntityManagerClass.getInstance().getEntityManagerFactory().createEntityManager();
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
		EntityManager entityManager = EntityManagerClass.getInstance().getEntityManagerFactory().createEntityManager();
		Book book = entityManager.find(Book.class, bookId);
		return book;
	}

	public List<Book> getAll() {
		EntityManager entityManager = EntityManagerClass.getInstance().getEntityManagerFactory().createEntityManager();
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

	public List<Book> getSearch(String search) {
		EntityManager entityManager = EntityManagerClass.getInstance().getEntityManagerFactory().createEntityManager();
		String auth = "", name = "";
		if(search.equals("null")) {
			return getAll();
		} else {
			if (search.contains("-")){
				auth = "%" + search.split(" - ")[0] + "%";
				name = "%" + search.split(" - ")[1] + "%";
			}
			else {
				search = "%" + search + "%";
			}
		}
		try{
			entityManager.getTransaction().begin();
			Query q;
			if (!search.contains("-")){
				q = entityManager.createQuery("select b from Book b where b.name like ?1 or b.author like ?1", Book.class);
				q.setParameter(1, search);
			}
			else{
				q = entityManager.createQuery("select b from Book b where b.name like ?1 and b.author like ?2", Book.class);
				q.setParameter(1, name);
				q.setParameter(2, auth);
			}
			List<Book> books =  q.getResultList();
			entityManager.getTransaction().commit();
			entityManager.close();
			return books;
		} catch (Exception e) {
			entityManager.close();
			return null;
			// TODO: handle exception
		}
	}

}
