package com.gluonapplication.views;

import com.gluonhq.charm.glisten.mvc.View;
import java.io.IOException;
import javafx.fxml.FXMLLoader;

public class CalculatorView {
    
    public View getView() {
        try {
            View view = FXMLLoader.load(CalculatorView.class.getResource("calculator.fxml"));
            return view;
        } catch (IOException e) {
            System.out.println("IOException: " + e);
            return new View();
        }
    }
}
