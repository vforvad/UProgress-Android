package com.example.vsokoltsov.uprogress.views;

import com.example.vsokoltsov.uprogress.models.authorization.Token;

import java.io.IOException;

/**
 * Created by vsokoltsov on 24.12.16.
 */

public interface SignInView {
    void successResponse(Token token);
    void failedResponse(Throwable t) throws IOException;
}
