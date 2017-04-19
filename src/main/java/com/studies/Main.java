package com.studies;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.studies.entity.User;

public class Main {

	public static void main(String[] args) {
		
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory( "E-library" );
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		List<User> result = entityManager.createQuery( "select u from User u", User.class ).getResultList();
		for ( User event : result ) {
		    System.out.println( "Event (" + event.getName() + ") : " + event.getId() );
		}
		entityManager.getTransaction().commit();
		entityManager.close();
	}

}
