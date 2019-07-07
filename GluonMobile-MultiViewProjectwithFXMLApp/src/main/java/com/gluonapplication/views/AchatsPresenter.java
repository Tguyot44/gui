package com.gluonapplication.views;

import com.gluonapplication.object.Achat;
import com.gluonapplication.object.Pharmacie;
import com.gluonapplication.object.Produit;
import com.gluonhq.charm.glisten.animation.RotateInDownRightTransition;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class AchatsPresenter {
    @FXML
    private View achat;
    @FXML
    private ChoiceBox<String> choix;
    private final ObservableList<String> data =
            FXCollections.observableArrayList();
    private final ObservableList<Achat> donnee =
            FXCollections.observableArrayList();
    @FXML
    private TableView<Achat> table;

    public void initialize() {
        achat.setShowTransitionFactory(RotateInDownRightTransition::new);

        achat.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = MobileApplication.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e ->
                        MobileApplication.getInstance().getDrawer().open()));
                appBar.setTitleText("Achat");
            }

        });
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
                    for (int i = 0; i < array.size(); i++) {


                        JsonObject object = array.getJsonObject(i);
                        String libelle = object.getString("libelle");
                        data.add(libelle);
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


        choix.getItems().addAll(data);


    }


    public void testbutton(javafx.event.ActionEvent actionEvent) {


        URL url = null;
        try {
            url = new URL("http://localhost:8080/get/PharmacieByLibelle?libelle=" + choix.getValue());
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
                    affichage(inputLine);


                }
                in.close();


            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (ProtocolException e) {
            e.printStackTrace();
        }


    }

    private void affichage(String inputLine) throws IOException {

        TableColumn client = new TableColumn("Client");
        client.setMinWidth(100);
        client.setCellValueFactory(
                new PropertyValueFactory<Achat, String>("Client"));

        TableColumn pharmacie = new TableColumn("Pharmacie");
        pharmacie.setMinWidth(100);
        pharmacie.setCellValueFactory(
                new PropertyValueFactory<Achat, String>("Pharmacie"));

        TableColumn dmo = new TableColumn("DMO");
        dmo.setMinWidth(100);
        dmo.setCellValueFactory(
                new PropertyValueFactory<Achat, String>("DMO"));

        TableColumn produit = new TableColumn("Produit");
        produit.setMinWidth(100);
        produit.setCellValueFactory(
                new PropertyValueFactory<Achat, String>("Produit"));

        URL url = null;
        try {
            url = new URL("http://localhost:8080/get/AllAchatsbyPharmacie?Pharmacie=" + inputLine);
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
                String inputLine1;
                StringBuffer content = new StringBuffer();

                while ((inputLine1 = in.readLine()) != null) {
                    content.append(inputLine1);
                    JsonReader jsonReader = Json.createReader(new StringReader(inputLine1));
                    JsonArray array = jsonReader.readArray();
                    for (int i = 0; i<array.size(); i++){


                        JsonObject object = array.getJsonObject(i);

                        Achat achat = new Achat(object.getJsonObject("idClient").getString("mail"),object.getJsonObject("idPharmacie").getString("libelle"),object.getJsonObject("idDmo").getString("nom"),object.getJsonObject("produit").getString("libelle"));


                       achat.setclient(object.getJsonObject("idClient").getString("mail"));
                        achat.setpharmacie(object.getJsonObject("idPharmacie").getString("libelle"));
                        achat.setdmo(object.getJsonObject("idDmo").getString("nom"));
                        achat.setproduit(object.getJsonObject("produit").getString("libelle"));
                        donnee.add(achat);
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

        table.setItems(donnee);
        table.getColumns().addAll(client,pharmacie,dmo,produit);

    }
}

