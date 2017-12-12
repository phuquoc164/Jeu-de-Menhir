package Carte;

import Joueur.*;

public class CarteAllie extends Carte {

    private int[][] valeur = new int[1][4];

    @Override
    public int[][] getValeur() {
        return valeur;
    }

    @Override
    public void setValeur(int[][] valeur) {
        this.valeur = valeur;
    }

    // constructeur
    public CarteAllie(int numero) {
        super(numero);
    }

    // choisir carte Taupe Geant
    public void TaupeGeant(Joueur joueur1, int valeur, Joueur joueur2) {
        int nombreMenhir = joueur2.getChamp().getMenhir();
        if (nombreMenhir > valeur) {
            joueur2.getChamp().supprimerMenhir(valeur);
        } else {
            joueur2.getChamp().supprimerMenhir(nombreMenhir);
        }
    }

    // choisir carte Chien De Garde
    public void ChienDeGarde(Joueur joueur1, int valeurFarfadet, Joueur joueur2, int valeurChienDeGarde) {
        int nombreGraine = joueur2.getGraine().getTotal();
        if (valeurFarfadet > valeurChienDeGarde) {
            if (valeurFarfadet - valeurChienDeGarde > nombreGraine) {
                joueur2.getGraine().supprimerGraine(nombreGraine);
                joueur1.getGraine().ajouterGraine(nombreGraine);
            } else {
                joueur2.getGraine().supprimerGraine(valeurFarfadet - valeurChienDeGarde);
                joueur1.getGraine().ajouterGraine(valeurFarfadet - valeurChienDeGarde);
            }
        }
    }
}
