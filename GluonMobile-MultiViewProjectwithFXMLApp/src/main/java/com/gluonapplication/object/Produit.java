package com.gluonapplication.object;

import javax.json.JsonValue;
import java.util.List;

public class Produit {

    private String libelle;
    private String prix;


    public Produit(String libelle,String prix) {
    }



    public String getPrix() {
        return prix;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }


}
