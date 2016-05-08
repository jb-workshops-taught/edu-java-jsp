package com.codeforanyone.edujavajsp;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.util.log.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloServlet extends HttpServlet {
    static Logger log = LoggerFactory.getLogger(HelloServlet.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		log.debug("Received a doGet() request");
		resp.setContentType("text/html"); // What does it do if you set "text/plain" ? Try it!
		
		PrintWriter pw = resp.getWriter();
		String firstname = "";
		if (req.getParameter("firstname") != null) {
			firstname = req.getParameter("firstname");
	 		pw.println("<h1>Hello, " + firstname + "!</h1>");
	 	} else {
	 		pw.println("<h1>Hello!</h1>");
	 	}
		
		pw.close();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		log.debug("Received a doPost() request");
		super.doPost(req, resp);
	}

	/*
	 * schema: javaproject
	 * user: javadbuser
	 * pass: javacoderocks
	 * hibernate.connection.url=jdbc:mysql://localhost:3306/javaproject
	 */
	
	
	
}
