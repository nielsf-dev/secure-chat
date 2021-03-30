package org.nelis.securechat.service.blocking.servlet.commands;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.nelis.securechat.JsonHelper;
import org.nelis.securechat.domain.ChatMessage;
import org.nelis.securechat.service.blocking.ChatRoomManager;

import java.io.IOException;

public class SendMessageCommand implements Command {
    private ChatRoomManager chatRoomManager;
    private ObjectMapper objectMapper;

    public SendMessageCommand(ChatRoomManager chatRoomManager, ObjectMapper objectMapper)  {
        this.chatRoomManager = chatRoomManager;
        this.objectMapper = objectMapper;
    }

    @Override
    public String getCommandURI() {
        return "/sendmessage";
    }

    @Override
    public String getResponse(String request) throws IOException {
        JsonNode jsonNode = objectMapper.readTree(request);
        long userid = jsonNode.get("userid").asLong();
        long chatroomid = jsonNode.get("chatroomid").asLong();
        String message = jsonNode.get("message").asText();
        chatRoomManager.addUserToRoom(chatroomid, userid);
        boolean success = chatRoomManager.sendChatMessage(chatroomid, userid, new ChatMessage(message));

        // id returnen
        ObjectNode result = objectMapper.createObjectNode();
        result.put("success", success);
        return JsonHelper.objectNodeToString(objectMapper, result);
    }
}