package com.gluonapplication.views;

import com.gluonapplication.object.pharmInfo;
import com.gluonhq.charm.down.Services;
import com.gluonhq.charm.down.plugins.PositionService;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GestionPresenter {

    public WebView mapView;
    @FXML private TableView<pharmInfo> infoTable;
    @FXML private TableColumn<pharmInfo, String> labelCol;
    @FXML private TableColumn<pharmInfo, String> resCol;
    @FXML
    private View gestion;
    private String urlCall = "http://pboucher.ddns.net/dorade/index.htm?pos=47.2052529,-1.5374115";

    public void initialize(){
        gestion.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue){
                AppBar appBar = MobileApplication.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e ->
                        MobileApplication.getInstance().getDrawer().open()));
                appBar.setTitleText("Gestion");
            }


            WebEngine engine = mapView.getEngine();
            List pos = findPosition();
            if (pos.size() != 2){
                File file = new File("src/main/resources/errorGPS.html");
                engine.load(file.toURI().toString());
                System.out.println(file.toURI().toString());
            }else{
                engine.load(urlCall);
                System.out.println("on est la!");
            }

            labelCol.setCellValueFactory(new PropertyValueFactory<pharmInfo,String>("label"));
            resCol.setCellValueFactory(new PropertyValueFactory<pharmInfo,String>("data"));
            infoTable.setItems(getPharmInfo());

        });
    }

    private ObservableList<pharmInfo> getPharmInfo() {
        ObservableList<pharmInfo> lpi = FXCollections.observableArrayList();

        lpi.add(new pharmInfo("a recup","depuis l'api"));

        return lpi;
    }

    public List<Double> findPosition(){
        List<Double> pos = new ArrayList<>();
        Services.get(PositionService.class).ifPresent(service -> {
            System.out.println("Waiting for GPS signal");

            service.positionProperty().addListener((obs, ov, nv) ->{
                    pos.add(nv.getLatitude());
                    pos.add(nv.getLongitude());
            }
            );
        });

        return pos;
    }

}
