package org.nelis.securechat.service.blocking.servlet.commands;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.nelis.securechat.JsonHelper;
import org.nelis.securechat.domain.ChatRoom;
import org.nelis.securechat.domain.ChatRoomMessage;
import org.nelis.securechat.service.blocking.dao.ChatRoomDao;

import java.io.IOException;

public class ShowMessagesCommand implements Command {
    private ChatRoomDao chatRoomDao;
    private ObjectMapper objectMapper;

    public ShowMessagesCommand(ChatRoomDao chatRoomDao, ObjectMapper objectMapper)  {
        this.chatRoomDao = chatRoomDao;
        this.objectMapper = objectMapper;
    }

    @Override
    public String getCommandURI() {
        return "/showmessages";
    }

    @Override
    public String getResponse(String request) throws IOException {
        JsonNode jsonNode = objectMapper.readTree(request);
        long chatroomid = jsonNode.get("chatroomid").asLong();
        ChatRoom chatRoom = chatRoomDao.find(chatroomid);
        if(chatRoom == null)
            throw new IOException("Onbekende chatroom");

        // id returnen
        ObjectNode result = objectMapper.createObjectNode();
        ArrayNode messagesNodes = result.putArray("messages");
        for (ChatRoomMessage message : chatRoom.getMessages()) {
            messagesNodes.add(message.getChatMessage().getMessage());
        }
        return JsonHelper.objectNodeToString(objectMapper, result);
    }

}
