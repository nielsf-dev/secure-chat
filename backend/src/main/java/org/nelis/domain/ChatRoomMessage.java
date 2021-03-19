package org.nelis.domain;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Een ChatMessage in een ChatRoom
 */
@Entity
@Table(name = "chatroommessage")
public class ChatRoomMessage {
    @Id
    @GeneratedValue(strategy=IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private ChatRoom chatRoom;

    @ManyToOne
    private ChatMessage chatMessage;

    public ChatRoomMessage(User user, ChatRoom chatRoom, ChatMessage chatMessage) {
        this.user = user;
        this.chatRoom = chatRoom;
        this.chatMessage = chatMessage;
    }

    protected ChatRoomMessage() {

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
