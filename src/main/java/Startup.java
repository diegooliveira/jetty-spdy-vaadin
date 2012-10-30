import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.spdy.server.http.HTTPSPDYServerConnector;
import org.eclipse.jetty.util.ssl.SslContextFactory;

import com.vaadin.terminal.gwt.server.ApplicationServlet;

import edu.diego.vaadin.VaadinApplication;




public class Startup {

	public static void main(String[] args) throws Exception {
		
		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        context.setInitParameter("productionMode", "true");
        ServletHolder holder = new ServletHolder(ApplicationServlet.class);
        holder.setInitParameter("application", VaadinApplication.class.getName());
        context.addServlet(holder, "/*");

        SslContextFactory fact = new SslContextFactory();
        fact.setKeyStorePath("keystore.jks");
        fact.setKeyStorePassword("diego123");
        fact.setIncludeProtocols("TLSv1");
        
        Server server = new Server();
        
        HTTPSPDYServerConnector spdyConnector = new HTTPSPDYServerConnector(server, fact);
        spdyConnector.setPort(8080);
        server.addConnector(spdyConnector);
        
        server.setHandler(context);
 		server.start();
		server.join();
	}
	
}
