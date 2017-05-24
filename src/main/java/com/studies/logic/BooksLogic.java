package com.studies.logic;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.studies.entity.Book;

public class BooksLogic {
	
	public static BooksLogic singleton = null;
	public static BooksLogic getInstance() {
	if(singleton == null) {
		singleton = new BooksLogic();
	}
	return singleton;
	}

	public void writeToFile(InputStream uploadedInputStream, String uploadedFileLocation) throws Exception {
	    OutputStream out = new FileOutputStream(new File(uploadedFileLocation));
	    int read = 0;
	    byte[] bytes = new byte[1024];

	    out = new FileOutputStream(new File(uploadedFileLocation));
	    while ((read = uploadedInputStream.read(bytes)) != -1) {
	        out.write(bytes, 0, read);
	    }
	    out.flush();
	    out.close();
	}
	
	public Book makeBook(String json) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		Book book = mapper.readValue(json, Book.class);
		return book;
	}
	
	public String getFileExtentionFromName(String name) {
		return name.substring(name.length()-4, name.length());
	}
	
	public boolean saveFile(InputStream file, String fileName) {
		String uploadedFileLocation = "D:/Project/Uploaded/" + fileName;
	    // save it
	    try {
	        BooksLogic.getInstance().writeToFile(file, uploadedFileLocation);
	        return true;
	    } catch(Exception e) {
	        return false;
	    }
	}
}
