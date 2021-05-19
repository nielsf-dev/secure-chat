package org.nelis.securechat.domain;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Een ChatMessage in een ChatRoom
 */
@Entity
@Table(name = "chatroommessage")
public class ChatRoomMessage {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userid")
    private User user;

    @ManyToOne
    @JoinColumn(name = "chatroomid")
    private ChatRoom chatRoom;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "chatmessageid")
    private ChatMessage chatMessage;

    public ChatRoomMessage(ChatRoom chatRoom, User user, ChatMessage chatMessage) {
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
