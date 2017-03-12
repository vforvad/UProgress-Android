package com.vsokoltsov.uprogress.user.current;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by vsokoltsov on 10.01.17.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserResponse {
    @JsonProperty("current_user")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
