package org.nelis.service.blocking.servlet;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface ServletCommand {
    String getCommandURI();
    String getResponse(String request) throws IOException;
}
