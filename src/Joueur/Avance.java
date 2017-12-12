package Joueur;

import Carte.CarteIngredient;
import Carte.Carte;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Avance implements Strategie {

    //// joueur virtuel choisit une carte par hasard (partie rapide)
    @Override
    public int sChoisir(ArrayList<CarteIngredient> listCarte) {
        int numCarte = rand(0, 3);
        int count = 0;
        int numChoisi = 0;
        while (count < 1) {
            if (listCarte.get(numCarte).getJouer() == false) {
                numChoisi = numCarte;
                count++;
            } else {
                numCarte = rand(0, 3);
            }
        }
        return numChoisi;
    }

    // joueur virtuel choisit une carte par hasard (regle avance)
    @Override
    public int sChoisirAvance(ArrayList<Carte> listCarte) {
        int numCarte = rand(0, 3);
        int count = 0;
        int numChoisi = 0;
        while (count < 1) {
            if (listCarte.get(numCarte).getJouer() == false) {
                numChoisi = numCarte;
                count++;
            } else {
                numCarte = rand(0, 3);
            }
        }
        return numChoisi;
    }

    // joueur virtuel choisit une force selon les valeurs de la carte et ses graines
    @Override
    public String sJouer(CarteIngredient carte, int saison, int numGraine) {
        int[][] valeur = new int[3][4];
        valeur = carte.getValeur();
        int numGeant = valeur[0][saison];
        int numEngrais = valeur[1][saison];
        int numFarfadet = valeur[2][saison];
        String force;
        if (numGraine >= numEngrais) {
            force = "Engrais";
        } else {
            if (numGeant >= numFarfadet) {
                force = "Geant";
            } else {
                force = "Farfadet";
            }
        }
        return force;
    }

    // detruire les menhirs selon le nombre de menhirs
    @Override
    public int sDetruireMenhir(ArrayList<Joueur> listJoueur, int pos) {
        System.out.println("Voici la liste d'autre joueurs");
        for (int k = 0; k < listJoueur.size(); k++) {
            if (k != pos) {
                System.out.println("Entrer numero " + k + " pour: " + listJoueur.get(k).getNomDeJoueur() + " avec " + listJoueur.get(k).getChamp().getMenhir() + " Menhirs Aldults");
            }
        }
        int min = listJoueur.get(0).getChamp().getMenhir();
        int adver = 0;
        for (int k = 0; k < listJoueur.size(); k++) {
            if (k != pos) {
                if (listJoueur.get(k).getChamp().getMenhir() > min) {
                    min = listJoueur.get(k).getChamp().getMenhir();
                    adver = k;
                }
            }
        }
        return adver;
    }

    // joueur virtuel choisit une force selon les valeurs de la carte et ses graines
    @Override
    public String sJouerAvance(Carte carte, int saison, int numGraine) {
        int[][] valeur = new int[3][4];
        valeur = carte.getValeur();
        int numGeant = valeur[0][saison];
        int numEngrais = valeur[1][saison];
        int numFarfadet = valeur[2][saison];
        String force;
        if (saison != 3) {
            if ((numGraine >= numEngrais) && (numEngrais != 0) && (numGraine != 0)) {
                force = "Engrais";
            } else {
                if (numGeant >= numFarfadet) {
                    force = "Geant";
                } else {
                    force = "Farfadet";
                }
            }
        } else {
            if ((numGraine > 0) && (numEngrais > 0)) {
                force = "Engrais";
            } else {
                force = "Farfadet";
            }
        }
        return force;
    }

    // joueur virtuel vole les graines
    @Override
    public int sVoler(ArrayList<Joueur> listJoueur, int pos, int saison) {
        int adver = 0;
        HashMap<Integer, Integer> valeurGraine = new HashMap();
        for (int i = 0; i < listJoueur.size(); i++) {
            if (i != pos) {
                valeurGraine.put(i, listJoueur.get(i).getGraine().getTotal());
            }
        }
        valeurGraine.put(pos, 0);
        int max = 0;
        for (int i = 0; i < valeurGraine.size(); i++) {
            if ((i != pos) && (valeurGraine.get(i) >= max)) {
                adver = i;
            }
        }
        return adver;
    }

    // trouver un nombre aleatoire entre min et max
    public static int rand(int min, int max) {
        try {
            Random rn = new Random();
            int range = max - min + 1;
            int randomNum = min + rn.nextInt(range);
            return randomNum;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

}
