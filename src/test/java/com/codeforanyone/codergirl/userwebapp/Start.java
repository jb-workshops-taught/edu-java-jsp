package com.codeforanyone.codergirl.userwebapp;

import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.server.Server;
//import org.eclipse.jetty.util.thread.ScheduledExecutorScheduler;
import org.eclipse.jetty.webapp.WebAppContext;

public class Start {
	public static void main(String[] args) throws Exception {
		// Create a basic jetty server object that will listen on port 8080.
		// Note that if you set this to port 0 then a randomly available port
		// will be assigned that you can either look in the logs for the port,
		// or programmatically obtain it for use in test cases.
		Server server = new Server(8080);

		//server.addBean(new ScheduledExecutorScheduler());

		WebAppContext bb = new WebAppContext();
		bb.setServer(server);
		bb.setContextPath("/");
		bb.setWar("src/main/webapp");
		server.setHandler(bb);

		/*
		 * String jetty_base =
		 * "/Users/jennybrown/Documents/workspace/userwebapp/target"; String
		 * jetty_war_dir =
		 * "/Users/jennybrown/Documents/workspace/userwebapp/target";
		 * 
		 * File f_jetty_base = new File(jetty_war_dir); if
		 * (!f_jetty_base.isDirectory()) { boolean success =
		 * f_jetty_base.mkdirs(); } System.setProperty("jetty.base",
		 * jetty_base);
		 * 
		 * // Handler Structure HandlerCollection handlers = new
		 * HandlerCollection(); ContextHandlerCollection contexts = new
		 * ContextHandlerCollection(); handlers.setHandlers(new Handler[] {
		 * contexts, new DefaultHandler() }); server.setHandler(handlers);
		 * 
		 * DeploymentManager deployer = new DeploymentManager();
		 * deployer.setContexts(contexts); deployer.setContextAttribute(
		 * "org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern", ".*" +
		 * "/servlet-api-[^/]*\\.jar$");
		 * 
		 * WebAppProvider webapp_provider = new WebAppProvider();
		 * webapp_provider.setMonitoredDirName(jetty_war_dir);
		 * webapp_provider.setScanInterval(1);
		 * webapp_provider.setExtractWars(true);
		 * webapp_provider.setConfigurationManager(new
		 * PropertiesConfigurationManager());
		 * 
		 * deployer.addAppProvider(webapp_provider); server.addBean(deployer);
		 */

		server.setDumpAfterStart(false);
		server.setDumpBeforeStop(false);
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
			System.out.println(">>> STARTING EMBEDDED JETTY SERVER, PRESS ANY KEY TO STOP");
			server.start();
			System.in.read();
			System.out.println(">>> STOPPING EMBEDDED JETTY SERVER");
			server.stop();

			// The use of server.join() the will make the current thread join
			// and wait until the server is done executing. See
			// http://docs.oracle.com/javase/7/docs/api/java/lang/Thread.html#join()
			server.join();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}
