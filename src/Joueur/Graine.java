package Joueur;

public class Graine {

    private int total = 0;

    public void setTotal(int graine) {
        this.total = graine;
    }

    public int getTotal() {
        return this.total;
    }

    // ajouter graines
    public void ajouterGraine(int nouveauGraine) {
        this.total += nouveauGraine;
    }

    // supprimer les graines
    public void supprimerGraine(int nouveauGraine) {
        this.total -= nouveauGraine;

    }
}
