package com.codeforanyone.codergirl.userwebapp;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HelloServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter pw = resp.getWriter();
		pw.println("Hello!");
		resp.flushBuffer();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}

	/*
	 * schema: javaproject
	 * user: javadbuser
	 * pass: javacoderocks
	 * hibernate.connection.url=jdbc:mysql://localhost:3306/javaproject
	 */
	
	
	
}
