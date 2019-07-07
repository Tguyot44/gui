package com.gluonapplication.views;

import com.gluonapplication.object.Pharmacie;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.json.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;


public class PharmaciePresenter {

    @FXML
    private View pharmacie;
    @FXML private TableView<Pharmacie> pharmatable;
    private final ObservableList<Pharmacie> data =
            FXCollections.observableArrayList();

    public void initialize() {
        pharmacie.setShowTransitionFactory(RotateInDownRightTransition::new);

        pharmacie.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = MobileApplication.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e ->
                        MobileApplication.getInstance().getDrawer().open()));
                appBar.setTitleText("Produits");
            }
            TableColumn adresse = new TableColumn("Adresse");
            adresse.setMinWidth(100);
            adresse.setCellValueFactory(
                    new PropertyValueFactory<Pharmacie, String>("Adresse"));

            TableColumn numero = new TableColumn("Numero");
            numero.setMinWidth(25);
            numero.setCellValueFactory(
                    new PropertyValueFactory<Pharmacie, String>("Numero"));

            TableColumn codePostal = new TableColumn("CodePostal");
            codePostal.setMinWidth(25);
            codePostal.setCellValueFactory(
                    new PropertyValueFactory<Pharmacie, String>("CodePostal"));

            TableColumn libelle = new TableColumn("Libelle");
            libelle.setMinWidth(200);
            libelle.setCellValueFactory(
                    new PropertyValueFactory<Pharmacie, String>("Libelle"));


            URL url = null;
            try {
                url = new URL("http://localhost:8080/get/Pharmacie");
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
                            Pharmacie pharmacie = new Pharmacie(object.getString("adresse"),object.getString("numero"),object.getString("codePostal"),object.getString("libelle"));

                            pharmacie.setAdresse(object.getString("adresse"));
                            pharmacie.setNumero(object.getString("numero"));
                            pharmacie.setCodePostal(object.getString("codePostal"));
                            pharmacie.setLibelle(object.getString("libelle"));

                            data.add(pharmacie);
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
            pharmatable.setItems(data);
            pharmatable.getColumns().addAll(adresse,numero,codePostal,libelle);

        });
    }



}

