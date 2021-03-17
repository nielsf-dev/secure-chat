package org.nelis.domain;

import java.util.ArrayList;
import java.util.List;

public class ChatRoom {

    List<User> users;
    List<ChatRoomMessage> messages;

    public ChatRoom() {
        this.users = new ArrayList<>();
        this.messages = new ArrayList<>();
    }

    public void insertUser(User user){
        users.add(user);
    }

    public ChatRoomMessage sendChatMessage(int userIdFrom, ChatMessage chatMessage) throws Exception {
        User userFrom = users.stream()
                .filter(user -> user.id == userIdFrom)
                .findAny()
                // todo: uitzoeken wat voor constructie dit is
                .orElseThrow(() -> new Exception("From gebruiker niet bekend: " + userIdFrom));

        ChatRoomMessage chatRoomMessage = new ChatRoomMessage(userFrom, this, chatMessage);
        messages.add(chatRoomMessage);
        return chatRoomMessage;
    }
}
