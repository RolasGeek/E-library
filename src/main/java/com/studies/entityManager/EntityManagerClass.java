package com.studies.entityManager;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class EntityManagerClass {
	private EntityManagerFactory entityManagerFactory = null;
	//Not singelton just saving some space
	private static EntityManagerClass singleton = null;
	public static EntityManagerClass getInstance() {
		 if(singleton == null) {
			singleton = new EntityManagerClass();
	        singleton.setEntityManagerFactory(Persistence.createEntityManagerFactory( "E-library" ));
		 }
	        return singleton;
	  }
	


	public EntityManagerFactory getEntityManagerFactory() {
		return entityManagerFactory;
	}


	public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}
	
	
}
