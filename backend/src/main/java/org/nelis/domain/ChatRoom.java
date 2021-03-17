package org.nelis.domain;

import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Een ChatRoom bevat een aantal gebruikers die berichten naar de chatroom kunnen sturen.
 */
public class ChatRoom {

    private static Logger logger = LoggerFactory.getLogger(ChatRoom.class);

    private Long id;
    private String name;
    private List<User> users;
    private List<ChatRoomMessage> messages;

    public ChatRoom(String name) {
        this.name = name;
        this.users = new ArrayList<>();
        this.messages = new ArrayList<>();
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
    public ChatRoomMessage sendMessage(int userId, ChatMessage chatMessage) throws NotFoundException {

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
}
