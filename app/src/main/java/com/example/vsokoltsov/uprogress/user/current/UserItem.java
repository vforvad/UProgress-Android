package com.example.vsokoltsov.uprogress.user.current;

/**
 * Created by vsokoltsov on 08.01.17.
 */

public class UserItem {
    private final String title;
    private final String description;

    public UserItem(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }
}
