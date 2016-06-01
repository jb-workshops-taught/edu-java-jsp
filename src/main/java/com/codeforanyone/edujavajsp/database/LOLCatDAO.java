package com.codeforanyone.edujavajsp.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.codeforanyone.edujavajsp.model.CatNotFoundException;
import com.codeforanyone.edujavajsp.model.LOLCat;

/**
 * Data access object for a LOLCat entity; this class understands how to load
 * and save LOLCat entities in a SQL database.
 * 
 * Stack Overflow has a decent explanation of the Data Access Object design
 * pattern:
 * http://stackoverflow.com/questions/19154202/data-access-object-dao-in-java
 * 
 * We're not using an interface because we don't need to implement more than one
 * of these for different databases.
 * 
 * @author jennybrown
 *
 */
public class LOLCatDAO {

	DataSource ds;

	public LOLCatDAO() {
		this.ds = DataSource.getInstance();
	}

	public void save(LOLCat cat) throws SQLException {
		System.out.println("Debug. About to save " + cat);
		if (cat.getId() != null) {
			update(cat);
		} else {
			insert(cat);
		}
	}

	private void insert(LOLCat cat) throws SQLException {
		String sql = "insert into lolcat (title, filename, image_format, image_data) values (?,?,?,?)";
		PreparedStatement pstmt = null;
		Connection conn = null;

		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, cat.getTitle());
			pstmt.setString(2, cat.getFilename());
			pstmt.setString(3, cat.getImageFormat());
			pstmt.setBytes(4, cat.getImageData());
			pstmt.executeUpdate();

			ResultSet primaryKeys = pstmt.getGeneratedKeys();
			if (primaryKeys.next()) {
				cat.setId(primaryKeys.getInt(1));
			}
		} finally {
			DataSource.silentClose(pstmt);
			DataSource.silentClose(conn);
		}
	}

	private void update(LOLCat cat) throws SQLException {
		String sql = "update lolcat set title=?, filename=?, image_format=?, image_data=? where id=?";
		PreparedStatement pstmt = null;
		Connection conn = null;

		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, cat.getTitle());
			pstmt.setString(2, cat.getFilename());
			pstmt.setString(3, cat.getImageFormat());
			pstmt.setBytes(4, cat.getImageData());
			pstmt.setInt(5, cat.getId());
			pstmt.executeUpdate();
		} finally {
			DataSource.silentClose(pstmt);
			DataSource.silentClose(conn);
		}
	}

	public void delete(LOLCat cat) throws SQLException {
		String sql = "delete from lolcat where id=?";
		PreparedStatement pstmt = null;
		Connection conn = null;

		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, cat.getId());
			pstmt.executeUpdate();
		} finally {
			DataSource.silentClose(pstmt);
			DataSource.silentClose(conn);
		}
	}

	public LOLCat get(int id) throws SQLException, CatNotFoundException {
		String sql = "select * from lolcat where id=?";

		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet res = null;

		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			res = pstmt.executeQuery();

			if (res.next()) {
				LOLCat cat = loadCat(res);
				return cat;
			} else {
				throw new CatNotFoundException("Error: Cat not found with id " + id + "!");
			}
		} finally {
			DataSource.silentClose(pstmt);
			DataSource.silentClose(conn);
		}

	}

	public List<LOLCat> list() throws SQLException {
		List<LOLCat> cats = new ArrayList<LOLCat>();
		String sql = "select * from lolcat";

		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet res = null;

		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			res = pstmt.executeQuery();

			while (res.next()) {
				LOLCat cat = loadCat(res);
				cats.add(cat);
			}
			return cats;
		} finally {
			DataSource.silentClose(pstmt);
			DataSource.silentClose(conn);
		}

	}

	public List<LOLCat> search(String text) throws SQLException {
		List<LOLCat> cats = new ArrayList<LOLCat>();
		String sql = "select * from lolcat where title like ? or filename like ?";

		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet res = null;

		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);

			// % is the wildcard character, so "title like %ppl%" would match on applesauce
			pstmt.setString(1, "%" + text + "%");
			pstmt.setString(2, "%" + text + "%");
			res = pstmt.executeQuery();

			while (res.next()) {
				LOLCat cat = loadCat(res);
				cats.add(cat);
			}
			return cats;
		} finally {
			DataSource.silentClose(pstmt);
			DataSource.silentClose(conn);
		}


	}

	private LOLCat loadCat(ResultSet res) throws SQLException {
		LOLCat cat = new LOLCat();
		cat.setId(res.getInt("id"));
		cat.setTitle(res.getString("title"));
		cat.setFilename(res.getString("filename"));
		cat.setImageFormat(res.getString("image_format"));
		cat.setImageData(res.getBytes("image_data"));
		cat.setCreated(res.getDate("created"));
		return cat;
	}

}
