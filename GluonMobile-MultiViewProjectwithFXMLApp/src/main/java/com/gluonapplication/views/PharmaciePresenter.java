package com.gluonapplication.views;

import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
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
    @FXML
    public TextArea test;

    public void initialize() {
        pharmacie.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = MobileApplication.getInstance().getAppBar();
                appBar.setTitleText("Pharmacie");
            }
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
                        JsonObject object = array.getJsonObject(0);
                        test.setText(object.getString("adresse"));
                        jsonReader.close();
                    }
                    in.close();


                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (ProtocolException e) {
                e.printStackTrace();
            }


        });
    }



}

