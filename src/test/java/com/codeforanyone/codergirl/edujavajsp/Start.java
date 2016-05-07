package com.codeforanyone.codergirl.edujavajsp;

import org.eclipse.jetty.security.HashLoginService;
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
		// Since this example is for our test webapp, we need to setup a
		// LoginService so this shows how to create a very simple hashmap based
		// one. The name of the LoginService needs to correspond to what is
		// configured in the webapp's web.xml and since it has a lifecycle of
		// its own we register it as a bean with the Jetty server object so it
		// can be started and stopped according to the lifecycle of the server
		// itself.
		HashLoginService loginService = new HashLoginService();
		loginService.setName("Test Realm");
		loginService.setConfig("src/test/resources/realm.properties");
		server.addBean(loginService);

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
