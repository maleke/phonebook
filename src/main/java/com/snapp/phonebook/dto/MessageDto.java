package com.snapp.phonebook.dto;


import com.snapp.phonebook.enums.MessageType;

import java.io.Serializable;

public class MessageDto implements Serializable{

    //region properties

    private String message;

    private MessageType type;

    //endregion

    //region constructor

    public MessageDto() {
        super();
    }

    public MessageDto(MessageType type, String message) {
        super();
        this.message = message;
        this.type = type;
    }
    //endregion

    //region getter and setter

    public String getMessage() {
        return message;
    }

    public MessageDto setMessage(String message) {
        this.message = message;
        return this;
    }

    public MessageType getType() {
        return type;
    }

    public MessageDto setType(MessageType type) {
        this.type = type;
        return this;
    }
}
