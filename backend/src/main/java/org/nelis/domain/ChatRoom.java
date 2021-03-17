package org.nelis.domain;

import javassist.NotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * Een ChatRoom bevat een aantal gebruikers die berichten naar de chatroom kunnen sturen.
 */
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

    /**
     * Verstuur een chat bericht in deze room
     * @param userId Originating gebruiker
     * @param chatMessage Het chat bericht
     * @throws NotFoundException
     */
    public ChatRoomMessage sendChatMessage(int userId, ChatMessage chatMessage) throws NotFoundException {
        User userFrom = users.stream()
                .filter(user -> user.id == userId)
                .findAny()
                // todo: uitzoeken wat voor constructie dit is
                .orElseThrow(() -> new NotFoundException("User niet bekend: " + userId));

        ChatRoomMessage chatRoomMessage = new ChatRoomMessage(userFrom, this, chatMessage);
        messages.add(chatRoomMessage);
        return chatRoomMessage;
    }
}
