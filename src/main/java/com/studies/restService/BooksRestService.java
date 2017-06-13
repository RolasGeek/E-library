package com.studies.restService;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.print.attribute.standard.Media;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.studies.entity.User;
import com.studies.helpers.Mapper;
import com.studies.service.UserService;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import com.studies.entity.Book;
import com.studies.entity.Genre;
import com.studies.entity.GenrePK;
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
	public String insertBook(@FormDataParam("image") InputStream file,
			@FormDataParam("image") FormDataContentDisposition fileDetail, @FormDataParam("file1") InputStream file1,
			@FormDataParam("file1") FormDataContentDisposition fileDetail1, @FormDataParam("book") String json,
			@FormDataParam("genres") String genres, @Context HttpHeaders headers) throws IOException {
		String st = headers.getRequestHeaders().getFirst("Authorization");
		Book book = BooksLogic.getInstance().makeBook(json);
		//BooksService.getInstance().updateBook(book);
		BooksService.getInstance().deleteBookGenres(book.getId());
		book.setRents(BooksService.getInstance().getRents(book.getId()));
		book.setSolds(BooksService.getInstance().getSolds(book.getId()));
		book = BooksService.getInstance().updateBook(book);
		List<GenrePK> gnr = BooksLogic.getInstance().makeGenres(genres);
		if (book.getId() != 0) {
			// Update files
			List<Genre> genre = new ArrayList<>();
			for(GenrePK genrePK : gnr){
				genrePK.setBookId(book.getId());
				Genre g = new Genre();
				g.setId(genrePK);
				g.setBook(book);
				book.addGenre(g);
			}

			//book.setGenres(genre);
			BooksService.getInstance().updateBook(book);
			if (fileDetail.getFileName() != null) {
				File f = new File(baseDir + book.getFileName(".png"));
				f.delete();
				BooksLogic.getInstance().saveFile(file, book.getFileName(".png"));
			}
			if (fileDetail1.getFileName() != null) {
				File f = new File(baseDir + book.getFileName(".pdf"));
				f.delete();
				BooksLogic.getInstance().saveFile(file1, book.getFileName(".pdf"));
			}
		} else {
			// Save files
			BooksLogic.getInstance().saveFile(file, book.getFileName(".png"));
			BooksLogic.getInstance().saveFile(file1, book.getFileName(".pdf"));
			book = BooksService.getInstance().insertBook(book);
			for (GenrePK genrePK : gnr) {
				genrePK.setBookId(book.getId());
				Genre g = new Genre();
				g.setId(genrePK);
				book.addGenre(g);
			}
			BooksService.getInstance().updateBook(book);
		}
		return "Fuck u";
	}

	@POST
	@Path("/rentBook")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_XML)
	public String rentBook(@FormDataParam("book") Integer bookId, @FormDataParam("user") String username, @FormDataParam("toHome") boolean toHome, @Context HttpHeaders headers) throws IOException {
		if (BooksService.getInstance().isAlreadyRented(bookId, username)){
			return "User has already rented this book!";
		}

		Book book = BooksService.getInstance().getBook(bookId);

		if (book.getQuantityToRent() <= 0) {
			return "Not enough books to rent";
		}
		if (!book.getRentable()) {
			return "Book is not rentable";
		}

		User user = UserService.getInstance().getUser(username);
		BooksService.getInstance().rentBook(book, user);
		if (toHome){
			String subject = "Rent to home from E-library";
			String body = "User: " + username + " has rented book: '" + book.getAuthor() + " - " + book.getName()
						+ "' to home at address: " + user.getAddress();
			sendMail(null, "eivyses@gmail.com", subject, body);
			return "Book has successfully been rented, please wait for administrator to send it to your address";
		}
		return "Book has been successfully rented";
	}

	@POST
	@Path("/buyBook")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_XML)
	public String buyBook(@FormDataParam("book") Integer bookId, @FormDataParam("user") String username, @FormDataParam("toHome") boolean toHome, @Context HttpHeaders headers) throws IOException {
		Book book = BooksService.getInstance().getBook(bookId);

		if (book.getQuantityToSell() <= 0) {
			return "Not enough books to be sold";
		}
		if (!book.getSellable()) {
			return "Book is not sellable";
		}

		User user = UserService.getInstance().getUser(username);
		BooksService.getInstance().buyBook(book, user);
		String fileLink = baseDir + book.getAuthor() + " - " + book.getName() + ".pdf";
		sendMail(fileLink, "eivyses@gmail.com");
		return "Book has successfully been purchased, email with book pdf has been send to you";
	}

	@GET
	@Path("getRents/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getRents(@PathParam("username") String username) throws IOException{
		return Mapper.getInstance().objectToJSON(BooksService.getInstance().getRents(username));
	}


	@GET
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	@Path("getpdf/{bookId}")
	public Response getBookPdf(@PathParam("bookId") Integer bookId) {
		Book book = BooksService.getInstance().getBook(bookId);
		File f = new File(baseDir + book.getFileName(".pdf"));
		return Response.ok(f, MediaType.APPLICATION_OCTET_STREAM)
				.header("Content-Disposition", "attachment; filename=\"" + f.getName() + "\"") // optional
				.build();
	}

	@GET
	@Produces("image/png")
	@Path("get/{bookId}")
	public Response getBookImage(@PathParam("bookId") Integer bookId) throws IOException {
		Book book = BooksService.getInstance().getBook(bookId);
		BufferedImage img = ImageIO.read(new File(baseDir + book.getFileName(".png")));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(img, "png", baos);
		byte[] imageData = baos.toByteArray();
		return Response.ok(imageData).build();
	}

	@GET
	@Path("getBook/{bookId}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getBook(@PathParam("bookId") Integer bookId) throws IOException{
		return Mapper.getInstance().objectToJSON(BooksService.getInstance().getBook(bookId));
	}

	@GET
	@Path("getBookGenres/{bookId}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getBookGenres(@PathParam("bookId") Integer bookId) throws  IOException{
		return Mapper.getInstance().objectToJSON(BooksService.getInstance().getBook(bookId).getGenres());
	}

	@GET
	@Path("delete/{bookId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSearch(@PathParam("bookId") Integer bookId) {
		if (BooksService.getInstance().remove(bookId)) {
			return Response.ok().build();
		}
		return Response.status(400).build();
	}

	@GET
	@Path("getAll")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAll() throws JsonProcessingException {
		return Mapper.getInstance().objectToJSON(BooksService.getInstance().getAll());
	}

	@GET
	@Path("getSearch/{search}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getSearch(@PathParam("search") String search) {
		return Mapper.getInstance().objectToJSON(BooksService.getInstance().getSearch(search));
	}

	@GET
	@Path("sendMail")
	@Produces(MediaType.APPLICATION_XML)
	public String sendMail() {
		MailService ml = new MailService();
		ml.sendMail("C:/Temp/Uploaded/rolas - Rolas.pdf", "eivydas.senkus@gmail.com");
		return "Done";
	}

	/**
	 * Send mail to user with default subject and body
	 * @param link file location to be attached
	 * @param to email of recipient
	 */
	public void sendMail(String link, String to){
		MailService ml = new MailService();
		ml.sendMail(link, to);
	}

	/**
	 * Send mail to user
	 * @param link file location to be attached
	 * @param to email of recipient
	 * @param subject subject of email
	 * @param body body of email
	 */
	public void sendMail(String link, String to, String subject, String body){
		MailService ml = new MailService();
		ml.sendMail(link, to, subject, body);
	}

}