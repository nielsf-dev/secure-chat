package org.nelis.securechat;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Server;
import org.apache.catalina.startup.Tomcat;
import org.nelis.securechat.domain.ChatRoom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import java.io.File;

public class TomcatHelper {
    private static Logger logger = LoggerFactory.getLogger(TomcatHelper.class);

    public static void startTomcat(Servlet servlet, int port) {
        try {
            Tomcat tomcat = new Tomcat();
            tomcat.setPort(port);

            String absolutePath = new File(".").getAbsolutePath();
            Context ctx = tomcat.addContext("/", absolutePath);

            Tomcat.addServlet(ctx, "coolio", servlet);
            ctx.addServletMapping("/*", "coolio");

            tomcat.start();
            tomcat.getServer().await();
        }
        catch (Exception exception){
            logger.error("Fout bij starten tomcat", exception);
        }
    }
}
