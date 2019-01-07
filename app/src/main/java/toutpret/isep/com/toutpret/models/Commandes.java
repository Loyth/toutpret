package toutpret.isep.com.toutpret.models;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;

@IgnoreExtraProperties
public class Commandes {
    private String date;
    private String userId;
    private Long numeroCommande;
    private String status;
    private HashMap<String, ProductPanier> listProducts;
    private String commandId;
    private String livreurId;
    private String userLat;
    private String userLng;
    private String livreurLat;
    private String livreurLng;

    public Commandes(Long numeroCommande, HashMap<String, ProductPanier> listProducts, String date, String userId, String status, String userLat, String userLng) {
        this.numeroCommande = numeroCommande;
        this.date = date;
        this.userId = userId;
        this.listProducts = listProducts;
        this.status = status;
        this.userLat = userLat;
        this.userLng = userLng;
    }

    public Commandes() {

    }

    public void setCommandId(String commandId) {
        this.commandId = commandId;
    }

    public String getCommandId() {
        return commandId;
    }

    public String getLivreurId() {
        return livreurId;
    }

    public void setLivreurId(String livreurId) {
        this.livreurId = livreurId;
    }

    public String getUserLat() {
        return userLat;
    }

    public void setUserLat(String userLat) {
        this.userLat = userLat;
    }

    public String getUserLng() {
        return userLng;
    }

    public void setUserLng(String userLng) {
        this.userLng = userLng;
    }

    public String getLivreurLat() {
        return livreurLat;
    }

    public void setLivreurLat(String livreurLat) {
        this.livreurLat = livreurLat;
    }

    public String getLivreurLng() {
        return livreurLng;
    }

    public void setLivreurLng(String livreurLng) {
        this.livreurLng = livreurLng;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
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