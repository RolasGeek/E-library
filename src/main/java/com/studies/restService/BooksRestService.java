package com.studies.restService;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.studies.entity.Book;
import com.studies.logic.BooksLogic;


@Path("books")
public class BooksRestService {
	@Context
	private ServletContext context;
	
	@POST
	@Path("/insert")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_XML)
	public String insertBook(@FormDataParam("file") InputStream file,  @FormDataParam("file") FormDataContentDisposition fileDetail, @FormDataParam("book") String json, @Context HttpHeaders headers) throws IOException {
	String st = headers.getRequestHeaders().getFirst("Authorization");
	ObjectMapper mapper = new ObjectMapper();
	Book book = mapper.readValue(json, Book.class);
    //String uploadedFileLocation = context.getRealPath("images/"+ fileDetail.getFileName());  pagal tai kur yra projektas
	String uploadedFileLocation = "D:/Project/Uploaded/" + book.getName() + "_" + fileDetail.getFileName();
	    // save it
	    try {
	        BooksLogic.getInstance().writeToFile(file, uploadedFileLocation);
	    } catch(Exception e) {
	        return "no";
	    }
	    return "yes";
//		
//		if(st != null) {
//			if(BooksService.getInstance().insertBook(book)) {
//				return "Succes	s";
//			}
//		}
	}
	
}
