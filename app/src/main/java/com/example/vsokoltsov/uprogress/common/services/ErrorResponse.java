package com.example.vsokoltsov.uprogress.common.services;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Hashtable;

/**
 * Created by vsokoltsov on 23.11.16.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResponse {
    @JsonProperty("errors")
    private Hashtable<String, String[]> errors;

    public Hashtable<String, String[]> getErrors() {
        return errors;
    }

    public void setErrors(Hashtable<String, String[]> errors) {
        this.errors = errors;
    }

    public String getFullErrorMessage(String key) {
        String[] messages = errors.get(key);
        if (messages != null) {
            StringBuilder strBuilder = new StringBuilder();
            for (int i = 0; i < messages.length; i++) {
                strBuilder.append(messages[i] + "\n");
            }
            String newString = strBuilder.toString();
            return newString;
        }
        else {
            return null;
        }
    }
}

