package com.codeforanyone.edujavajsp.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * Demonstration of how to create connections without a connection pool, but
 * using a properties file for the config.
 * 
 * @author jenny
 *
 */
public class ConnectionManager {

    private String username = null;
    private String password = null;
    private String longUrl = null;

    public ConnectionManager() throws ClassNotFoundException {
	PropertyResourceBundle dbProps = (PropertyResourceBundle) ResourceBundle.getBundle("db");
	if ("".equals(dbProps.getString("db.username"))) {
	    throw new RuntimeException(
		    "Can't understand contents of db.properties file - got empty username.");
	}
	this.username = dbProps.getString("db.username");
	this.password = dbProps.getString("db.password");

	// only need to load the driver into memory once.
	Class.forName(dbProps.getString("db.driver"));

	longUrl = dbProps.getString("db.url") + "/" + dbProps.getString("db.schema")
		+ "?useUnicode=true&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC";

	// Unicode means we can support non-English languages.
	// Turning off ssl means no encryption between the app and the db which
	// is
	// fine for educational use and less tricky to set up.
	// Some versions of the mysql driver have a bug in timezone detection;
	// the date stuff is a workaround.
    }

    public Connection getConnection() throws SQLException {
	return DriverManager.getConnection(longUrl, username, password);
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

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
	System.out.println("Testing ConnectionManager.");
	
	ConnectionManager manager = new ConnectionManager();

	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet res = null;

	try {
	    conn = manager.getConnection();
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
