package com.studies.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.studies.entity.*;
import org.apache.commons.codec.net.QCodec;

import com.studies.entityManager.EntityManagerClass;

public class BooksService {
	private static BooksService singleton = null;

	public static BooksService getInstance() {
		if (singleton == null) {
			singleton = new BooksService();
		}
		return singleton;
	}

	public Book insertBook(Book book) {
		EntityManager entityManager = EntityManagerClass.getInstance().getEntityManagerFactory().createEntityManager();
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(book);
			entityManager.flush();
			entityManager.getTransaction().commit();
			entityManager.close();

			return book;
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.close();
			return null;
		}
	}

	public Book updateBook(Book book) {
		EntityManager entityManager = EntityManagerClass.getInstance().getEntityManagerFactory().createEntityManager();
		try {
			entityManager.getTransaction().begin();
			entityManager.merge(book);
			entityManager.flush();
			entityManager.getTransaction().commit();
			entityManager.close();
			return book;
		} catch (Exception ex) {
			entityManager.close();
			return null;
		}
	}

	public boolean deleteBookGenres(Integer bookId){
		EntityManager entityManager = EntityManagerClass.getInstance().getEntityManagerFactory().createEntityManager();

		try{
			entityManager.getTransaction().begin();
			Query q = entityManager.createQuery("delete from Genre g where g.book.id = ?1", Rent.class);
			q.setParameter(1, bookId);
			q.executeUpdate();
			entityManager.flush();
			entityManager.getTransaction().commit();
			entityManager.close();
			return true;
		} catch (Exception ex){
			ex.printStackTrace();
			return false;
		}
	}

	public boolean remove(Integer bookId) {
		EntityManager entityManager = EntityManagerClass.getInstance().getEntityManagerFactory().createEntityManager();
		Book book = entityManager.find(Book.class, bookId);
		try {
			entityManager.getTransaction().begin();
			entityManager.remove(book);
			entityManager.getTransaction().commit();
			entityManager.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	public boolean rentBook(Book book, User user) {
		EntityManager entityManager = EntityManagerClass.getInstance().getEntityManagerFactory().createEntityManager();

		book.setQuantityToRent(book.getQuantityToRent() - 1);
		if (book.getQuantityToRent() <= 0) {
			book.setRentable(false);
		}

		updateBook(book);

		Rent rent = new Rent();
		rent.setBook(book);
		rent.setUser(user);

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 30);
		rent.setDueDate(cal.getTime());

		try {
			entityManager.getTransaction().begin();
			entityManager.merge(rent);
			entityManager.flush();
			entityManager.getTransaction().commit();
			entityManager.close();
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.close();
			return false;
		}
	}

	public boolean buyBook(Book book, User user) {
		EntityManager entityManager = EntityManagerClass.getInstance().getEntityManagerFactory().createEntityManager();

		book.setQuantityToSell(book.getQuantityToSell() - 1);
		if (book.getQuantityToSell() <= 0) {
			book.setSellable(false);
		}

		updateBook(book);

		Sold sold = new Sold();
		sold.setBook(book);
		sold.setUser(user);
		sold.setPrice(book.getPrice());
		sold.setDateSold(Calendar.getInstance().getTime());

		try {
			entityManager.getTransaction().begin();
			entityManager.merge(sold);
			entityManager.flush();
			entityManager.getTransaction().commit();
			entityManager.close();
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.close();
			return false;
		}
	}

	public boolean exists(Integer bookId) {
		EntityManager entityManager = EntityManagerClass.getInstance().getEntityManagerFactory().createEntityManager();
		return entityManager.find(Book.class, bookId) != null;
	}

	public Book getBook(Integer bookId) {
		EntityManager entityManager = EntityManagerClass.getInstance().getEntityManagerFactory().createEntityManager();
		Book book = entityManager.find(Book.class, bookId);
		return book;
	}

	public List<Rent> getRents(){
		EntityManager entityManager = EntityManagerClass.getInstance().getEntityManagerFactory().createEntityManager();

		entityManager.getTransaction().begin();
		Query q = entityManager.createQuery("select r from Rent r", Rent.class);
		List<Rent> rents =  q.getResultList();
		entityManager.getTransaction().commit();
		entityManager.close();
		return rents;
	}

	public List<Rent> getRents(Integer bookId){
		EntityManager entityManager = EntityManagerClass.getInstance().getEntityManagerFactory().createEntityManager();

		entityManager.getTransaction().begin();
		Query q = entityManager.createQuery("select r from Rent r where r.book.id = ?1", Rent.class);
		q.setParameter(1, bookId);
		List<Rent> rents =  q.getResultList();
		entityManager.getTransaction().commit();
		entityManager.close();
		return rents;
	}

	public List<Sold> getSolds(Integer bookId){
		EntityManager entityManager = EntityManagerClass.getInstance().getEntityManagerFactory().createEntityManager();

		entityManager.getTransaction().begin();
		Query q = entityManager.createQuery("select s from Sold s where s.book.id = ?1", Sold.class);
		q.setParameter(1, bookId);
		List<Sold> solds =  q.getResultList();
		entityManager.getTransaction().commit();
		entityManager.close();
		return solds;
	}

	public List<Rent> getRents(String username){
		EntityManager entityManager = EntityManagerClass.getInstance().getEntityManagerFactory().createEntityManager();

		entityManager.getTransaction().begin();
		Query q = entityManager.createQuery("select r from Rent r where r.user.username = ?1", Rent.class);
		q.setParameter(1, username);
		List<Rent> rents =  q.getResultList();
		entityManager.getTransaction().commit();
		entityManager.close();
		return rents;
	}

	public boolean isAlreadyRented(Integer bookId, String username){
		EntityManager entityManager = EntityManagerClass.getInstance().getEntityManagerFactory().createEntityManager();

		entityManager.getTransaction().begin();
		Query q = entityManager.createQuery("select r from Rent r where r.user.username = ?1 and r.book.id = ?2 and r.returned = false", Rent.class);
		q.setParameter(1, username);
		q.setParameter(2, bookId);
		List<Rent> rents =  q.getResultList();
		entityManager.getTransaction().commit();
		entityManager.close();
		if (rents.size() > 0){
			return true;
		}
		return false;
	}

	public List<Book> getAll() {
		EntityManager entityManager = EntityManagerClass.getInstance().getEntityManagerFactory().createEntityManager();
		try {
			entityManager.getTransaction().begin();
			TypedQuery<Book> q = entityManager.createNamedQuery("Book.findAll", Book.class);
			List<Book> result = q.getResultList();
			entityManager.getTransaction().commit();
			entityManager.close();
			return result;
		} catch (Exception ex) {
			entityManager.close();
			return null;
		}
	}

	public List<Book> getSearch(String search) {
		EntityManager entityManager = EntityManagerClass.getInstance().getEntityManagerFactory().createEntityManager();
		String auth = "", name = "";
		if (search.equals("null")) {
			return getAll();
		} else {
			if (search.contains("-")) {
				auth = "%" + search.split(" - ")[0] + "%";
				name = "%" + search.split(" - ")[1] + "%";
			} else {
				search = "%" + search + "%";
			}
		}
		try {
			entityManager.getTransaction().begin();
			Query q;
			if (!search.contains("-")) {
				q = entityManager.createQuery("select b from Book b where b.name like ?1 or b.author like ?1",
						Book.class);
				q.setParameter(1, search);
			} else {
				q = entityManager.createQuery("select b from Book b where b.name like ?1 and b.author like ?2",
						Book.class);
				q.setParameter(1, name);
				q.setParameter(2, auth);
			}
			List<Book> books = q.getResultList();
			entityManager.getTransaction().commit();
			entityManager.close();
			return books;
		} catch (Exception e) {
			entityManager.close();
			return null;
		}
	}

}
