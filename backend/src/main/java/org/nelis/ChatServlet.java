package org.nelis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import java.io.IOException;
import java.io.Writer;

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
        Writer w = res.getWriter();
        w.write("Chat embedded JE WEET ZELF Tomcat servlet.\n");
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
