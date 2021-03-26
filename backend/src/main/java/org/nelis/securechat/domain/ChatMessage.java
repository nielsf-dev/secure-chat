package org.nelis.securechat.domain;

import javax.persistence.*;

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

    @Column(name = "text")
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
