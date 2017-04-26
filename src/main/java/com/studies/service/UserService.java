
package com.studies.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.studies.UserLogic.UserLogic;
import com.studies.entity.Users;
import com.studies.entityManager.EntityManagerClass;
public class UserService {
	
	 public static UserService singleton = null;
	 public static UserService getInstance() {
	     if(singleton == null) {
	        singleton = new UserService();
	     }
	     return singleton;
	  }
    
    public boolean createUser(Users user){
    	EntityManager entityManager = EntityManagerClass.getInstance().getEntityManager();
    	try{
    		entityManager.getTransaction().begin();
    		//Query q = entityManager.createQuery("INSERT INTO Users(username, email, password) VALUES (?1, ?2, ?3)", Users.class);
    		entityManager.persist(user);
    		entityManager.getTransaction().commit();
    		entityManager.close();
    	}
    	catch (Exception ex){
    		return false;
    	}
    	
    	return true;
    }
    
    public Users hasUser(String username) {
    	EntityManager entityManager = EntityManagerClass.getInstance().getEntityManager();
    	try {
    	entityManager.getTransaction().begin();
    	Query q = entityManager.createQuery("select u from Users u where u.username=?1", Users.class);
    	q.setParameter(1, username);
    	Users a = (Users) q.getSingleResult();
    	entityManager.getTransaction().commit();
    	entityManager.close();
    	if(a != null) {
    		return a;
    	} else {
    		return null;
    	}
    	} catch (Exception ex) {
    	}
    		return null;
    }
    
    public boolean usernameExists(String username){
    	EntityManager entityManager = EntityManagerClass.getInstance().getEntityManager();
    	try{
    		entityManager.getTransaction().begin();
    		//username = "'" + username + "'";
        	Query q = entityManager.createQuery("select u from Users u where u.username=?1", Users.class);
        	q.setParameter(1, username);
        	Users user = (Users) q.getSingleResult();
        	if (user != null){
        		return true;
        	}
        	else{
        		return false;
        	}
    	}
    	catch (Exception ex) {
    		return false;
    	}
    }
    
    public boolean emailExists(String email){
    	EntityManager entityManager = EntityManagerClass.getInstance().getEntityManager();
    	try{
    		entityManager.getTransaction().begin();
    		//email = "'" + email + "'";
        	Query q = entityManager.createQuery("select u from Users u where u.email=?1", Users.class);
        	q.setParameter(1, email);
        	Users user = (Users) q.getSingleResult();
        	if (user != null){
        		return true;
        	}
        	else{
        		return false;
        	}
    	}
    	catch (Exception ex) {
    		return false;
    	}
    }
    
    
    public String updateDate(Users u) {
		
		Date dt = new Date();
		Calendar c = Calendar.getInstance(); 
		c.setTime(dt); 
		c.add(Calendar.HOUR, 1);
		u.setExpiratioDate(c.getTime());
		String token = UserLogic.getInstance().createToken(u,c.getTime());
		u.setToken(token);
		EntityManager entityManager = EntityManagerClass.getInstance().getEntityManager();
		
			entityManager.getTransaction().begin();
	    	entityManager.merge(u);
			entityManager.getTransaction().commit();
			entityManager.close();
			
			return token;
		
	}
    
    
    
}