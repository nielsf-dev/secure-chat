package org.nelis;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.Writer;

public class App {
    private static Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) throws LifecycleException {
        logger.info("logging working");
        System.out.println("yolo");

        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8082);

        String absolutePath = new File(".").getAbsolutePath();
        Context ctx = tomcat.addContext("/", absolutePath);

        Tomcat.addServlet(ctx,"coolio",new ChatServlet());
        ctx.addServletMapping("/*", "coolio");

        tomcat.start();
        tomcat.getServer().await();
    }
}
