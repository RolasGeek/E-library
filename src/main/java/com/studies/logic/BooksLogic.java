package com.studies.logic;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

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
}
