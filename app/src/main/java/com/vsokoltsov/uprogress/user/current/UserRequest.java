package com.vsokoltsov.uprogress.user.current;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by vsokoltsov on 10.01.17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRequest {
    @JsonProperty("first_name")
    final private String firstName;
    @JsonProperty("last_name")
    final private String lastName;
    @JsonProperty("email")
    final private String email;
    @JsonProperty("location")
    final private String location;
    @JsonProperty("description")
    final private String description;

    public UserRequest(String firstName, String lastName, String email, String location, String description) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.location = location;
        this.description = description;
    }
}
