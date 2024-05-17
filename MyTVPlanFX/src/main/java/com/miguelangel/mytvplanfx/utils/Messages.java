package com.miguelangel.mytvplanfx.utils;

import javafx.scene.control.Alert;

public class Messages {
    public static void ShowError(String errorMessage){
        Alert alert = new Alert(Alert.AlertType.ERROR, errorMessage);
        alert.show();
    }
    public static void ShowMessage(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
        alert.show();
    }
}
