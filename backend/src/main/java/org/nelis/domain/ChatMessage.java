package org.nelis.domain;

/**
 * Een ChatMessage, kan emoticons bevatten en tekst
 */
public class ChatMessage {
    private Long id;
    private String message;

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
