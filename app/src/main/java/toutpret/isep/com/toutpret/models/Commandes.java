package toutpret.isep.com.toutpret.models;

import java.util.List;

public class Commandes {
    private String date;
    private String userId;
    private Long numeroCommande;
    private List<ProductPanier> listProducts;

    public Commandes(Long numeroCommande, List<ProductPanier> listProducts, String date, String userId) {
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

    public List<ProductPanier> getListProducts() {
        return listProducts;
    }

    public void setListProducts(List<ProductPanier> listProducts) {
        this.listProducts = listProducts;
    }
}
