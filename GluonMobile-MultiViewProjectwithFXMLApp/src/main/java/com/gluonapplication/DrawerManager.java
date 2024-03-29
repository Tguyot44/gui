package com.gluonapplication;

import com.gluonhq.charm.down.Platform;
import com.gluonhq.charm.down.Services;
import com.gluonhq.charm.down.plugins.LifecycleService;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.application.ViewStackPolicy;
import com.gluonhq.charm.glisten.control.Avatar;
import com.gluonhq.charm.glisten.control.NavigationDrawer;
import com.gluonhq.charm.glisten.control.NavigationDrawer.Item;
import com.gluonhq.charm.glisten.control.NavigationDrawer.ViewItem;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.scene.image.Image;

import static com.gluonapplication.GluonApplication.*;

public class DrawerManager {

    public static void buildDrawer(MobileApplication app) {
        NavigationDrawer drawer = app.getDrawer();
        
        NavigationDrawer.Header header = new NavigationDrawer.Header("Gluon Mobile",
                "Multi View Project",
                new Avatar(21, new Image(DrawerManager.class.getResourceAsStream("/icon.png"))));
        drawer.setHeader(header);
        
        final Item menuItem = new ViewItem("Log Out", MaterialDesignIcon.HOME.graphic(), MENU_VIEW, ViewStackPolicy.SKIP);
        final Item calculatorItem = new ViewItem("Calculator", MaterialDesignIcon.DASHBOARD.graphic(), CALCULATOR_VIEW);
        final Item gestionItem = new ViewItem("Gestion",MaterialDesignIcon.NEAR_ME.graphic(),GESTION_VIEW);
        final Item PharmacieItem = new ViewItem("Pharmacie",MaterialDesignIcon.HEALING.graphic(),PHARMACIE_VIEW);
        final Item ProduitItem = new ViewItem("Produits",MaterialDesignIcon.REDEEM.graphic(),PRODUIT_VIEW);
        final Item AchatItem = new ViewItem("Achat",MaterialDesignIcon.EURO_SYMBOL.graphic(),ACHAT_VIEW);
        final Item ContactItem = new ViewItem("Contact",MaterialDesignIcon.CONTACTS.graphic(),CONTACT_VIEW);
        drawer.getItems().addAll(menuItem, calculatorItem,gestionItem,PharmacieItem,ProduitItem,AchatItem,ContactItem);
        
        if (Platform.isDesktop()) {
            final Item quitItem = new Item("Quit", MaterialDesignIcon.EXIT_TO_APP.graphic());
            quitItem.selectedProperty().addListener((obs, ov, nv) -> {
                if (nv) {
                    Services.get(LifecycleService.class).ifPresent(LifecycleService::shutdown);
                }
            });
            drawer.getItems().add(quitItem);
        }
    }
}