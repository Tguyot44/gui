package com.gluonapplication.object;

public class Achat {

    private String client;
    private String pharmacie;
    private String dmo;
    private String produit;

    public Achat(String client, String pharmacie, String dmo, String produit)
    {

    }
    public String getclient() {
        return client;
    }

    public void setclient(String client) {
        this.client = client;
    }

    public String getpharmacie() {
        return pharmacie;
    }

    public void setpharmacie(String pharmacie) {
        this.pharmacie = pharmacie;
    }

    public String getdmo() {
        return dmo;
    }

    public void setdmo(String dmo) {
        this.dmo = dmo;
    }

    public String getproduit() {
        return produit;
    }

    public void setproduit(String produit) {
        this.produit = produit;
    }
}
