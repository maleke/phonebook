package com.snapp.phonebook.dto;


import com.snapp.phonebook.enums.MessageType;

import java.io.Serializable;

public class MessageDTO implements Serializable{

    //region properties

    private String message;

    private MessageType type;

    //endregion

    //region constructor

    public MessageDTO() {
        super();
    }

    public MessageDTO(MessageType type, String message) {
        super();
        this.message = message;
        this.type = type;
    }
    //endregion

    //region getter and setter

    public String getMessage() {
        return message;
    }

    public MessageDTO setMessage(String message) {
        this.message = message;
        return this;
    }

    public MessageType getType() {
        return type;
    }

    public MessageDTO setType(MessageType type) {
        this.type = type;
        return this;
    }
}
