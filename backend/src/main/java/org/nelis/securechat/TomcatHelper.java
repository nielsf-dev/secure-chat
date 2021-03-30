package org.nelis.securechat;

import org.apache.catalina.Context;
import org.apache.catalina.Server;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.FilterDef;
import org.apache.tomcat.util.descriptor.web.FilterMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Filter;
import javax.servlet.Servlet;
import java.io.File;

public class TomcatHelper {
    private static Logger logger = LoggerFactory.getLogger(TomcatHelper.class);

    public static void startTomcat(Servlet servlet, Filter filter, int port) {
        try {
            Tomcat tomcat = new Tomcat();
            tomcat.setPort(port);

            String absolutePath = new File(".").getAbsolutePath();
            Context ctx = tomcat.addContext("/", absolutePath);

            Tomcat.addServlet(ctx, "servlet", servlet);
            ctx.addServletMapping("/*", "servlet");

            FilterDef filterDef = new FilterDef();
            filterDef.setFilter(filter);
            filterDef.setFilterName("filter");
            ctx.addFilterDef(filterDef);

            FilterMap filterMap = new FilterMap();
            filterMap.setFilterName("filter");
            filterMap.addURLPattern("/*");
            ctx.addFilterMap(filterMap);

            tomcat.start();
            Server server = tomcat.getServer();
            server.await();
        }
        catch (Exception exception){
            logger.error("Fout bij starten tomcat", exception);
        }
    }
}
