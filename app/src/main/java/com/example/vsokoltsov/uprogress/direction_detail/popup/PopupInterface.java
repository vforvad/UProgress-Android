package com.example.vsokoltsov.uprogress.direction_detail.popup;

/**
 * Created by vsokoltsov on 05.01.17.
 */

public interface PopupInterface {
    void successPopupOperation(Object obj);
    void failedPopupOperation(Throwable t);
}
