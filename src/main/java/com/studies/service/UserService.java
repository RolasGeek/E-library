
package com.studies.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.studies.entity.User;
import com.studies.entityManager.EntityManagerClass;
import com.studies.logic.UserLogic;
public class UserService {
	
	 public static UserService singleton = null;
	 public static UserService getInstance() {
	     if(singleton == null) {
	        singleton = new UserService();
	     }
	     return singleton;
	  }
    
    public boolean createUser(User user){
    	EntityManager entityManager = EntityManagerClass.getInstance().getEntityManagerFactory().createEntityManager();
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
    
    public Boolean update(User user) {
    	EntityManager entityManager = EntityManagerClass.getInstance().getEntityManagerFactory().createEntityManager();
    	try {
    		entityManager.getTransaction().begin();
    		entityManager.merge(user);
    		entityManager.getTransaction().commit();
    		entityManager.close();
    		return true;
    	}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
    }

    public boolean deleteUser(String username){
		EntityManager entityManager = EntityManagerClass.getInstance().getEntityManagerFactory().createEntityManager();
		try {
			User u = entityManager.find(User.class, username);
			entityManager.getTransaction().begin();
			entityManager.remove(u);
			entityManager.getTransaction().commit();
			entityManager.close();
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

    public User hasUser(String username) {
    	EntityManager entityManager = EntityManagerClass.getInstance().getEntityManagerFactory().createEntityManager();
    	try {
    	entityManager.getTransaction().begin();
    	Query q = entityManager.createQuery("select u from User u where u.username=?1", User.class);
    	q.setParameter(1, username);
    	User a = (User) q.getSingleResult();
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
    	EntityManager entityManager = EntityManagerClass.getInstance().getEntityManagerFactory().createEntityManager();
    	try{
    		entityManager.getTransaction().begin();
    		//username = "'" + username + "'";
        	Query q = entityManager.createQuery("select u from Users u where u.username=?1", User.class);
        	q.setParameter(1, username);
        	User user = (User) q.getSingleResult();
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

	public User getUser(String username) {
		EntityManager entityManager = EntityManagerClass.getInstance().getEntityManagerFactory().createEntityManager();
		User user = entityManager.find(User.class, username);
		return user;
	}

    public boolean emailExists(String email){
    	EntityManager entityManager = EntityManagerClass.getInstance().getEntityManagerFactory().createEntityManager();
    	try{
    		entityManager.getTransaction().begin();
    		//email = "'" + email + "'";
        	Query q = entityManager.createQuery("select u from Users u where u.email=?1", User.class);
        	q.setParameter(1, email);
        	User user = (User) q.getSingleResult();
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
    
    
    public List<User> getAll() {
    	EntityManager entityManager = EntityManagerClass.getInstance().getEntityManagerFactory().createEntityManager();
    	try{
    		entityManager.getTransaction().begin();
    		List<User> list = (List<User>) entityManager.createNamedQuery("User.findAll").getResultList();
    		entityManager.getTransaction().commit();
    		entityManager.close();
    		return list;
    	} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    	
    }
    
    public User login(User u) {
		
		Date dt = new Date();
		Calendar c = Calendar.getInstance(); 
		c.setTime(dt); 
		c.add(Calendar.HOUR, 1);
		u.setExpiratioDate(c.getTime());
		String token = UserLogic.getInstance().createToken(u,c.getTime());
		u.setToken(token);
		EntityManager entityManager = EntityManagerClass.getInstance().getEntityManagerFactory().createEntityManager();
		
			entityManager.getTransaction().begin();
	    	entityManager.merge(u);
			entityManager.getTransaction().commit();
			entityManager.close();
			
			return u;
		
	}
    
    
    
}