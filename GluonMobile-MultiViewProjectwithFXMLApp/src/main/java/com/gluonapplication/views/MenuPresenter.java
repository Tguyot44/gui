package com.gluonapplication.views;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import httpHelper.HttpHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;



public class MenuPresenter {

    @FXML
    private View menu;
    @FXML
    TextField loginField;
    @FXML
    PasswordField passwordField;

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

        /*String login = loginField.getText();
        String loginAttendu = "test";
        if(login == loginAttendu) {
            loginField.setText("Ca marche");*/
            menu.getApplication().getDrawer().open();
        /*}
        else{
            loginField.setText("Ca marche plus");
        }*/
    }
}
