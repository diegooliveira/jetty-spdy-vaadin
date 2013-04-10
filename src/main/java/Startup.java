import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.SecureRequestCustomizer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.SslConnectionFactory;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.spdy.api.SPDY;
import org.eclipse.jetty.spdy.server.NPNServerConnectionFactory;
import org.eclipse.jetty.spdy.server.http.HTTPSPDYServerConnectionFactory;
import org.eclipse.jetty.spdy.server.http.HTTPSPDYServerConnector;
import org.eclipse.jetty.spdy.server.http.PushStrategy;
import org.eclipse.jetty.spdy.server.http.ReferrerPushStrategy;
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

        SslContextFactory sslContextFactory = new SslContextFactory();
        sslContextFactory.setKeyStorePath("keystore.jks");
        sslContextFactory.setKeyStorePassword("diego123");
        sslContextFactory.setIncludeProtocols("TLSv1");  
        
        Server server = new Server();
        
        ReferrerPushStrategy push = new ReferrerPushStrategy();
        
        HttpConfiguration tlsHttpConfig = new HttpConfiguration();
        tlsHttpConfig.setSecureScheme("https");
        tlsHttpConfig.setSecurePort(8443);
        tlsHttpConfig.setOutputBufferSize(32768);
        tlsHttpConfig.setRequestHeaderSize(8192);
        tlsHttpConfig.setResponseHeaderSize(8192);
        tlsHttpConfig.addCustomizer(new SecureRequestCustomizer());
           
        
        SslConnectionFactory sslConnectionFactory = new SslConnectionFactory(sslContextFactory, "npn");
        NPNServerConnectionFactory npnFact = new NPNServerConnectionFactory("spdy/3","spdy/2","http/1.1");
        npnFact.setDefaultProtocol("http/1.1");
        HTTPSPDYServerConnectionFactory spdy3Connector = new HTTPSPDYServerConnectionFactory(SPDY.V3, tlsHttpConfig, push);
        HTTPSPDYServerConnectionFactory spdy2Connector = new HTTPSPDYServerConnectionFactory(SPDY.V2, tlsHttpConfig);
        HttpConnectionFactory http = new HttpConnectionFactory(tlsHttpConfig);
      
        
        ServerConnector connector = new ServerConnector(server, sslConnectionFactory, npnFact, spdy3Connector, spdy2Connector, http);
        connector.setPort(8443);
        server.addConnector(connector);
       
        
        server.setHandler(context);
 		server.start();
		server.join();
	}
	
}
