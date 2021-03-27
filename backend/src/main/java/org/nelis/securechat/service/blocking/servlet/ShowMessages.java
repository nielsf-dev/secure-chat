package org.nelis.securechat.service.blocking.servlet;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.hibernate.SessionFactory;
import org.nelis.securechat.JsonHelper;
import org.nelis.securechat.domain.ChatMessage;
import org.nelis.securechat.domain.ChatRoom;
import org.nelis.securechat.domain.ChatRoomMessage;
import org.nelis.securechat.service.blocking.dao.ChatRoomDao;

import java.io.IOException;

public class ShowMessages extends AbstractChatServletCommand {
    private ChatRoomDao chatRoomDao;
    private ObjectMapper objectMapper;

    public ShowMessages(ChatRoomDao chatRoomDao, ObjectMapper objectMapper, SessionFactory sessionFactory)  {
        super(sessionFactory);
        this.chatRoomDao = chatRoomDao;
        this.objectMapper = objectMapper;
    }

    @Override
    public String getCommandURI() {
        return "/showmessages";
    }

    @Override
    protected String doGetResponse(String request) throws Exception { // chatroom aanmaken obv naam
        JsonNode jsonNode = objectMapper.readTree(request);
        long chatroomid = jsonNode.get("chatroomid").asLong();
        ChatRoom chatRoom = chatRoomDao.find(chatroomid);
        if(chatRoom == null)
            throw new Exception("Onbekende chatroom");

        // id returnen
        ObjectNode result = objectMapper.createObjectNode();
        ArrayNode messagesNodes = result.putArray("messages");
        for (ChatRoomMessage message : chatRoom.getMessages()) {
            messagesNodes.add(message.getChatMessage().getMessage());
        }
        return JsonHelper.objectNodeToString(objectMapper, result);
    }
}
