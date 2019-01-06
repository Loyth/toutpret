package toutpret.isep.com.toutpret.models;

public class Commandes {
    private int numeroCommande;
    private int nbArticles;
    private String date;

    public Commandes(int numeroCommande, int nbArticles, String date) {
        this.numeroCommande = numeroCommande;
        this.nbArticles = nbArticles;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getNumeroCommande() {
        return numeroCommande;
    }


    public void setNumeroCommande(int numeroCommande) {
        this.numeroCommande = numeroCommande;
    }

    public int getNbArticles() {
        return nbArticles;
    }

    public void setNbArticles(int nbArticles) {
        this.nbArticles = nbArticles;
    }
}
