package com.gluonapplication.views;

import com.gluonhq.charm.glisten.animation.*;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.DropdownButton;
import com.gluonhq.charm.glisten.control.FloatingActionButton;
import com.gluonhq.charm.glisten.control.TextField;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import httpHelper.HttpHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class CalculatorPresenter {

    public Label result;
    public Label label1;
    public Label label2;
    public TextField field1;
    public TextField field2;
    public DropdownButton dropDownCalculType;
    @FXML
    private View calculator;
    

    public void initialize() {
        calculator.setShowTransitionFactory(RotateInDownRightTransition::new);
        
        calculator.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = MobileApplication.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e -> 
                        MobileApplication.getInstance().getDrawer().open()));
                appBar.setTitleText("Calculator");
            }
        });
    }
    
    public void setTypeTauxRemise(ActionEvent actionEvent) {
        label1.setText("Prix d’achat net");
        label2.setText("Prix d’achat brut");
    }

    public void setTypeAchatNet(ActionEvent actionEvent) {
        label1.setText("Prix d’achat brut");
        label2.setText("taux de remise");
    }

    public void setTypeVenteNet(ActionEvent actionEvent) {
        label1.setText("prix d’achat net");
        label2.setText("coefficient multiplicateur");
    }

    public void setTypeCoefMult(ActionEvent actionEvent) {
        label1.setText("Prix de vente net");
        label2.setText("Prix de vente net");
    }

    public void calcul(ActionEvent actionEvent) {
        String val1 = field1.getText();
        String val2 = field2.getText();
        Map<String,String > pmp = new HashMap<>();
        HttpHelper hh = null;
        switch (dropDownCalculType.getSelectedItem().getId()){
            case "choice01":
                pmp.put("Net",val1);
                pmp.put("Brut",val2);
                hh = new HttpHelper("/Math/TauxDeRemise",pmp);
                break;
            case "choice02":
                pmp.put("Brut",val1);
                pmp.put("Taux",val2);
                hh = new HttpHelper("/Math/PrixAchatNet",pmp);
                break;
            case "choice03":
                pmp.put("Net",val1);
                pmp.put("Coef",val2);
                hh =new HttpHelper("/Math/PrixVentetNet",pmp);
                break;
            case "choice04":
                pmp.put("Vente",val1);
                pmp.put("Achat",val2);
                hh = new HttpHelper("/Math/CoefficientMultiplicateur",pmp);
                break;
                default:
                    break;
        }
        result.setText(hh.execute());
        
    }
}
