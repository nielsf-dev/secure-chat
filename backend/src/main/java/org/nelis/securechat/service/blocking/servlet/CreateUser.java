package org.nelis.securechat.service.blocking.servlet;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.hibernate.SessionFactory;
import org.nelis.securechat.JsonHelper;
import org.nelis.securechat.service.blocking.ChatRoomManager;

import java.io.IOException;

public class CreateUser implements ChatServletCommand {
    private ChatRoomManager chatRoomManager;
    private ObjectMapper objectMapper;

    public CreateUser(ChatRoomManager chatRoomManager, ObjectMapper objectMapper)  {
        this.chatRoomManager = chatRoomManager;
        this.objectMapper = objectMapper;
    }

    @Override
    public String getCommandURI() {
        return "/createuser";
    }

    @Override
    public String getResponse(String request) throws IOException {
        // chatroom aanmaken obv naam
        JsonNode jsonNode = objectMapper.readTree(request);
        String chatRoomName = jsonNode.get("name").asText();
        Long userId = chatRoomManager.createUser(chatRoomName);

        // id returnen
        ObjectNode result = objectMapper.createObjectNode();
        result.put("id", userId);
        return JsonHelper.objectNodeToString(objectMapper, result);
    }
}
