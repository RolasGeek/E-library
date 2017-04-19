package com.studies.entityManager;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class EntityManagerClass {
	private EntityManagerFactory entityManagerFactory = null;
	private EntityManager entityManager =  null;
	
	public static EntityManagerClass singleton = null;
	 public static EntityManagerClass getInstance() {
	        singleton = new EntityManagerClass();
	        singleton.setEntityManagerFactory(Persistence.createEntityManagerFactory( "E-library" ));
	        singleton.setEntityManager(singleton.getEntityManagerFactory().createEntityManager());
	        return singleton;
	  }
	
		
	public EntityManager getEntityManager() {
		return this.entityManager;
	}


	public EntityManagerFactory getEntityManagerFactory() {
		return entityManagerFactory;
	}


	public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}


	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	
}
