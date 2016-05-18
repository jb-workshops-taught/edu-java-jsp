package com.codeforanyone.edujavajsp;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CrazyWordsServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");

		String servletPath = req.getServletPath();
		System.out.println("Servlet Path was " + servletPath);

		String pathInfo = req.getPathInfo();
		System.out.println("Path Info was " + pathInfo);

		if (pathInfo == null || "/".equals(pathInfo)) {
			doIntro(req, resp);
		} else if ("/morewords".equals(pathInfo)) {
			doMoreWords(req, resp);
		} else if ("/results".equals(pathInfo)) {
			doResults(req, resp);
		} else {
			PrintWriter pw = resp.getWriter();
			pw.println("Got pathInfo of " + pathInfo + " -- that's unexpected!");
		}
	}

	private void doIntro(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/words-form1.jsp").forward(req, resp);
	}

	private void doMoreWords(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("noun1", req.getParameter("noun1"));
		req.setAttribute("noun2", req.getParameter("noun2"));
		req.getRequestDispatcher("/WEB-INF/words-form2.jsp").forward(req, resp);
	}

	private void doResults(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("noun1", req.getParameter("noun1"));
		req.setAttribute("noun2", req.getParameter("noun2"));
		req.setAttribute("verb1", req.getParameter("verb1"));
		req.setAttribute("verb2", req.getParameter("verb2"));
		req.getRequestDispatcher("/WEB-INF/words-results.jsp").forward(req, resp);
	}

}
