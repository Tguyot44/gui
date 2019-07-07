package com.gluonapplication.views;

import com.gluonapplication.object.Produit;
import com.gluonhq.charm.glisten.animation.RotateInDownRightTransition;
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

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Map;

public class ProduitPresenter {
    @FXML
    private View produit;
    @FXML private TableView<Produit> table;
    private final ObservableList<Produit> data =
            FXCollections.observableArrayList();
    public void initialize() {
        produit.setShowTransitionFactory(RotateInDownRightTransition::new);

        produit.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = MobileApplication.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e ->
                        MobileApplication.getInstance().getDrawer().open()));
                appBar.setTitleText("Produits");
            }

            TableColumn libelle = new TableColumn("Libelle");
            libelle.setMinWidth(100);
            libelle.setCellValueFactory(
                    new PropertyValueFactory<Produit, String>("Libelle"));

            TableColumn prix = new TableColumn("Prix");
            prix.setMinWidth(100);
            prix.setCellValueFactory(
                    new PropertyValueFactory<Produit, Double>("Prix"));


        URL url = null;
        try {
            url = new URL("http://localhost:8080/get/AllProduit");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection con = null;
        try {
            con = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            con.setRequestMethod("GET");
            try {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer content = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                    JsonReader jsonReader = Json.createReader(new StringReader(inputLine));
                    JsonArray array = jsonReader.readArray();
                    for (int i = 0; i<array.size(); i++){


                    JsonObject object = array.getJsonObject(i);




                    Produit produit = new Produit(object.getString("libelle"),object.getString("prix"));

                   produit.setLibelle(object.getString("libelle"));
                   produit.setPrix(object.getString("prix"));

                    data.add(produit);
                    jsonReader.close();
                }


                }
                in.close();


            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (ProtocolException e) {
            e.printStackTrace();
        }

            table.setItems(data);
            table.getColumns().addAll(libelle,prix);
        });


    }
}
