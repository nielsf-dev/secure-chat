package org.nelis.securechat.service.blocking.servlet;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.hibernate.SessionFactory;
import org.nelis.securechat.JsonHelper;
import org.nelis.securechat.domain.ChatMessage;
import org.nelis.securechat.service.blocking.ChatRoomManager;

import java.io.IOException;

public class SendMessage extends AbstractChatServletCommand {
    private ChatRoomManager chatRoomManager;
    private ObjectMapper objectMapper;

    public SendMessage(ChatRoomManager chatRoomManager, ObjectMapper objectMapper, SessionFactory sessionFactory)  {
        super(sessionFactory);
        this.chatRoomManager = chatRoomManager;
        this.objectMapper = objectMapper;
    }

    @Override
    public String getCommandURI() {
        return "/sendmessage";
    }

    @Override
    protected String doGetResponse(String request) throws IOException { // chatroom aanmaken obv naam
        JsonNode jsonNode = objectMapper.readTree(request);
        long userid = jsonNode.get("userid").asLong();
        long chatroomid = jsonNode.get("chatroomid").asLong();
        String message = jsonNode.get("message").asText();
        boolean success = chatRoomManager.sendChatMessage(chatroomid, userid, new ChatMessage(message));

        // id returnen
        ObjectNode result = objectMapper.createObjectNode();
        result.put("success", success);
        return JsonHelper.objectNodeToString(objectMapper, result);
    }
}