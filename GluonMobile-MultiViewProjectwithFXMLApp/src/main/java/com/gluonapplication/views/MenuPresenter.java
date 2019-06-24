package com.gluonapplication.views;

import com.gluonhq.charm.down.Services;
import com.gluonhq.charm.down.plugins.PositionService;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
        Services.get(PositionService.class).ifPresent(service -> {
            System.out.println("Waiting for GPS signal");

            service.positionProperty().addListener((obs, ov, nv) ->
                    System.out.println("Latest known GPS coordinates:\n" + nv.getLatitude() + ", " + nv.getLongitude()));
        });
        System.out.println("is it missing ?");
    }

}
