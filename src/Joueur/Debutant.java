package Joueur;

import Carte.CarteIngredient;
import Carte.Carte;
import java.util.ArrayList;
import java.util.Random;

public class Debutant implements Strategie {

    // joueur virtuel choisit une carte par hasard (partie rapide)
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

    // joueur virtuel detruit les menhirs
    @Override
    public int sDetruireMenhir(ArrayList<Joueur> listJoueur, int pos) {
//        System.out.println("Voici la liste d'autre joueurs");
//        for (int k = 0; k < listJoueur.size(); k++) {
//            if (k != pos) {
//                System.out.println("Entrer numero " + k + " pour: " + listJoueur.get(k).getNomDeJoueur() + " avec " + listJoueur.get(k).getChamp().getMenhir() + " Menhirs Aldults");
//            }
//        }
        int adver = 0;
        int count = 0;
        while (count < 1) {
            adver = rand(0, listJoueur.size() - 1);
            if (adver != pos) {
                count++;
            } else {
                adver = rand(0, listJoueur.size() - 1);
            }
        }
        return adver;
    }

    // joueur virtuel choisit la force par hasard (partie rapide)
    @Override
    public String sJouer(CarteIngredient carte, int saison, int numGraine) {
        int numAleatoire = rand(0, 2);
        String force = null;
        if (numAleatoire == 0) {
            force = "Geant";
        } else if (numAleatoire == 1) {
            force = "Engrais";
        } else {
            force = "Farfadet";
        }
        return force;
    }

    // joueur virtuel choisit la force par hasard (regle avance)
    @Override
    public String sJouerAvance(Carte carte, int saison, int numGraine) {
        int numAleatoire = rand(0, 2);
        String force = null;
        if (numAleatoire == 0) {
            force = "Geant";
        } else if (numAleatoire == 1) {
            force = "Engrais";
        } else {
            force = "Farfadet";
        }
        return force;
    }

    // joueur virtuel vole les graines
    @Override
    public int sVoler(ArrayList<Joueur> listJoueur, int pos, int saison) {
        int choisiAdver = rand(0, listJoueur.size() - 1);
        int count = 0;
        int adver = 0;
        while (count < 1) {
            if (choisiAdver != pos) {
                adver = choisiAdver;
                count++;
            } else {
                choisiAdver = rand(0, listJoueur.size() - 1);
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
