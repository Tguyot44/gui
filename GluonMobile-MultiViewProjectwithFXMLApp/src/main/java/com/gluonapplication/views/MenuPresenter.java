package com.gluonapplication.views;

import com.gluonapplication.DrawerManager;
import com.gluonhq.charm.down.Services;
import com.gluonhq.charm.down.plugins.PositionService;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import httpHelper.HttpHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class MenuPresenter {

    @FXML
    private View menu;


    public void initialize() {
        menu.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = MobileApplication.getInstance().getAppBar();
                appBar.setTitleText("Login");
            }
        });
        HttpHelper hh = new HttpHelper("/Marco");
        if(!hh.execute().toUpperCase().equals("POLO")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Could Not reach the API, Check your connection.");
            alert.showAndWait();
        }
    }

    public void signIn(ActionEvent actionEvent) {
        menu.getApplication().getDrawer().open();
    }
}
