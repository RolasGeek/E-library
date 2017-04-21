
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

    public Users getDefaultUser() {
    	
		EntityManager entityManager = EntityManagerClass.getInstance().getEntityManager();
		entityManager.getTransaction().begin();
		List<Users> result = entityManager.createQuery( "select u from Users u", Users.class ).getResultList();
		for ( Users event : result ) {
		    System.out.println( "Event (" + event.getUsername() + ") : " + event.getId() );
		}
		entityManager.getTransaction().commit();
		entityManager.close();
        return result.get(0);
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
    public String updateDate(Users u) {
		
		Date dt = new Date();
		Calendar c = Calendar.getInstance(); 
		c.setTime(dt); 
		c.add(Calendar.HOUR, 1);
		u.setExpiratioDate(c.getTime());
		String token = UserLogic.getInstance().createToken(u,c.getTime());
		u.setToken(token);
		u.setEmail("testUpdate");
		EntityManager entityManager = EntityManagerClass.getInstance().getEntityManager();
		
			entityManager.getTransaction().begin();
	    	entityManager.merge(u);
			entityManager.getTransaction().commit();
			entityManager.close();
			
			return token;
		
	}
    
    
    
}