package org.nelis.securechat.service.blocking.servlet.commands;

import java.io.IOException;

public interface Command {
    String getCommandURI();
    String getResponse(String request) throws IOException;
}
