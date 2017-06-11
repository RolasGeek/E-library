
package com.studies.restService;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;

import javax.print.attribute.standard.Media;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.studies.entity.User;
import com.studies.helpers.Mapper;
import com.studies.logic.BooksLogic;
import com.studies.logic.UserLogic;
import com.studies.service.BooksService;
import com.studies.service.UserService;


@Path("users")
public class UserRestService {

	@GET
	@Path("/getAll")
	@Produces(MediaType.APPLICATION_JSON)
	public String getUsers() {
		return Mapper.getInstance().entityToJSON(UserService.getInstance().getAll());
	}
	
	@POST
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateUser(User user) {
		if(UserService.getInstance().update(user)) {
			return Response.ok().build();
		}
		return Response.status(400).build();
	}
   
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
    @Produces(MediaType.APPLICATION_JSON)
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
    		User usr = UserService.getInstance().login(u);
    		return Mapper.getInstance().entityToJSON(usr); 
    	}
    
    	}
    	else{
    		state = 0;
    	}
		return  null;
    }
    
    
}