package org.nelis.securechat.service.blocking.servlet.commands;

import java.io.IOException;

/***
 * Een command die uitgevoerd kan worden door de chat servlet
 */
public interface Command {

    /**
     * De URI van de command
     * @return URI als string
     */
    String getCommandURI();

    /**
     * Haalt de response op voor deze command
     * @param request De request als json
     * @return Response als json
     * @throws IOException als fout gaat
     */
    String getResponse(String request) throws IOException;
}
