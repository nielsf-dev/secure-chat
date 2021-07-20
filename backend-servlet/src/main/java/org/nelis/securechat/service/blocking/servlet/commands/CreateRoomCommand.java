package org.nelis.securechat.service.blocking.servlet.commands;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.nelis.securechat.JsonHelper;
import org.nelis.securechat.service.blocking.ChatRoomManager;

import java.io.IOException;

public class CreateRoomCommand implements Command {

    private final ChatRoomManager chatRoomManager;
    private final ObjectMapper objectMapper;

    public CreateRoomCommand(ChatRoomManager chatRoomManager, ObjectMapper objectMapper)  {
        this.chatRoomManager = chatRoomManager;
        this.objectMapper = objectMapper;
    }

    @Override
    public String getCommandURI() {
        return "/createroom";
    }

    @Override
    public String getResponse(String request) throws IOException {
        JsonNode jsonNode = objectMapper.readTree(request);
        String chatRoomName = jsonNode.get("name").asText();
        Long chatRoomID = chatRoomManager.createChatRoom(chatRoomName);

        // id returnen
        ObjectNode result = objectMapper.createObjectNode();
        result.put("id", chatRoomID);
        return JsonHelper.objectNodeToString(result, objectMapper);
    }
}
