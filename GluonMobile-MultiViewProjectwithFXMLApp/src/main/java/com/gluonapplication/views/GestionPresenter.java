package com.gluonapplication.views;

import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class GestionPresenter {

    public WebView mapView;
    public TableView infoTable;
    @FXML
    private View gestion;
    private String urlCall = "https://www.google.com/maps/dir/?api=1&origin=47.2061588,-1.5391952&destination=47.2052594,-1.5373647&travelmode=driving";

    public void initialize(){
        gestion.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue){
                AppBar appBar = MobileApplication.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e ->
                        MobileApplication.getInstance().getDrawer().open()));
                appBar.setTitleText("Gestion");
                WebEngine engine = mapView.getEngine();
                engine.load(urlCall);
            }
        });
    }

}
