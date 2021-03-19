package org.nelis.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Een ChatMessage, kan emoticons bevatten en tekst
 */
@Entity
@Table(name = "chatmessage")
public class ChatMessage {
    @Id
    @GeneratedValue(strategy=IDENTITY)
    private Long id;
    private String message;

    protected ChatMessage() {
    }

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
