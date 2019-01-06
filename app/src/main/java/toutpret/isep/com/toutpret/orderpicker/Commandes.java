package toutpret.isep.com.toutpret.orderpicker;

public class Commandes {
    private int numeroCommande;
    private int nbArticles;
    private int Thumbnail;

    public Commandes(int numeroCommande, int nbArticles, int thumbnail) {
        this.numeroCommande = numeroCommande;
        this.nbArticles = nbArticles;
        Thumbnail = thumbnail;
    }

    public int getThumbnail() {
        return Thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        Thumbnail = thumbnail;
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
