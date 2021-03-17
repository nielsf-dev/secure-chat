package org.nelis.domain;

/**
 * Een ChatMessage in een ChatRoom
 */
public class ChatRoomMessage {
    private Long id;
    private User user;
    private ChatRoom chatRoom;
    private ChatMessage chatMessage;

    public ChatRoomMessage(User user, ChatRoom chatRoom, ChatMessage chatMessage) {
        this.user = user;
        this.chatRoom = chatRoom;
        this.chatMessage = chatMessage;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public ChatRoom getChatRoom() {
        return chatRoom;
    }

    public ChatMessage getChatMessage() {
        return chatMessage;
    }
}
