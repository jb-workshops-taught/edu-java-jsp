package com.codeforanyone.edujavajsp.database;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.apache.commons.dbcp2.BasicDataSource;

/**
 * A helper class for getting database connections from a persistent connection
 * pool.
 * 
 * @author jennybrown
 *
 */
public class DataSource {

	private static DataSource datasource;
	private BasicDataSource ds;

	private DataSource() throws IOException, SQLException, PropertyVetoException {
		System.out.println("Setting up connection pool.  This should only happen once.");
		
		PropertyResourceBundle dbProps = (PropertyResourceBundle) ResourceBundle.getBundle("db");
		if ("".equals(dbProps.getString("db.username"))) {
			throw new RuntimeException("Can't understand contents of db.properties file - got empty username.");
		}

		ds = new BasicDataSource();
		ds.setDriverClassName(dbProps.getString("db.driver"));
		ds.setUsername(dbProps.getString("db.username"));
		ds.setPassword(dbProps.getString("db.password"));

		// some versions of the mysql driver have a bug in timezone detection;
		// this is a workaround.
		String timezoneWorkaround = "&useLegacyDatetimeCode=false&serverTimezone=UTC";

		// general settings to make it more cooperative
		String config = "?useUnicode=true&useSSL=false";

		ds.setUrl(dbProps.getString("db.url") + dbProps.getString("db.schema") + config + timezoneWorkaround);

		// the settings below are optional -- dbcp can work with defaults
		ds.setMinIdle(5);
		ds.setMaxIdle(20);
		ds.setMaxOpenPreparedStatements(180);
	}

	/**
	 * Gets the instance of the connection pool, from which you can get
	 * individual connections.
	 * 
	 * @return
	 * @throws IOException
	 * @throws SQLException
	 * @throws PropertyVetoException
	 */
	public static DataSource getInstance()  {
		if (datasource == null) {
			try {
				datasource = new DataSource();
			} catch (IOException | SQLException | PropertyVetoException e) {
				throw new RuntimeException(e);
			}
			return datasource;
		} else {
			return datasource;
		}
	}

	/**
	 * Gets a connection from the pool.
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Connection getConnection() throws SQLException {
		return this.ds.getConnection();
	}

	public static void silentClose(ResultSet res) {
		if (res != null) {
			try {
				res.close();
			} catch (SQLException se) {
			}
		}
	}
	public static void silentClose(Statement stmt) {
		if (stmt != null) { 
			try {
				stmt.close();
			} catch (SQLException se) {
			}
		}
	}
	public static void silentClose(Connection conn) {
		if (conn != null) { 
			try {
				conn.close();
			} catch (SQLException se) {
				// do not report because it would obscure earlier exceptions.
			}
		}
	}

	/**
	 * For testing use, validates that it can connect to a database and run a
	 * simple query.  Also demonstrates proper use.
	 * 
	 * @param args
	 * @throws SQLException
	 * @throws IOException
	 * @throws PropertyVetoException
	 */
	public static void main(String[] args) throws SQLException {
		System.out.println("Testing DataSource.");

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet res = null;

		try {
			conn = DataSource.getInstance().getConnection();
			pstmt = conn.prepareStatement("select 1 as a, 2 as b");
			res = pstmt.executeQuery();
			while (res.next()) {
				System.out.println("Data row = " + res.getString(1) + ", " + res.getString(2));
			}
			System.out.println("Success.");
		} finally {
			DataSource.silentClose(res);
			DataSource.silentClose(pstmt);
			DataSource.silentClose(conn);
		}

	}
}
