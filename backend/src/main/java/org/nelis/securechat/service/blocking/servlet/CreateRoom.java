package org.nelis.securechat.service.blocking.servlet;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.hibernate.SessionFactory;
import org.nelis.securechat.JsonHelper;
import org.nelis.securechat.service.blocking.ChatRoomManager;

import java.io.IOException;

public class CreateRoom extends AbstractChatServletCommand {

    private ChatRoomManager chatRoomManager;
    private ObjectMapper objectMapper;

    public CreateRoom(ChatRoomManager chatRoomManager, ObjectMapper objectMapper, SessionFactory sessionFactory)  {
        super(sessionFactory);
        this.chatRoomManager = chatRoomManager;
        this.objectMapper = objectMapper;
    }

    @Override
    public String getCommandURI() {
        return "/createroom";
    }

    @Override
    protected String doGetResponse(String request) throws IOException { // chatroom aanmaken obv naam
        JsonNode jsonNode = objectMapper.readTree(request);
        String chatRoomName = jsonNode.get("name").asText();
        Long chatRoomID = chatRoomManager.createChatRoom(chatRoomName);

        // id returnen
        ObjectNode result = objectMapper.createObjectNode();
        result.put("id", chatRoomID);
        return JsonHelper.objectNodeToString(objectMapper, result);
    }
}
