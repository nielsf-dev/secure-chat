package org.nelis.service.blocking.servlet;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.nelis.JsonHelper;
import org.nelis.service.blocking.ChatRoomManager;
import org.nelis.service.blocking.IChatRoomManager;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

public class CreateRoom implements ServletCommand{

    private IChatRoomManager chatRoomManager;
    private ObjectMapper objectMapper;

    public CreateRoom(IChatRoomManager chatRoomManager, ObjectMapper objectMapper) {
        this.chatRoomManager = chatRoomManager;
        this.objectMapper = objectMapper;
    }

    @Override
    public String getCommandURI() {
        return "/createroom";
    }

    @Override
    public String getResponse(String request) throws IOException {
        // chatroom aanmaken obv naam
        JsonNode jsonNode = objectMapper.readTree(request);
        String chatRoomName = jsonNode.get("name").asText();
        Long chatRoomID = chatRoomManager.createChatRoom(chatRoomName);

        // id returnen
        ObjectNode result = objectMapper.createObjectNode();
        result.put("id", chatRoomID);
        return JsonHelper.objectNodeToString(objectMapper, result);
    }

}
