package com.studies.restService;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.studies.entity.Book;
import com.studies.logic.BooksLogic;
import com.studies.service.BooksService;
import com.studies.service.MailService;


@Path("books")
public class BooksRestService {
	@Context
	private ServletContext context;
	
	private String baseDir = "C:/Temp/Uploaded/";
	@POST
	@Path("/insert")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_XML)
	public String insertBook(@FormDataParam("image") InputStream file,  @FormDataParam("image") FormDataContentDisposition fileDetail,@FormDataParam("file1") InputStream file1,  @FormDataParam("file1") FormDataContentDisposition fileDetail1, @FormDataParam("book") String json, @Context HttpHeaders headers) throws IOException {
	String st = headers.getRequestHeaders().getFirst("Authorization");
	Book book = BooksLogic.getInstance().makeBook(json);
	//Save files
	BooksLogic.getInstance().saveFile(file, book.getFileName(".png"));
	BooksLogic.getInstance().saveFile(file1, book.getFileName(".pdf"));
	BooksService.getInstance().insertBook(book);
	return "Fuck u";
	}
	
	@GET
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	@Path("getpdf/{bookId}")
	public Response getBook(@PathParam("bookId") Integer bookId) {
		Book book = BooksService.getInstance().getBook(bookId);
		File f = new File(baseDir+book.getFileName(".pdf"));
		return Response.ok(f, MediaType.APPLICATION_OCTET_STREAM)
			      .header("Content-Disposition", "attachment; filename=\"" + f.getName() + "\"" ) //optional
			      .build();
	}
	
	@GET
	@Produces("image/png")
	@Path("get/{bookId}")
	public Response getPdf(@PathParam("bookId") Integer bookId) throws IOException {
		Book book = BooksService.getInstance().getBook(bookId);
		BufferedImage img = ImageIO.read(new File(baseDir+book.getFileName(".png"))); 
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    ImageIO.write(img, "png", baos);
	    byte[] imageData = baos.toByteArray();
	    return Response.ok(imageData).build();
	}
	
	
	@GET
	@Path("getAll")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Book> getAll() {
		return BooksService.getInstance().getAll();
	}
	
	@GET
	@Path("getSearch/{search}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Book> getSearch(@PathParam("search") String search) {
		return BooksService.getInstance().getSearch(search);
	}
	
	@GET
	@Path("sendMail")
	@Produces(MediaType.APPLICATION_XML)
	public String sendMail() {
		MailService ml = new MailService();
		ml.sendMail("C:/Temp/Uploaded/rolas - Rolas.pdf", "eivydas.senkus@gmail.com");
		return "DOne";
	}
	
	
}
