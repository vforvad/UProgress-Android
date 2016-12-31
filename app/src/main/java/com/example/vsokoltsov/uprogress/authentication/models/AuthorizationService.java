package com.example.vsokoltsov.uprogress.authentication.models;

/**
 * Created by vsokoltsov on 23.11.16.
 */

public class AuthorizationService {
    private User currentUser;

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public static class SingletonHolder {
        public static final AuthorizationService HOLDER_INSTANCE = new AuthorizationService();
    }

    public static AuthorizationService getInstance() {
        return SingletonHolder.HOLDER_INSTANCE;
    }
}
