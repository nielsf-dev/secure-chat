package org.nelis.domain;

public class ChatMessage {
    Long id;
    String message;

    public ChatMessage(String message) {
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }
}
