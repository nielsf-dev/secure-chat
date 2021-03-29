package org.nelis.securechat;

import org.apache.catalina.Context;
import org.apache.catalina.Server;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.FilterDef;
import org.apache.tomcat.util.descriptor.web.FilterMap;
import org.nelis.securechat.service.blocking.servlet.HibernateServletFilter;
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

            FilterDef filterDef = new FilterDef();
            filterDef.setFilter(new HibernateServletFilter());
            filterDef.setFilterName("myfilter");
            ctx.addFilterDef(filterDef);

            FilterMap filterMap = new FilterMap();
            filterMap.setFilterName("myfilter");
            filterMap.addURLPattern("/*");
            ctx.addFilterMap(filterMap);

            tomcat.start();
            Server server = tomcat.getServer();
            server.addLifecycleListener(event -> {
                logger.info("lifecycle event: {}", event.getType());
            });
            server.await();
        }
        catch (Exception exception){
            logger.error("Fout bij starten tomcat", exception);
        }
    }
}
