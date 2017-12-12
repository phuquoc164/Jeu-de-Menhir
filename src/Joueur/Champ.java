package Joueur;

public class Champ {

    private int nombreMenhir = 0;

    public int getMenhir() {
        return this.nombreMenhir;
    }

    // mettre les menhirs sur son champ
    public int ajouterMenhir(int nouveauMenhir) {
        this.nombreMenhir = this.nombreMenhir + nouveauMenhir;
        return this.nombreMenhir;
    }

    // supprimer les menhirs
    public int supprimerMenhir(int nouveauMenhir) {
        this.nombreMenhir -= nouveauMenhir;
        return this.nombreMenhir;
    }
    
    // set nombre menhir = 0
    public void setZero() {
        this.nombreMenhir =0;
    }
}
