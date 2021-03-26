package org.nelis;

import io.undertow.Undertow;
import io.undertow.util.Headers;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class App {
    private static Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) throws LifecycleException {
        logger.info("logging working");
        System.out.println("yolo");

        //startUndertow();
        startTomcat();
    }

    private static void startUndertow() {
        Undertow server = Undertow.builder()
                .addHttpListener(8080,"localhost")
                .setHandler(exchange -> {
                                exchange.getResponseHeaders()
                                .put(Headers.CONTENT_TYPE, "text/plain");
                                exchange.getResponseSender().send("Hello Baeldung");
                           })
                .build();
        server.start();
    }

    private static void startTomcat() throws LifecycleException {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8082);

        String absolutePath = new File(".").getAbsolutePath();
        Context ctx = tomcat.addContext("/", absolutePath);

        Tomcat.addServlet(ctx,"coolio",new DemoServlet());
        ctx.addServletMapping("/*", "coolio");

        tomcat.start();
        tomcat.getServer().await();
    }
}
