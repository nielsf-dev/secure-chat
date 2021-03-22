package org.nelis;

import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

public class ChatServlet implements Servlet {

    private static Logger logger = LoggerFactory.getLogger(ChatServlet.class);
    private ServletConfig config;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.config = config;
    }

    @Override
    public ServletConfig getServletConfig() {
        return config;
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {

        HttpServletRequest http = (HttpServletRequest)req;
        String requestURI = http.getRequestURI();
        logger.info(requestURI);

        for (Enumeration<String> headerNames = http.getHeaderNames(); headerNames.hasMoreElements();) {
            String headerName = headerNames.nextElement();
            String headerValue = http.getHeader(headerName);
            logger.info("{}:{}",headerName,headerValue);
        }
        logger.info("");

        String responseMsg;
        if(requestURI.equalsIgnoreCase("/conjomapima")) {
            responseMsg = "CONJOO MANNGGG tjoerie";
        }
        else{
            responseMsg = "Chat embedded JE WEET ZELF Tomcat servlet.\n";
        }

        Writer w = res.getWriter();
        w.write(responseMsg);
        w.flush();
        w.close();
    }

    @Override
    public String getServletInfo() {
        return "Pretty cool servlet";
    }

    @Override
    public void destroy() {
        logger.info("destroying servlet");
    }
}
