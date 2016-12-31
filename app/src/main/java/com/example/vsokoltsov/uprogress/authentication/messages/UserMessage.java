package com.example.vsokoltsov.uprogress.authentication.messages;

import com.example.vsokoltsov.uprogress.user.User;

/**
 * Created by vsokoltsov on 23.11.16.
 */

public class UserMessage {
    public String operationName;
    public User user;

    public UserMessage(String name, User user){
        this.operationName = name;
        this.user = user;
    }
}

