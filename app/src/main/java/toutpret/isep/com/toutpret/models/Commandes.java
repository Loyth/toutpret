package toutpret.isep.com.toutpret.models;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.HashMap;

@IgnoreExtraProperties
public class Commandes implements Serializable {
    private String date;
    private String userId;
    private Long numeroCommande;
    private String status;
    private HashMap<String, ProductPanier> listProducts;
    private String commandId;
    private String livreurId;
    private double userLat;
    private double userLng;
    private double livreurLat;
    private double livreurLng;

    public Commandes(Long numeroCommande, HashMap<String, ProductPanier> listProducts, String date, String userId, String status, double userLat, double userLng, double livreurLat, double livreurLng) {
        this.numeroCommande = numeroCommande;
        this.date = date;
        this.userId = userId;
        this.listProducts = listProducts;
        this.status = status;
        this.userLat = userLat;
        this.userLng = userLng;
        this.livreurLat = livreurLat;
        this.livreurLng = livreurLng;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public HashMap<String, ProductPanier> getListProducts() {
        return listProducts;
    }

    public void setListProducts(HashMap<String, ProductPanier> listProducts) {
        this.listProducts = listProducts;
    }

    public String getCommandId() {
        return commandId;
    }

    public void setCommandId(String commandId) {
        this.commandId = commandId;
    }

    public String getLivreurId() {
        return livreurId;
    }

    public void setLivreurId(String livreurId) {
        this.livreurId = livreurId;
    }

    public double getUserLat() {
        return userLat;
    }

    public void setUserLat(double userLat) {
        this.userLat = userLat;
    }

    public double getUserLng() {
        return userLng;
    }

    public void setUserLng(double userLng) {
        this.userLng = userLng;
    }

    public double getLivreurLat() {
        return livreurLat;
    }

    public void setLivreurLat(double livreurLat) {
        this.livreurLat = livreurLat;
    }

    public double getLivreurLng() {
        return livreurLng;
    }

    public void setLivreurLng(double livreurLng) {
        this.livreurLng = livreurLng;
    }
}