package org.nelis.domain;

public class ChatRoomMessage {
    Long id;
    User user;
    ChatRoom chatRoom;
    ChatMessage chatMessage;

    public ChatRoomMessage(User user, ChatRoom chatRoom, ChatMessage chatMessage) {
        this.user = user;
        this.chatRoom = chatRoom;
        this.chatMessage = chatMessage;
    }
}
