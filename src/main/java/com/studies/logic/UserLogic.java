package com.studies.logic;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.studies.entity.Users;
import com.studies.entityManager.EntityManagerClass;

public class UserLogic {
	 public static UserLogic singleton = null;
	 public static UserLogic getInstance() {
	     if(singleton == null) {
	        singleton = new UserLogic();
	     }
	     return singleton;
	  }
	public String getSHA512SecurePassword(String passwordToHash, String   salt) throws UnsupportedEncodingException{
		String generatedPassword = null;
		    try {
		         MessageDigest md = MessageDigest.getInstance("SHA-512");
		         md.update(salt.getBytes("UTF-8"));
		         byte[] bytes = md.digest(passwordToHash.getBytes("UTF-8"));
		         StringBuilder sb = new StringBuilder();
		         for(int i=0; i< bytes.length ;i++){
		            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
		         }
		         generatedPassword = sb.toString();
		        } 
		       catch (NoSuchAlgorithmException e){
		        e.printStackTrace();
		       }
		    return generatedPassword;
		}
	public String createToken(Users u, Date exp) {
		String token = null;
		try {
		    Algorithm algorithm = Algorithm.HMAC256("secret");
		    token = JWT.create()
		    	.withExpiresAt(exp)
		        .withIssuer(u.getUsername())
		        .withClaim("email", u.getEmail())
		        .sign(algorithm);
		} catch (UnsupportedEncodingException exception){
		    //UTF-8 encoding not supported
		} catch (JWTCreationException exception){
		    //Invalid Signing configuration / Couldn't convert Claims.
		}
		return token;
	}
	
	public DecodedJWT decodeToken( String token) {
		try {
		    DecodedJWT jwt = JWT.decode(token);
		    return jwt;
		} catch (JWTDecodeException exception){
		    //Invalid token
		}
		return null;
	}
	
	public boolean isExpired(String token) {
		DecodedJWT jwt = decodeToken(token);
		int size = token.length();
		EntityManager entityManager = EntityManagerClass.getInstance().getEntityManager();
		try {
		entityManager.getTransaction().begin();
		Query q = entityManager.createQuery("select u.expiratioDate from Users u where u.username=?1 and u.token=?2", Users.class);
		q.setParameter(1, jwt.getIssuedAt());
		q.setParameter(2, jwt.getToken());
		Date a = (Date) q.getSingleResult();
		} catch (Exception ex) {
			
		}
		
		return true;
	}
	
}
	
