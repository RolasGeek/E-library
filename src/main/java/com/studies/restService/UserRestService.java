
package com.studies.restService;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.studies.UserLogic.UserLogic;
import com.studies.annotationFilters.Secured;
import com.studies.entity.Users;
import com.studies.service.UserService;


@Path("users")
public class UserRestService {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Users getDefaultUserInJSON() {
        
        return UserService.getInstance().getDefaultUser();
    }
   
    @POST
    @Secured
    @Path("/createAccount")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_XML)
    public String getRegister(Users user){
    	
    	System.out.println("REGISTER");
    	return "a";
    }
    
    
    @POST
    @Secured
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_XML)
    public String getLogin(Users user) throws UnsupportedEncodingException {
    	System.out.println(user.getUsername());
    	Integer state = null;
    	if(user.getUsername() != null && user.getPassword() != null) {
    	String hashedPassword = UserLogic.getInstance().getSHA512SecurePassword(user.getPassword(), "E-Library");
    	System.out.println("PASSWORD: " + hashedPassword);
    	Users u = UserService.getInstance().hasUser(user.getUsername());
    	if(u != null ) {
    		if(u.getUsername().equals(user.getUsername()) && u.getPassword().equals(hashedPassword)) {
    			state = 2;
    		} else if (u.getUsername().equals(user.getUsername()) && !u.getPassword().equals(hashedPassword)) {
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