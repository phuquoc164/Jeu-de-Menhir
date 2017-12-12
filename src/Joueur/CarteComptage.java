package Joueur;

public class CarteComptage {

    private int nombreMenhirTotal = 0;

    // prendre nombre menhir totale sur la carte comptage
    public int getNombreMenhirTotal() {
        return this.nombreMenhirTotal;
    }

    // ajouter les menhirs sur la carte comptage
    public int ajouterMenhirTotal(int nouveauMenhir) {
        this.nombreMenhirTotal += nouveauMenhir;
        return this.nombreMenhirTotal;
    }
}
