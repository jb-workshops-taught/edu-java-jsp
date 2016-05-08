package com.codeforanyone.edujavajsp;

import org.eclipse.jetty.security.JDBCLoginService;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

public class Start {
	public static void main(String[] args) throws Exception {
		// Create a basic jetty server object that will listen on port 8080.
		Server server = new Server(8080);

		// Tell it where to find the code and jsps relative to our project.
		WebAppContext bb = new WebAppContext();
		bb.setServer(server);
		bb.setContextPath("/");
		bb.setWar("src/main/webapp");
		server.setHandler(bb);

		// Turn down logging so it's less noisy.
		server.setDumpAfterStart(false);
		server.setDumpBeforeStop(false);
		
		// Stop jetty when we exit Eclipse.
		server.setStopAtShutdown(true);

		// Configure a LoginService.
		// The name of the login service must match the Realm declared in web.xml
		JDBCLoginService dbLoginService = new JDBCLoginService();
		dbLoginService.setConfig("src/main/resources/jdbcRealm.properties");
		dbLoginService.setName("My Java Project JDBC Login Realm");
		server.addBean(dbLoginService);
		
		// Authentication is currently using basic auth, rather than form-based.  There is much work
		// to be done to use form-based auth and create pages for login, logout, etc.
		// TODO: Create web pages for login, logout, sign up, forgot password, and profile
		// Page on basic auth: http://blog.intelligencecomputing.io/middleware/3238/repost-how-to-configure-security-with-embedded-jetty

		try {
			System.out.println(">>> STARTING EMBEDDED JETTY SERVER. Click this window and press any key to stop.");
			server.start();
			System.in.read();
			System.out.println(">>> STOPPING EMBEDDED JETTY SERVER.");
			server.stop();

			// The use of server.join() the will make the current thread join
			// and wait until the server is done executing. See
			// http://docs.oracle.com/javase/7/docs/api/java/lang/Thread.html#join()
			server.join();
		} catch (Exception e) {
			// basic error reporting to console.
			e.printStackTrace();
			System.exit(1);
		}
	}
}
