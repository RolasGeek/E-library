package com.studies.restService;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import com.studies.entity.Book;
import com.studies.service.BooksService;

@Path("books")
public class BooksRestService {
	
	@POST
	@Path("/insert")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_XML)
	public String insertBook(Book book, @Context HttpHeaders headers) {
		String st = headers.getRequestHeaders().getFirst("Authorization");
		if(st != null) {
			if(BooksService.getInstance().insertBook(book)) {
				return "Success";
			}
		}
		return "Failed";
	}
	
}
