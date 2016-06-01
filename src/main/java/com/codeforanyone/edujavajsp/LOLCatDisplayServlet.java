package com.codeforanyone.edujavajsp;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.codeforanyone.edujavajsp.database.LOLCatDAO;
import com.codeforanyone.edujavajsp.model.CatNotFoundException;
import com.codeforanyone.edujavajsp.model.LOLCat;

/**
 * Shows a random lolcat image to a visitor, and provides menu behaviors for
 * login, logout, and profile. This is a demonstration of form-based
 * authentication, as well as layers of code for retrieving data from a
 * database.
 * 
 * @author jennybrown
 *
 */
public class LOLCatDisplayServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pathInfo = req.getPathInfo();
		if (pathInfo == null || "".equals(pathInfo)) {
			show(req, resp);
		} else if (pathInfo.equals("/image")) {
			streamImage(req, resp);
		}
	}

	/** Simple front page shows one random lolcat each time it's reloaded */
	void show(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}

	/** Binary image data returned to client based on image id */
	void streamImage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id = Integer.parseInt(req.getParameter("id"));
		LOLCatDAO dao = new LOLCatDAO();
		try {
			LOLCat cat = dao.get(id);
			resp.setContentType("image/" + cat.getImageFormat());
			resp.setContentLength(cat.getImageData().length);

			ServletOutputStream out = resp.getOutputStream();
			out.write(cat.getImageData(), 0, cat.getImageData().length);
			out.close();

		} catch (SQLException e) {
			e.printStackTrace(); // no data will return.
		} catch (CatNotFoundException e) {
			System.out.println("Unknown lolcat id " + id + " requested image data."); // no data will return.
		}
	}

}
