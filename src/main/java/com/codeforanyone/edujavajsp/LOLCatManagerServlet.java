package com.codeforanyone.edujavajsp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.codeforanyone.edujavajsp.database.LOLCatDAO;
import com.codeforanyone.edujavajsp.model.CatNotFoundException;
import com.codeforanyone.edujavajsp.model.LOLCat;

/**
 * Handles all the features that let a logged-in administrator add and remove
 * lolcat images.
 * 
 * @author jennybrown
 *
 */
@MultipartConfig // enables file-upload from multipart html forms
public class LOLCatManagerServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pathInfo = req.getPathInfo();
		if (pathInfo == null || "".equals(pathInfo)) {
			list(req, resp);
		} else if (pathInfo.equals("/add")) {
			addForm(req, resp);
		} else if (pathInfo.equals("/edit")) {
			editForm(req, resp);
		} else if (pathInfo.equals("/delete")) {
			deleteConfirm(req, resp);
		} else if (pathInfo.equals("/aftersave")) {
			afterSave(req, resp);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pathInfo = req.getPathInfo();
		if (pathInfo == null || "".equals(pathInfo)) {
			list(req, resp);
		} else if (pathInfo.equals("/save")) {
			save(req, resp);
		}
	}

	void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		LOLCatDAO dao = new LOLCatDAO();
		List<LOLCat> cats = null;
		try {
			cats = dao.list();
			req.setAttribute("cats", cats);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		req.getRequestDispatcher("/WEB-INF/lolcats/list.jsp").forward(req, resp);
	}

	void addForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/lolcats/add.jsp").forward(req, resp);
	}

	void editForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/lolcats/edit.jsp").forward(req, resp);
	}

	void deleteConfirm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/lolcats/delete-confirm.jsp").forward(req, resp);
	}

	/** From a post, saves the newly added or modified LOLcat */
	void save(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//dumpRequest(req);  // debug
		
		LOLCatDAO dao = new LOLCatDAO();
		String result = null;

		if (req.getParameter("id") != null) {
			// edit-existing scenario since we have an id already
			try {
				LOLCat cat = dao.get(Integer.parseInt(req.getParameter("id")));
				cat.setTitle(req.getParameter("title"));
				cat.setFilename(req.getParameter("filename"));
				cat.setImageFormat(req.getParameter("format"));

				// Retrieves <input type="file" name="file">
				Part filePart = req.getPart("image");
				String fileName = filePart.getName(); // TODO
				System.out.println("DEBUG - fileName is " + fileName);
				byte[] imageData = readDataStream(filePart.getInputStream());
				cat.setImageData(imageData);

				dao.save(cat);
				result = "Changes saved!";
			} catch (CatNotFoundException e) {
				result = "Error - LOLCat not found with that id! Edit aborted.";
			} catch (SQLException e) {
				result = "Error - A database error occurred.";
				e.printStackTrace();
			}
		} else {
			// add-new scenario since there's no id
			LOLCat cat = new LOLCat();
			cat.setTitle(req.getParameter("title"));
			cat.setFilename(req.getParameter("filename"));
			cat.setImageFormat(req.getParameter("format"));

			// Retrieves <input type="file" name="file">
			Part filePart = req.getPart("image");
			String fileName = filePart.getName(); // TODO
			System.out.println("DEBUG - fileName is " + fileName);
			byte[] imageData = readDataStream(filePart.getInputStream());
			cat.setImageData(imageData);

			try {
				dao.save(cat);
				result = "Success!";
			} catch (SQLException e) {
				result = "Error - A database error occurred.";
				e.printStackTrace();
			}
		}

		// Redirect to a GET so if the user presses reload we don't get a
		// duplicate image POSTed.
		resp.sendRedirect("/lolcats/manage/aftersave?result=" + URLEncoder.encode(result, "UTF8"));

	}

	private byte[] readDataStream(InputStream fileContent) throws IOException {
		// copies all bytes from the upload input stream into an in-memory byte
		// array of uncertain size.
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		byte[] buffer = new byte[255];
		int count = 0;
		while ((count = fileContent.read(buffer)) != -1) {
			bytes.write(buffer, 0, count);
		}
		return bytes.toByteArray();
	}

	void afterSave(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("result", req.getParameter("result"));
		req.getRequestDispatcher("/WEB-INF/lolcats/after-save.jsp").forward(req, resp);
	}
	
	/** Spits out a ton of info about the request so you can troubleshoot */
	public static void dumpRequest(HttpServletRequest req)
	{
		for (String name : req.getParameterMap().keySet()) {
			System.out.println("parameter " + name + " values " + req.getParameterValues(name));
		}
		for (Cookie c : req.getCookies()) {
			System.out.println("cookie " + c.getDomain() + " " + c.getPath() + " " + c.getName() + " " + c.getValue());
		}
		Enumeration<String> headers = req.getHeaderNames();
		while(headers.hasMoreElements()) {
			String header = headers.nextElement();
			System.out.println("header " + header + " value " + req.getHeader(header));
		}
		
	}

}
