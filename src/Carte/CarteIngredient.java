package Carte;

import Joueur.*;

public class CarteIngredient extends Carte {

    protected int[][] valeur = new int[3][4];

    // constructeur
    public CarteIngredient(int numero) {
        super(numero);
    }

    @Override
    public void setValeur(int[][] valeur) {
        this.valeur = valeur;
    }

    @Override
    public int[][] getValeur() {
        return this.valeur;
    }

    // choisir force geant
    public void effectGeant(Joueur j, int valeur) {
        j.getGraine().ajouterGraine(valeur);
    }

    //choisir force Engrais
    public void effectEngrais(Joueur j, int valeur) {
        int grainePousse = j.getGraine().getTotal();
        if (grainePousse <= valeur) {
            j.getChamp().ajouterMenhir(grainePousse);
            j.getGraine().supprimerGraine(grainePousse);
        } else {
            j.getChamp().ajouterMenhir(valeur);
            j.getGraine().supprimerGraine(valeur);
        }
    }

    //choisir force farfadet
    public void effectFarfadet(Joueur j1, Joueur j2, int valeur) {
        int graineAvoir = j2.getGraine().getTotal();
        if (valeur <= graineAvoir) {
            j2.getGraine().supprimerGraine(valeur);
            j1.getGraine().ajouterGraine(valeur);
        } else {
            j2.getGraine().supprimerGraine(graineAvoir);
            j1.getGraine().ajouterGraine(graineAvoir);
        }
    }

}
