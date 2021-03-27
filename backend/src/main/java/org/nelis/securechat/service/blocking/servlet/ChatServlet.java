package org.nelis.securechat.service.blocking.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

public class ChatServlet implements Servlet {

    private static Logger logger = LoggerFactory.getLogger(ChatServlet.class);
    private ServletConfig config;

    private List<ChatServletCommand> commands;

    public ChatServlet(List<ChatServletCommand> commands) {
        this.commands = commands;
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.config = config;
    }

    @Override
    public ServletConfig getServletConfig() {
        return config;
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest)servletRequest;
        logger.info("Chatservlet request URI: {}", httpRequest.getRequestURI());
        ChatServletCommand chatServletCommand = commands.stream()
                .filter(sc -> sc.getCommandURI().equalsIgnoreCase(httpRequest.getRequestURI()))
                .findAny()
                .orElseThrow(() -> new ServletException("Command niet gevonden"));

        BufferedReader requestReader = httpRequest.getReader();
        String requestBody = requestReader.lines().collect(Collectors.joining());
        String responseBody = chatServletCommand.getResponse(requestBody);

        PrintWriter writer = servletResponse.getWriter();
        writer.write(responseBody);
        writer.flush();
        writer.close();
    }

    @Override
    public String getServletInfo() {
        return "Chat servlet";
    }

    @Override
    public void destroy() {
        logger.info("Destroying servlet..");
    }
}
