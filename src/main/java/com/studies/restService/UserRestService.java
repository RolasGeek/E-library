
package com.studies.restService;

import java.io.UnsupportedEncodingException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.studies.entity.User;
import com.studies.helpers.Mapper;
import com.studies.logic.UserLogic;
import com.studies.service.UserService;


@Path("users")
public class UserRestService {

	@GET
	@Path("/getAll")
	@Produces(MediaType.APPLICATION_JSON)
	public String getUsers() {
		return Mapper.getInstance().objectToJSON(UserService.getInstance().getAll());
	}

	@GET
	@Path("/getUser/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getUser(@PathParam("username") String username){
		return Mapper.getInstance().objectToJSON(UserService.getInstance().getUser(username));
	}

	@GET
	@Path("deleteUser/{username}")
	@Produces(MediaType.APPLICATION_XML)
	public String deleteUser(@PathParam("username") String username){
		if(UserService.getInstance().deleteUser(username)){
			return "User successfully deleted";
		}
		return "User delete failed";
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
    	Integer state;
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
    		return Mapper.getInstance().objectToJSON(usr);
    	}
    
    	}
		return  null;
    }
    
    
}