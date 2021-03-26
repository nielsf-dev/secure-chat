package org.nelis.securechat;

import io.undertow.Undertow;
import io.undertow.util.Headers;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Server;
import org.apache.catalina.startup.Tomcat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import java.io.File;

public class App {
    private static Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) throws LifecycleException {
        logger.info("logging working");
        System.out.println("yolo");

        //startUndertow();
        TomcatHelper.startTomcat(new DemoServlet(),8082);
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


}
