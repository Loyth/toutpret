package toutpret.isep.com.toutpret.models;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;

@IgnoreExtraProperties
public class Commandes {
    private String date;
    private String userId;
    private Long numeroCommande;
    private HashMap<String, ProductPanier> listProducts;

    public Commandes(Long numeroCommande, HashMap<String, ProductPanier> listProducts, String date, String userId) {
        this.numeroCommande = numeroCommande;
        this.date = date;
        this.userId = userId;
        this.listProducts = listProducts;
    }

    public Commandes() {

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getNumeroCommande() {
        return numeroCommande;
    }

    public void setNumeroCommande(Long numeroCommande) {
        this.numeroCommande = numeroCommande;
    }

    public HashMap<String, ProductPanier> getListProducts() {
        return listProducts;
    }

    public void setListProducts(HashMap<String, ProductPanier> listProducts) {
        this.listProducts = listProducts;
    }
}