package org.nelis.domain;

import javassist.NotFoundException;
import org.hibernate.annotations.Cascade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Een ChatRoom bevat een aantal gebruikers die berichten naar de chatroom kunnen sturen.
 */
@Entity
@Table(name = "chatroom")
public class ChatRoom {

    private static Logger logger = LoggerFactory.getLogger(ChatRoom.class);

    @Id
    @GeneratedValue(strategy=IDENTITY)
    private Long id;

    private String name;

    @JoinTable(
            name = "chatroom_user",
            joinColumns = @JoinColumn(name="chatroomid"),
            inverseJoinColumns = @JoinColumn(name="userid"))
    @OneToMany(targetEntity = User.class)
    private List<User> users;

    @OneToMany(targetEntity = ChatRoomMessage.class, mappedBy = "chatRoom")
    private List<ChatRoomMessage> messages;

    public ChatRoom(String name) {
        this.name = name;
        this.users = new ArrayList<>();
        this.messages = new ArrayList<>();
    }

    protected ChatRoom() {

    }

    /**
     * Voeg een User toe aan de chatroom
     * @param user De User
     */
    public void insertUser(User user){
        if(!users.contains(user))
            users.add(user);
    }

    /**
     * Verstuur een chat bericht in deze room
     * @param userId Originating gebruiker
     * @param chatMessage Het chat bericht
     * @throws NotFoundException
     */
    public ChatRoomMessage sendMessage(long userId, ChatMessage chatMessage) throws NotFoundException {

        User userFrom = users.stream()
                .filter(user -> user.getId() == userId)
                .findAny()
                // todo: uitzoeken wat voor constructie dit is
                .orElseThrow(() -> {
                    String errorMsg = String.format("UserID %d niet bekend in room %d", userId, id);
                    return new NotFoundException(errorMsg);
                });

        ChatRoomMessage chatRoomMessage = new ChatRoomMessage(userFrom, this, chatMessage);
        messages.add(chatRoomMessage);
        logger.trace("sendChatMessage '{}' in ChatRoom '{}' door User '{}'", chatMessage.getMessage(), name, userFrom.getName());

        return chatRoomMessage;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<User> getUsers() {
        return users;
    }

    public List<ChatRoomMessage> getMessages() {
        return messages;
    }
}
