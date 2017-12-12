package Console;

import Carte.*;
import Game.*;
import Joueur.*;
import java.util.ArrayList;
import java.util.Random;

public class PartieRapide {

    // commencer une partie rapide
    public static void runPartieRapide(String nom, int age, int nombreDeJoueur, String niveau) {
        Game game = new Game();
        game.setPartieRapide(true);
        game.setNombreDeJoueur(nombreDeJoueur);
        ArrayList<Joueur> joueur = new ArrayList();
        String niveau1 = "Physique";
        Joueur jo = new Joueur(nom, age, niveau1);
        joueur.add(jo);
        for (int i = 1; i < nombreDeJoueur; i++) {
            String nomVirtuel = "JoueurVirtuel" + i;
            int ageVirtuel = rand(18, 30);
            Joueur jo1 = new Joueur(nomVirtuel, ageVirtuel, niveau);
            joueur.add(jo1);
        }
        // Initialiser 24 cartes ingredients
        ArrayList<CarteIngredient> listCarte_init = new ArrayList();
        for (int i = 0; i < 24; i++) {
            CarteIngredient carte = new CarteIngredient(i);
            listCarte_init.add(carte);
        }
        game.creerListCarteIngredient(listCarte_init);
        ArrayList<Joueur> joueur_final = new ArrayList();
        joueur_final = game.construireTour(joueur);
        // Imprimer le list de joueur  
        System.out.println("Voici la liste de joueur");
        for (int i = 0; i < joueur_final.size(); i++) {
            System.out.println(joueur_final.get(i).getNomDeJoueur() + " " + joueur_final.get(i).getAge() + " " + joueur_final.get(i).getNiveau());
        }
        System.out.println("Donc " + joueur_final.get(0).getNomDeJoueur() + " va commencer d'abord");

        //Commencer le game par placant champ et posant 2 graines 
        for (int i = 0; i < joueur_final.size(); i++) {
            Champ champ = new Champ();
            Graine graine = new Graine();
            joueur_final.get(i).setChamp(champ);
            joueur_final.get(i).setGraine(graine);
            joueur_final.get(i).poserGraine();
        }
        // Distribuer cartes pour tous les joueurs
        System.out.println("Voici les cartes pour chaque joueur");
        for (int i = 0; i < joueur_final.size(); i++) {
            game.distribuerCarteIngredient(listCarte_init, joueur_final.get(i));
            System.out.println("List carte pour " + joueur_final.get(i).getNomDeJoueur());
            joueur_final.get(i).ImprimerListCarte();
            System.out.println();
            System.out.println(joueur_final.get(i).getNomDeJoueur() + " a " + joueur_final.get(i).getGraine().getTotal() + " graines");
        }

        // Jouer chaque tour 
        for (int i = 0; i < 4; i++) {
            Tour tour = new Tour(i);
            System.out.println("Ici la saison " + tour.getTour());
            // Choisir une carte par chaque joueur
            for (int j = 0; j < nombreDeJoueur; j++) {
                // Joueur physique
                if (joueur_final.get(j).getNiveau().equalsIgnoreCase("Physique")) {
                    int numChoisi = joueur_final.get(j).choisirCarte(joueur_final.get(j).getListCarte());
                    String force = joueur_final.get(j).JouerCarte(joueur_final.get(j).getListCarte().get(numChoisi), i);
                    int[][] valeur = joueur_final.get(j).getListCarte().get(numChoisi).getValeur();
                    if (force.toUpperCase().equalsIgnoreCase("GEANT")) {
                        joueur_final.get(j).getListCarte().get(numChoisi).effectGeant(joueur_final.get(j), valeur[0][i]);
                        System.out.println("Donc vous avez " + joueur_final.get(j).getGraine().getTotal() + " graines");
                    } else if (force.toUpperCase().equalsIgnoreCase("ENGRAIS")) {
                        joueur_final.get(j).getListCarte().get(numChoisi).effectEngrais(joueur_final.get(j), valeur[1][i]);
                        System.out.println("Donc vous avez " + joueur_final.get(j).getGraine().getTotal() + " graines");
                        System.out.println("Donc vous avez " + joueur_final.get(j).getChamp().getMenhir() + " menhirs");

                    } else if (force.toUpperCase().equalsIgnoreCase("FARFADET")) {
                        int adver = joueur_final.get(j).volerJoueur(joueur_final, joueur_final.get(j), j);
                        joueur_final.get(j).getListCarte().get(numChoisi).effectFarfadet(joueur_final.get(j), joueur_final.get(adver), valeur[2][i]);
                        System.out.println("Donc vous avez " + joueur_final.get(j).getGraine().getTotal() + " graines");
                    } else {
                        System.out.println(force.toUpperCase());
                        System.out.println("Vous n'entrez pas le bon force. Vous perdez votre tour.");
                    }
                }
                // Joueur Virtuel avec le niveau Debutant
                if (joueur_final.get(j).getNiveau().toUpperCase().equalsIgnoreCase("DEBUTANT")) {
                    joueur_final.get(j).setStrategie(new Debutant());
                    int numChoisi = joueur_final.get(j).choisiVirtuel();
                    System.out.println(joueur_final.get(j).getNomDeJoueur() + " a choisi carte numero " + joueur_final.get(j).getListCarte().get(numChoisi).getNumero());
                    String force = joueur_final.get(j).joueVirtuel(joueur_final.get(j).getListCarte().get(numChoisi), i, joueur_final.get(j).getGraine().getTotal());
                    System.out.println(joueur_final.get(j).getNomDeJoueur() + " a choisi " + force);
                    int[][] valeur = joueur_final.get(j).getListCarte().get(numChoisi).getValeur();
                    if (force.toUpperCase().equalsIgnoreCase("GEANT")) {
                        joueur_final.get(j).getListCarte().get(numChoisi).effectGeant(joueur_final.get(j), valeur[0][i]);
                    } else if (force.toUpperCase().equalsIgnoreCase("ENGRAIS")) {
                        joueur_final.get(j).getListCarte().get(numChoisi).effectEngrais(joueur_final.get(j), valeur[1][i]);

                    } else if (force.toUpperCase().equalsIgnoreCase("FARFADET")) {
                        int adver = joueur_final.get(j).volerVirtuel(joueur_final, j, i);
                        System.out.println("J'ai choisi de voler à " + joueur_final.get(adver).getNomDeJoueur());
                        joueur_final.get(j).getListCarte().get(numChoisi).effectFarfadet(joueur_final.get(j), joueur_final.get(adver), valeur[2][i]);
                    }
                }
                // Joueur Virtuel avec le niveau Avance 
                if (joueur_final.get(j).getNiveau().toUpperCase().equalsIgnoreCase("AVANCE")) {
                    joueur_final.get(j).setStrategie(new Avance());
                    int numChoisi = joueur_final.get(j).choisiVirtuel();
                    System.out.println(joueur_final.get(j).getNomDeJoueur() + " a choisi carte numero " + joueur_final.get(j).getListCarte().get(numChoisi).getNumero());
                    String force = joueur_final.get(j).joueVirtuel(joueur_final.get(j).getListCarte().get(numChoisi), i, joueur_final.get(j).getGraine().getTotal());
                    System.out.println(joueur_final.get(j).getNomDeJoueur() + " a choisi " + force);
                    int[][] valeur = joueur_final.get(j).getListCarte().get(numChoisi).getValeur();
                    if (force.toUpperCase().equalsIgnoreCase("GEANT")) {
                        joueur_final.get(j).getListCarte().get(numChoisi).effectGeant(joueur_final.get(j), valeur[0][i]);
                    } else if (force.toUpperCase().equalsIgnoreCase("ENGRAIS")) {
                        joueur_final.get(j).getListCarte().get(numChoisi).effectEngrais(joueur_final.get(j), valeur[1][i]);

                    } else if (force.toUpperCase().equalsIgnoreCase("FARFADET")) {
                        int adver = joueur_final.get(j).volerVirtuel(joueur_final, j, i);
                        System.out.println("J'ai choisi de voler à " + joueur_final.get(adver).getNomDeJoueur());
                        joueur_final.get(j).getListCarte().get(numChoisi).effectFarfadet(joueur_final.get(j), joueur_final.get(adver), valeur[2][i]);
                    }
                }
            }
        }
        // Trouver le joueur qui a plus menhirs 
        int max = game.trouveMaxMenhir(joueur_final);
        // Trier la list de joueurs par l'ordre descendant de menhirs
        ArrayList<Joueur> joueur_final_fini = game.triParMenhirs(joueur_final);
        // Afficher la liste de joueurs dans l'ordre de menhirs descendant
        game.afficherResultat(joueur_final_fini);
        // Trouver tous les joueurs qui ont le meme menhirs avec le max 
        ArrayList<Joueur> joueur_meme_menhirs = game.trouveMemeMenhirs(max, joueur_final_fini);
        // Trier la liste de meme menhirs par graines
        joueur_meme_menhirs = game.triParGraines(joueur_meme_menhirs);
        // Le max graines maintenant est les graines de joueur(0) dans la list
        int maxGraines = joueur_meme_menhirs.get(0).getGraine().getTotal();
        // Trouver tous les joueurs ont le meme menhirs et le meme graines 
        ArrayList<Joueur> joueur_meme_menhirs_meme_graines = game.trouveMemeGraines(maxGraines, joueur_meme_menhirs);
        // Imprimer le gagnant  
        // verifier la taille de la liste joueur meme menhirs et meme graines 
        if (joueur_meme_menhirs_meme_graines.size() > 1) {
            System.out.println("Le game est null entre");
            game.afficherResultat(joueur_meme_menhirs_meme_graines);
        }
        if (joueur_meme_menhirs_meme_graines.size() == 1) {
            System.out.println("Le gagnant est");
            game.afficherResultat(joueur_meme_menhirs_meme_graines);
        }
    }
 // trouver un nombre aleatoire entre min et max (pour l'age d'un joueur virtuel)
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
