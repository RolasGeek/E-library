
package com.studies.restService;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.studies.entity.User;
import com.studies.logic.UserLogic;
import com.studies.service.UserService;


@Path("users")
public class UserRestService {

   
    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_XML)
    public String getRegister(User user) throws UnsupportedEncodingException{
    	System.out.println("REGISTER");
    	if(user.getUsername() != null && user.getPassword() != null && user.getEmail() != null){
    		if (UserService.getInstance().usernameExists(user.getUsername())){
    			return "userExists";
    		}
    		else if (UserService.getInstance().emailExists(user.getEmail())){
    			return "emailExists";
    		}
			String hashedPassword = UserLogic.getInstance().getSHA512SecurePassword(user.getPassword(), "E-Library");
    		user.setPassword(hashedPassword);
    		if (UserService.getInstance().createUser(user)){
    			return "created";
    		}
    		else{
    			return "failed";
    		}
    	}
    	return "unexpected";
    }
    
    
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_XML)
    public String getLogin(User user) throws UnsupportedEncodingException {
    	Integer state = null;
    	if(user.getUsername() != null && user.getPassword() != null) {
	    	String hashedPassword = UserLogic.getInstance().getSHA512SecurePassword(user.getPassword(), "E-Library");
	    	User u = UserService.getInstance().hasUser(user.getUsername());
	    	if(u != null ) {
	    		if(u.getUsername().toLowerCase().equals(user.getUsername().toLowerCase()) && u.getPassword().equals(hashedPassword)) {
	    			state = 2;
	    		} else if (u.getUsername().toLowerCase().equals(user.getUsername().toLowerCase()) && !u.getPassword().equals(hashedPassword)) {
	    			state =  1;
	    			return state.toString();
	    		} else {
	    			state = 0;
	    		}
    	} 
    	else{
    		state = 0;
    	}
    	
    	if(state ==  2) {
    		String token = UserService.getInstance().updateDate(u);
    		String s = UserLogic.getInstance().decodeToken(token).toString();
    		return token; 
    	}
    
    	}
    	else{
    		state = 0;
    	}
		return  state.toString();
    }
    
    
}