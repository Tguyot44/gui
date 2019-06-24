package com.gluonapplication;

import com.gluonapplication.views.GestionView;
import com.gluonapplication.views.MenuView;
import com.gluonapplication.views.CalculatorView;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.visual.Swatch;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class GluonApplication extends MobileApplication {

    public static final String MENU_VIEW = HOME_VIEW;
    public static final String CALCULATOR_VIEW = "Calculator View";
    public static final String GESTION_VIEW = "Gestion View";
    
    @Override
    public void init() {

        addViewFactory(MENU_VIEW, () -> new MenuView().getView());
        addViewFactory(CALCULATOR_VIEW, () -> new CalculatorView().getView());
        addViewFactory(GESTION_VIEW, () -> new GestionView().getView());


        DrawerManager.buildDrawer(this);
    }

    @Override
    public void postInit(Scene scene) {
        Swatch.INDIGO.assignTo(scene);

        scene.getStylesheets().add(GluonApplication.class.getResource("style.css").toExternalForm());
        ((Stage) scene.getWindow()).getIcons().add(new Image(GluonApplication.class.getResourceAsStream("/icon.png")));
    }
}
