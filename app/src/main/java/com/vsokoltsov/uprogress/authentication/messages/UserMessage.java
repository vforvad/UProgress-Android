package com.vsokoltsov.uprogress.authentication.messages;

import com.vsokoltsov.uprogress.user.current.User;

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

