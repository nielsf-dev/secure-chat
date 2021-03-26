package org.nelis.securechat.service.blocking.servlet;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface ChatServletCommand {
    String getCommandURI();
    String getResponse(String request) throws IOException;
}
