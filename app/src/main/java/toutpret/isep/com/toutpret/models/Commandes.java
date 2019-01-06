package toutpret.isep.com.toutpret.models;

import java.util.HashMap;

public class Commandes {
    private String date;
    private String userId;
    private Long numeroCommande;
    private HashMap<Integer, ProductPanier> listProducts;

    public Commandes(Long numeroCommande, HashMap<Integer, ProductPanier> listProducts, String date, String userId) {
        this.numeroCommande = numeroCommande;
        this.date = date;
        this.userId = userId;
        this.listProducts = listProducts;
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

    public HashMap<Integer, ProductPanier> getListProducts() {
        return listProducts;
    }

    public void setListProducts(HashMap<Integer, ProductPanier> listProducts) {
        this.listProducts = listProducts;
    }
}
