package com.gluonapplication.views;

import com.gluonapplication.object.Client;
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

public class ContactPresenter {

    @FXML
    private View contact;
    @FXML private TableView<Client> table;
    private final ObservableList<Client> data =
            FXCollections.observableArrayList();



    public void initialize() {
        contact.setShowTransitionFactory(RotateInDownRightTransition::new);

        contact.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = MobileApplication.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e ->
                        MobileApplication.getInstance().getDrawer().open()));
                appBar.setTitleText("Contact");
            }

        TableColumn mail = new TableColumn("Mail");
        mail.setMinWidth(100);
        mail.setCellValueFactory(
                new PropertyValueFactory<Client, String>("mail"));

        TableColumn numero = new TableColumn("Numero");
        numero.setMinWidth(25);
        numero.setCellValueFactory(
                new PropertyValueFactory<Client, String>("numero_telephone"));

        TableColumn nom = new TableColumn("Nom");
        nom.setMinWidth(25);
        nom.setCellValueFactory(
                new PropertyValueFactory<Client, String>("nom"));

        TableColumn prenom = new TableColumn("Prenom");
        prenom.setMinWidth(200);
        prenom.setCellValueFactory(
                new PropertyValueFactory<Client, String>("prenom"));


            URL url = null;
            try {
                url = new URL("http://localhost:8080/get/AllClients");
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
                            Client client = new Client(object.getString("mail"),object.getString("nom"),object.getString("numeroTelephone"),object.getString("prenom"));

                            client.setMail(object.getString("mail"));
                            client.setNom(object.getString("nom"));
                            client.setNumero_telephone(object.getString("numeroTelephone"));
                            client.setPrenom(object.getString("prenom"));

                            data.add(client);
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
            table.getColumns().addAll(mail,numero,nom,prenom);


    });
    }
}
