package Console;

import Carte.*;
import Game.*;
import Joueur.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class PartieAvance {

    // commencer une partie Avance
    public static void runPartieAvance(String nom, int age, int nombreDeJoueur, String niveau) {
        Scanner scannerString = new Scanner(System.in);
        Scanner scannerBoolean = new Scanner(System.in);
        Game game = new Game();
        game.setPartieRapide(false);
        ArrayList<Joueur> joueur = new ArrayList();
        String niveau1 = "Physique";
        // initialiser list Joueur
        Joueur jo = new Joueur(nom, age, niveau1);
        joueur.add(jo);
        for (int i = 1; i < nombreDeJoueur; i++) {
            String nomVirtuel = "JoueurVirtuel " + i;
            int ageVirtuel = rand(18, 30);
            Joueur jo1 = new Joueur(nomVirtuel, ageVirtuel, niveau);
            joueur.add(jo1);
        }
        // initialiser 24 cartes Ingrdients
        ArrayList<CarteIngredient> listCarteIngredient = new ArrayList();
        for (int i = 0; i < 24; i++) {
            CarteIngredient carte = new CarteIngredient(i);
            listCarteIngredient.add(carte);
        }
        game.creerListCarteIngredient(listCarteIngredient);

        // initialiser 24 cartesingredient et 6 carte Allie
        ArrayList<Carte> listCarteAvance = new ArrayList();
        game.creerListCarteAvance(listCarteAvance);
        // creer liste Joueur 
        ArrayList<Joueur> listJoueurFinal = new ArrayList();
        listJoueurFinal = game.construireTour(joueur);
        // Imprimer le list de joueur  
        System.out.println("Voici la liste de joueur");
        for (int i = 0; i < listJoueurFinal.size(); i++) {
            System.out.println(listJoueurFinal.get(i).getNomDeJoueur() + " " + listJoueurFinal.get(i).getAge() + " " + listJoueurFinal.get(i).getNiveau());
        }
        System.out.println("Donc " + listJoueurFinal.get(0).getNomDeJoueur() + " va commencer cette manche");
        // creer une carte comptage pour chaque joueur
        System.out.println("");
        for (int j = 0; j < listJoueurFinal.size(); j++) {
            CarteComptage carteComptage = new CarteComptage();
            listJoueurFinal.get(j).setCarteComptage(carteComptage);
        }

        for (int i = 0; i < nombreDeJoueur; i++) {
            Manche manche = new Manche();
            // changer le joueur qui commence en premier chaque manche
            if (i > 0) {
                listJoueurFinal = game.changerManche(listJoueurFinal);
                System.out.println(listJoueurFinal.get(0).getNomDeJoueur() + " va commencer cette manche");
                System.out.println("");
            }
            // demander joueur physique vouler une carte Allie ou 2 graines
            for (int j = 0; j < listJoueurFinal.size(); j++) {
                if (listJoueurFinal.get(j).getNomDeJoueur().equals(nom)) {
                    System.out.println("Vous voulez prendre une carte Allie ou prendre 2 graines?");
                    System.out.println("Saisir \"true\" si vous voulez prendre une carte Allie");
                    System.out.println("Saisir \"false\" si vous voulez prendre 2 graines");
                    listJoueurFinal.get(j).setAvoirCarteAllie(scannerBoolean.nextBoolean());
                } else {
                    // choisir aleatoir entre 0 et 1 pour prendre une carte Allie ou 2 graines pour joueur Virtuel
                    if (rand(0, 1) == 0) {
                        listJoueurFinal.get(j).setAvoirCarteAllie(true);
                    } else {
                        listJoueurFinal.get(j).setAvoirCarteAllie(false);
                    }
                }
            }
            // initialiser les graines, le champs pour chaque joueur
            for (int j = 0; j < listJoueurFinal.size(); j++) {
                if (listJoueurFinal.get(j).getAvoirCarteAllie()) {
                    Champ champ = new Champ();
                    Graine graine = new Graine();
                    listJoueurFinal.get(j).setChamp(champ);
                    listJoueurFinal.get(j).setGraine(graine);
                    listJoueurFinal.get(j).poserGraineAvance();
                } else {
                    Champ champ = new Champ();
                    Graine graine = new Graine();
                    listJoueurFinal.get(j).setChamp(champ);
                    listJoueurFinal.get(j).setGraine(graine);
                    listJoueurFinal.get(j).poserGraine();
                }
            }
            // creer list carte pour chaque joueur
            System.out.println("Voici les cartes pour chaque joueur");
            for (int j = 0; j < listJoueurFinal.size(); j++) {
                if (listJoueurFinal.get(j).getAvoirCarteAllie()) {
                    game.distribuerCarteAvance(listCarteAvance, listJoueurFinal.get(j));
                    System.out.println("List carte pour " + listJoueurFinal.get(j).getNomDeJoueur());
                    listJoueurFinal.get(j).ImprimerListCarteAvance();
                    System.out.println("");
                } else {
                    game.distribuerCarteAvance(listCarteAvance, listJoueurFinal.get(j));
                    System.out.println("List carte pour " + listJoueurFinal.get(j).getNomDeJoueur());
                    listJoueurFinal.get(j).ImprimerListCarteAvance();
                    System.out.println(listJoueurFinal.get(j).getNomDeJoueur() + " a " + listJoueurFinal.get(j).getGraine().getTotal() + " graines");
                    System.out.println("");
                }
            }
            for (int j = 0; j < 4; j++) {
                Tour tour = new Tour(j);
                System.out.println("Ici la saison: " + tour.getTour());
                for (int k = 0; k < listJoueurFinal.size(); k++) {
                    if (listJoueurFinal.get(k).getNomDeJoueur().equals(nom)) {
                        // joueur physique choisit la carte Ingredient et sa force
                        int numChoisi = listJoueurFinal.get(k).choisirCarteAvance(listJoueurFinal.get(k).getListCarteAvance());
                        if (listCarteAvance.get(numChoisi) instanceof CarteIngredient) {
                            String force = listJoueurFinal.get(k).JouerCarte((CarteIngredient) listJoueurFinal.get(k).getListCarteAvance().get(numChoisi), j);
                            int[][] valeur = listJoueurFinal.get(k).getListCarteAvance().get(numChoisi).getValeur();
                            if (force.toUpperCase().equals("GEANT")) {
                                ((CarteIngredient) listJoueurFinal.get(k).getListCarteAvance().get(numChoisi)).effectGeant(listJoueurFinal.get(k), valeur[0][j]);
                                System.out.println("Donc vous avez " + listJoueurFinal.get(k).getGraine().getTotal() + " graines");
                                System.out.println("");
                            } else if (force.toUpperCase().equals("ENGRAIS")) {
                                ((CarteIngredient) listJoueurFinal.get(k).getListCarteAvance().get(numChoisi)).effectEngrais(listJoueurFinal.get(k), valeur[1][j]);
                                System.out.println("Donc vous avez " + listJoueurFinal.get(k).getGraine().getTotal() + " graines");
                                System.out.println("Donc vous avez " + listJoueurFinal.get(k).getChamp().getMenhir() + " menhirs");
                                System.out.println("");
                            } else if (force.toUpperCase().equals("FARFADET")) {
                                // chercher l'adver
                                int adver = listJoueurFinal.get(k).volerJoueur(listJoueurFinal, listJoueurFinal.get(k), k);
                                System.out.println("Vous voulez prendre " + valeur[2][j] + " graines de" + listJoueurFinal.get(adver).getNomDeJoueur());
                                int pos1 = listJoueurFinal.get(adver).getListCarteAvance().size() - 1;
                                // verifier adver avoir une carte Chien De Garde qui n'utilise pas
                                if ((listJoueurFinal.get(adver).getAvoirCarteAllie()) && (listJoueurFinal.get(adver).getListCarteAvance().get(pos1).getNom().equals("CHIEN DE GARDE")) && (listJoueurFinal.get(adver).getListCarteAvance().get(pos1).getJouer() == false)) {
//                                    System.out.println(listJoueurFinal.get(adver).getNomDeJoueur()+" a une carte Chien De Garde");
                                    int[][] valeur1 = listJoueurFinal.get(adver).getListCarteAvance().get(pos1).getValeur();
                                    ((CarteAllie) listJoueurFinal.get(adver).getListCarteAvance().get(pos1)).ChienDeGarde(listJoueurFinal.get(k), valeur[2][j], listJoueurFinal.get(adver), valeur1[0][j]);
                                    System.out.println(listJoueurFinal.get(adver).getNomDeJoueur() + " utilise la carte CHIEN DE GARDE avec la valeur " + valeur1[0][j]);
                                    System.out.println("Donc vous avez " + listJoueurFinal.get(k).getGraine().getTotal() + " graines");
                                    System.out.println(listJoueurFinal.get(adver).getNomDeJoueur() + " a " + listJoueurFinal.get(adver).getGraine().getTotal() + " graines");
                                    listJoueurFinal.get(adver).getListCarteAvance().get(pos1).PoserCarte();
                                } else {
                                    // sinon, joueur physique peut prendre les graines normalement
                                    ((CarteIngredient) listJoueurFinal.get(k).getListCarteAvance().get(numChoisi)).effectFarfadet(listJoueurFinal.get(k), listJoueurFinal.get(adver), valeur[2][j]);
                                    System.out.println("Donc vous avez " + listJoueurFinal.get(k).getGraine().getTotal() + " graines");
                                    System.out.println(listJoueurFinal.get(adver).getNomDeJoueur() + " a " + listJoueurFinal.get(adver).getGraine().getTotal() + " graines");
                                }
                            }
                        }
                        // verifier joueur physique avoir carte Allie qui n'utilise pas
                        if (listJoueurFinal.get(k).getAvoirCarteAllie()) {
                            numChoisi = listJoueurFinal.get(k).getListCarteAvance().size() - 1;
                            if (listJoueurFinal.get(k).getListCarteAvance().get(numChoisi).getJouer() == false) {
                                System.out.println("Vous avez une carte Allie " + listJoueurFinal.get(k).getListCarteAvance().get(numChoisi).getNom());
                                System.out.println("Vous voulez la jouer? oui ou non ?");
                                String utiliserCarteAllie = scannerString.nextLine();
                                if (utiliserCarteAllie.equalsIgnoreCase("oui")) {
                                    int[][] valeur1 = listJoueurFinal.get(k).getListCarteAvance().get(numChoisi).getValeur();
                                    if ((listJoueurFinal.get(k).getListCarteAvance().get(numChoisi).getNom().equals("TAUPE GEANT")) && (listJoueurFinal.get(k).getListCarteAvance().get(numChoisi).getJouer() == false)) {
                                        int adver = listJoueurFinal.get(k).detruireMenhir(listJoueurFinal, k);
                                        ((CarteAllie) listJoueurFinal.get(k).getListCarteAvance().get(numChoisi)).TaupeGeant(listJoueurFinal.get(k), valeur1[0][j], listJoueurFinal.get(adver));
                                        listJoueurFinal.get(k).getListCarteAvance().get(numChoisi).PoserCarte();
                                        System.out.println("Donc, vous avez " + listJoueurFinal.get(k).getChamp().getMenhir() + " Menhirs");
                                        System.out.println(listJoueurFinal.get(adver).getNomDeJoueur() + " a " + listJoueurFinal.get(k).getChamp().getMenhir() + " Menhirs");
                                    } else {
                                        System.out.println("Il n'y a pas personne qui utilise la force Farfadet");
                                        System.out.println("Vous avez perdu la Carte Chien De Garde");
                                        listJoueurFinal.get(k).getListCarteAvance().get(numChoisi).PoserCarte();
                                    }
                                }
                            }
                        }
                    } else {
                        // joueur Virtuel debutant
                        if (listJoueurFinal.get(k).getNiveau().toUpperCase().equals("DEBUTANT")) {
                            listJoueurFinal.get(k).setStrategie(new Debutant());
                            // choisir carte
                            int numChoisi = listJoueurFinal.get(k).choisiVirtuelAvance();
                            System.out.println(listJoueurFinal.get(k).getNomDeJoueur() + " a choisi carte " + listJoueurFinal.get(k).getListCarteAvance().get(numChoisi).getNom() + " avec numero " + listJoueurFinal.get(k).getListCarteAvance().get(numChoisi).getNumero());
                            String force = listJoueurFinal.get(k).joueVirtuelAvance(listJoueurFinal.get(k).getListCarteAvance().get(numChoisi), j, listJoueurFinal.get(k).getGraine().getTotal());
                            int[][] valeur = listJoueurFinal.get(k).getListCarteAvance().get(numChoisi).getValeur();
                            System.out.print(listJoueurFinal.get(k).getNomDeJoueur() + " a choisi " + force);
                            if (force.toUpperCase().equals("GEANT")) {
                                System.out.println(" avec une valeur = " + valeur[0][j]);
                                ((CarteIngredient) listJoueurFinal.get(k).getListCarteAvance().get(numChoisi)).effectGeant(listJoueurFinal.get(k), valeur[0][j]);
                                System.out.println(listJoueurFinal.get(k).getNomDeJoueur() + " a " + listJoueurFinal.get(k).getGraine().getTotal() + " graines");
                            } else if (force.toUpperCase().equals("ENGRAIS")) {
                                System.out.println(" avec une valeur = " + valeur[1][j]);
                                ((CarteIngredient) listJoueurFinal.get(k).getListCarteAvance().get(numChoisi)).effectEngrais(listJoueurFinal.get(k), valeur[1][j]);
                                System.out.println(listJoueurFinal.get(k).getNomDeJoueur() + " a " + listJoueurFinal.get(k).getGraine().getTotal() + " graines");
                                System.out.println(listJoueurFinal.get(k).getNomDeJoueur() + " a " + listJoueurFinal.get(k).getChamp().getMenhir() + " menhirs");
                            } else if (force.toUpperCase().equals("FARFADET")) {
                                System.out.println(" avec une valeur = " + valeur[2][j]);
                                // chercher l'aver pour voler les graines
                                int adver = listJoueurFinal.get(k).volerVirtuel(listJoueurFinal, k, j);
                                int pos1 = listJoueurFinal.get(adver).getListCarteAvance().size() - 1;
                                // verifier adver avoir une carte Chien De Garde qui n'utilise pas
                                if ((listJoueurFinal.get(adver).getAvoirCarteAllie()) && (listJoueurFinal.get(adver).getListCarteAvance().get(pos1).getNom().equals("CHIEN DE GARDE")) && (listJoueurFinal.get(adver).getListCarteAvance().get(pos1).getJouer() == false)) {
                                    int[][] valeur1 = listJoueurFinal.get(adver).getListCarteAvance().get(pos1).getValeur();
                                    // verifier adever est joueur physique
                                    if (listJoueurFinal.get(adver).getNomDeJoueur().equals(nom)) {
                                        System.out.println(listJoueurFinal.get(k).getNomDeJoueur() + " veut prendre " + valeur[2][j] + " graines de "+listJoueurFinal.get(adver).getNomDeJoueur());
                                        System.out.println("Vous avez une carte Allie CHIEN DE GARDE avec la valeur = " + valeur1[0][j]);
                                        System.out.println("Vous voulez la jouer ? oui ou non?");
                                        String response = scannerString.nextLine();
                                        if (response.equalsIgnoreCase("oui")) {
                                            ((CarteAllie) listJoueurFinal.get(adver).getListCarteAvance().get(pos1)).ChienDeGarde(listJoueurFinal.get(k), valeur[2][j], listJoueurFinal.get(adver), valeur1[0][j]);
                                            System.out.println("Donc vous avez " + listJoueurFinal.get(adver).getGraine().getTotal() + " graines");
                                            System.out.println(listJoueurFinal.get(k).getNomDeJoueur() + " a " + listJoueurFinal.get(k).getGraine().getTotal() + " graines");
                                            listJoueurFinal.get(adver).getListCarteAvance().get(pos1).PoserCarte();
                                        } else {
                                            ((CarteIngredient) listJoueurFinal.get(k).getListCarteAvance().get(numChoisi)).effectFarfadet(listJoueurFinal.get(k), listJoueurFinal.get(adver), valeur[2][j]);
                                            System.out.println("Donc vous avez " + listJoueurFinal.get(adver).getGraine().getTotal() + " graines");
                                            System.out.println(listJoueurFinal.get(k).getNomDeJoueur() + " a " + listJoueurFinal.get(k).getGraine().getTotal() + " graines");
                                        }
                                    } else {
                                        // verifier si valeur de chien de garde est pas egale a 0
                                        if (valeur1[0][j] != 0) {
                                            System.out.println(listJoueurFinal.get(adver).getNomDeJoueur() + " utilise la carte CHIEN DE GARDE");
                                            ((CarteAllie) listJoueurFinal.get(adver).getListCarteAvance().get(pos1)).ChienDeGarde(listJoueurFinal.get(k), valeur[2][j], listJoueurFinal.get(adver), valeur1[0][j]);
                                            System.out.println("Donc " + listJoueurFinal.get(k).getNomDeJoueur() + " avez " + listJoueurFinal.get(k).getGraine().getTotal() + " graines");
                                            System.out.println("Donc " + listJoueurFinal.get(adver).getNomDeJoueur() + " avez " + listJoueurFinal.get(adver).getGraine().getTotal() + " graines");
                                            listJoueurFinal.get(adver).getListCarteAvance().get(pos1).PoserCarte();
                                        } else {
                                            System.out.println(listJoueurFinal.get(k).getNomDeJoueur() + " veut prendre " + valeur[2][j] + " graines de" + listJoueurFinal.get(adver).getNomDeJoueur());
                                            System.out.println(listJoueurFinal.get(adver).getNomDeJoueur() + " n'utilise pas la carte CHIEN DE GARDE");
                                            ((CarteIngredient) listJoueurFinal.get(k).getListCarteAvance().get(numChoisi)).effectFarfadet(listJoueurFinal.get(k), listJoueurFinal.get(adver), valeur[2][j]);
                                            System.out.println("Donc " + listJoueurFinal.get(k).getNomDeJoueur() + " avez " + listJoueurFinal.get(k).getGraine().getTotal() + " graines");
                                            System.out.println("Donc " + listJoueurFinal.get(adver).getNomDeJoueur() + " avez " + listJoueurFinal.get(adver).getGraine().getTotal() + " graines");

                                        }
                                    }
                                } else {
                                    if (listJoueurFinal.get(adver).getNomDeJoueur().equals(nom)) {
                                        System.out.println(listJoueurFinal.get(k).getNomDeJoueur() + " veut prendre " + valeur[2][j] + " graines");
                                        ((CarteIngredient) listJoueurFinal.get(k).getListCarteAvance().get(numChoisi)).effectFarfadet(listJoueurFinal.get(k), listJoueurFinal.get(adver), valeur[2][j]);
                                        System.out.println("Donc vous avez " + listJoueurFinal.get(adver).getGraine().getTotal() + " graines");
                                        System.out.println(listJoueurFinal.get(k).getNomDeJoueur() + " a " + listJoueurFinal.get(k).getGraine().getTotal() + " graines");
                                    } else {
                                        System.out.println(listJoueurFinal.get(k).getNomDeJoueur() + " veut prendre " + valeur[2][j] + " graines de" + listJoueurFinal.get(adver).getNomDeJoueur());
                                        ((CarteIngredient) listJoueurFinal.get(k).getListCarteAvance().get(numChoisi)).effectFarfadet(listJoueurFinal.get(k), listJoueurFinal.get(adver), valeur[2][j]);
                                        System.out.println("Donc " + listJoueurFinal.get(k).getNomDeJoueur() + " avez " + listJoueurFinal.get(k).getGraine().getTotal() + " graines");
                                        System.out.println("Donc " + listJoueurFinal.get(adver).getNomDeJoueur() + " avez " + listJoueurFinal.get(adver).getGraine().getTotal() + " graines");
                                    }
                                }

                            }
                            // determiner utiliser carte Taupe geant ou pas (j est la saison)
                            if ((j == 3) && (listJoueurFinal.get(k).getAvoirCarteAllie())) {
                                int pos1 = 0;
                                for (int n = 0; n < listJoueurFinal.get(k).getListCarteAvance().size(); n++) {
                                    if (listJoueurFinal.get(k).getListCarteAvance().get(n) instanceof CarteAllie) {
                                        pos1 = n;
                                    }
                                }
                                if (listJoueurFinal.get(k).getListCarteAvance().get(pos1).getNom().equals("TAUPE GEANT")) {
                                    System.out.println(listJoueurFinal.get(k).getNomDeJoueur() + " utilise carte TAUPE GEANT");
                                    int adver = listJoueurFinal.get(k).sDetruireMenhir(listJoueurFinal, k);
                                    int[][] valeur1 = listJoueurFinal.get(k).getListCarteAvance().get(pos1).getValeur();

                                    System.out.println(listJoueurFinal.get(k).getNomDeJoueur() + " veut detruire " + valeur1[0][j] + " menhirs de " + listJoueurFinal.get(adver).getNomDeJoueur());
                                    ((CarteAllie) listJoueurFinal.get(k).getListCarteAvance().get(pos1)).TaupeGeant(listJoueurFinal.get(k), valeur1[0][j], listJoueurFinal.get(adver));
                                    System.out.println("Donc " + listJoueurFinal.get(k).getNomDeJoueur() + " a " + listJoueurFinal.get(k).getChamp().getMenhir() + " Menhirs");
                                    System.out.println("Donc " + listJoueurFinal.get(adver).getNomDeJoueur() + " a " + listJoueurFinal.get(adver).getChamp().getMenhir() + " Menhirs");
                                    listJoueurFinal.get(k).getListCarteAvance().get(pos1).PoserCarte();
                                }
                            }
                        } else {
                            if (listJoueurFinal.get(k).getNiveau().toUpperCase().equalsIgnoreCase("AVANCE")) {
                                listJoueurFinal.get(k).setStrategie(new Avance());
                                int numChoisi = listJoueurFinal.get(k).choisiVirtuelAvance();
                                System.out.println(listJoueurFinal.get(k).getNomDeJoueur() + " a choisi carte " + listJoueurFinal.get(k).getListCarteAvance().get(numChoisi).getNom() + " avec numero " + listJoueurFinal.get(k).getListCarteAvance().get(numChoisi).getNumero());
                                String force = listJoueurFinal.get(k).joueVirtuelAvance(listJoueurFinal.get(k).getListCarteAvance().get(numChoisi), j, listJoueurFinal.get(k).getGraine().getTotal());
                                System.out.print(listJoueurFinal.get(k).getNomDeJoueur() + " a choisi " + force);
                                int[][] valeur = listJoueurFinal.get(k).getListCarteAvance().get(numChoisi).getValeur();
                                if (force.toUpperCase().equals("GEANT")) {
                                    System.out.println(" avec une valeur = " + valeur[0][j]);
                                    ((CarteIngredient) listJoueurFinal.get(k).getListCarteAvance().get(numChoisi)).effectGeant(listJoueurFinal.get(k), valeur[0][j]);
                                    System.out.println(listJoueurFinal.get(k).getNomDeJoueur() + " a " + listJoueurFinal.get(k).getGraine().getTotal() + " graines");
                                } else if (force.toUpperCase().equals("ENGRAIS")) {
                                    System.out.println(" avec une valeur = " + valeur[1][j]);
                                    ((CarteIngredient) listJoueurFinal.get(k).getListCarteAvance().get(numChoisi)).effectEngrais(listJoueurFinal.get(k), valeur[1][j]);
                                    System.out.println(listJoueurFinal.get(k).getNomDeJoueur() + " a " + listJoueurFinal.get(k).getGraine().getTotal() + " graines");
                                    System.out.println(listJoueurFinal.get(k).getNomDeJoueur() + " a " + listJoueurFinal.get(k).getChamp().getMenhir() + " menhirs");
                                } else if (force.toUpperCase().equals("FARFADET")) {
                                    System.out.println(" avec une valeur = " + valeur[2][j]);
                                    // chercher adver et determiner que l'adver a carte chien de garde ou pas
                                    int adver = listJoueurFinal.get(k).volerVirtuel(listJoueurFinal, k, j);
                                    int pos1 = listJoueurFinal.get(adver).getListCarteAvance().size() - 1;
                                    if ((listJoueurFinal.get(adver).getAvoirCarteAllie()) && (listJoueurFinal.get(adver).getListCarteAvance().get(pos1).getNom().equals("CHIEN DE GARDE")) && (listJoueurFinal.get(adver).getListCarteAvance().get(pos1).getJouer() == false)) {
                                        int[][] valeur1 = listJoueurFinal.get(adver).getListCarteAvance().get(pos1).getValeur();
                                        if (listJoueurFinal.get(adver).getNomDeJoueur().equals(nom)) {
                                            System.out.println(listJoueurFinal.get(k).getNomDeJoueur() + " veut prendre " + valeur[2][j] + " graines de " + listJoueurFinal.get(adver).getNomDeJoueur());
                                            System.out.println("Vous avez une carte Allie CHIEN DE GARDE avec la valeur = " + valeur1[0][j]);
                                            System.out.println("Vous voulez la jouer ? oui ou non?");
                                            String response = scannerString.nextLine();
                                            if (response.equalsIgnoreCase("oui")) {
                                                ((CarteAllie) listJoueurFinal.get(adver).getListCarteAvance().get(pos1)).ChienDeGarde(listJoueurFinal.get(k), valeur[2][j], listJoueurFinal.get(adver), valeur1[0][j]);
                                                System.out.println("Donc vous avez " + listJoueurFinal.get(adver).getGraine().getTotal() + " graines");
                                                listJoueurFinal.get(adver).getListCarteAvance().get(pos1).PoserCarte();
                                            } else {
                                                ((CarteIngredient) listJoueurFinal.get(k).getListCarteAvance().get(numChoisi)).effectFarfadet(listJoueurFinal.get(k), listJoueurFinal.get(adver), valeur[2][j]);
                                                System.out.println("Donc vous avez " + listJoueurFinal.get(adver).getGraine().getTotal() + " graines");
                                                System.out.println("Donc " + listJoueurFinal.get(k).getNomDeJoueur() + " avez " + listJoueurFinal.get(k).getGraine().getTotal() + " graines");
                                            }
                                        } else {
                                            System.out.println(listJoueurFinal.get(adver).getNomDeJoueur() + " veut prendre " + valeur[2][j] + " graines de " + listJoueurFinal.get(adver).getNomDeJoueur());
                                            if (listJoueurFinal.get(adver).getGraine().getTotal() == 0) {
                                                ((CarteIngredient) listJoueurFinal.get(k).getListCarteAvance().get(numChoisi)).effectFarfadet(listJoueurFinal.get(k), listJoueurFinal.get(adver), valeur[2][j]);
                                                System.out.println(listJoueurFinal.get(adver).getNomDeJoueur() + " n'utilise pas la carte CHIEN DE GARDE");
                                                System.out.println("Donc " + listJoueurFinal.get(k).getNomDeJoueur() + " avez " + listJoueurFinal.get(k).getGraine().getTotal() + " graines");
                                                System.out.println("Donc " + listJoueurFinal.get(adver).getNomDeJoueur() + " avez " + listJoueurFinal.get(adver).getGraine().getTotal() + " graines");

                                            } else {
                                                if ((valeur[2][j] > 0) && (valeur1[0][j] != 0)) {
                                                    System.out.println(listJoueurFinal.get(adver).getNomDeJoueur() + " utilise la carte CHIEN DE GARDE");
                                                    ((CarteAllie) listJoueurFinal.get(adver).getListCarteAvance().get(pos1)).ChienDeGarde(listJoueurFinal.get(k), valeur[2][j], listJoueurFinal.get(adver), valeur1[0][j]);
                                                    System.out.println("Donc " + listJoueurFinal.get(k).getNomDeJoueur() + " avez " + listJoueurFinal.get(k).getGraine().getTotal() + " graines");
                                                    System.out.println("Donc " + listJoueurFinal.get(adver).getNomDeJoueur() + " avez " + listJoueurFinal.get(adver).getGraine().getTotal() + " graines");
                                                    listJoueurFinal.get(adver).getListCarteAvance().get(pos1).PoserCarte();
                                                } else {
                                                    ((CarteIngredient) listJoueurFinal.get(k).getListCarteAvance().get(numChoisi)).effectFarfadet(listJoueurFinal.get(k), listJoueurFinal.get(adver), valeur[2][j]);
                                                    System.out.println(listJoueurFinal.get(adver).getNomDeJoueur() + " n'utilise pas la carte CHIEN DE GARDE");
                                                    System.out.println("Donc " + listJoueurFinal.get(k).getNomDeJoueur() + " avez " + listJoueurFinal.get(k).getGraine().getTotal() + " graines");
                                                    System.out.println("Donc " + listJoueurFinal.get(adver).getNomDeJoueur() + " avez " + listJoueurFinal.get(adver).getGraine().getTotal() + " graines");

                                                }
                                            }
                                        }
                                    } else {
                                        if (listJoueurFinal.get(adver).getNomDeJoueur().equals(nom)) {
                                            System.out.println(listJoueurFinal.get(k).getNomDeJoueur() + " veut prendre" + valeur[2][j] + " graines de " + listJoueurFinal.get(adver).getNomDeJoueur());
                                            ((CarteIngredient) listJoueurFinal.get(k).getListCarteAvance().get(numChoisi)).effectFarfadet(listJoueurFinal.get(k), listJoueurFinal.get(adver), valeur[2][j]);
                                            System.out.println("Donc vous avez " + listJoueurFinal.get(adver).getGraine().getTotal() + " graines");
                                            System.out.println(listJoueurFinal.get(k).getNomDeJoueur() + " a " + listJoueurFinal.get(k).getGraine().getTotal() + " graines");
                                        } else {
                                            System.out.println(listJoueurFinal.get(k).getNomDeJoueur() + " veut prendre " + valeur[2][j] + " graines de" + listJoueurFinal.get(adver).getNomDeJoueur());
                                            ((CarteIngredient) listJoueurFinal.get(k).getListCarteAvance().get(numChoisi)).effectFarfadet(listJoueurFinal.get(k), listJoueurFinal.get(adver), valeur[2][j]);
                                            System.out.println("Donc " + listJoueurFinal.get(k).getNomDeJoueur() + " avez " + listJoueurFinal.get(k).getGraine().getTotal() + " graines");
                                            System.out.println("Donc " + listJoueurFinal.get(adver).getNomDeJoueur() + " avez " + listJoueurFinal.get(adver).getGraine().getTotal() + " graines");
                                        }
                                    }
                                }
                                if (listJoueurFinal.get(k).getAvoirCarteAllie()) {
                                    int pos1 = 0;
                                    for (int n = 0; n < listJoueurFinal.get(k).getListCarteAvance().size(); n++) {
                                        if (listJoueurFinal.get(k).getListCarteAvance().get(n) instanceof CarteAllie) {
                                            pos1 = n;
                                        }
                                    }
                                    int[][] valeur1 = listJoueurFinal.get(k).getListCarteAvance().get(pos1).getValeur();
                                    if (valeur1[0][3] != 0) {
                                        if (j == 3) {
                                            if (listJoueurFinal.get(k).getListCarteAvance().get(pos1).getNom().equals("TAUPE GEANT")) {
                                                System.out.println(listJoueurFinal.get(k).getNomDeJoueur() + " utilise carte TAUPE GEANT");
                                                int adver = listJoueurFinal.get(k).sDetruireMenhir(listJoueurFinal, k);
                                                System.out.println(listJoueurFinal.get(k).getNomDeJoueur() + " veut detruire " + valeur1[0][j] + " menhirs de " + listJoueurFinal.get(adver).getNomDeJoueur());
                                                ((CarteAllie) listJoueurFinal.get(k).getListCarteAvance().get(pos1)).TaupeGeant(listJoueurFinal.get(k), valeur1[0][j], listJoueurFinal.get(adver));
                                                System.out.println("Donc " + listJoueurFinal.get(k).getNomDeJoueur() + " a " + listJoueurFinal.get(k).getChamp().getMenhir() + " Menhirs");
                                                System.out.println("Donc " + listJoueurFinal.get(adver).getNomDeJoueur() + " a " + listJoueurFinal.get(adver).getChamp().getMenhir() + " Menhirs");
                                                listJoueurFinal.get(k).getListCarteAvance().get(pos1).PoserCarte();
                                            }
                                        }
                                    } else {
                                        int pos2 = 1;
                                        // car je pense que le menhir est pousse quand la saison est automne ou hiver
                                        if ((valeur1[0][2] >= valeur[0][1]) || (valeur1[0][2]>0)) {
                                            pos2 = 2;
                                        }
                                        if (j == pos2) {
                                            if (listJoueurFinal.get(k).getListCarteAvance().get(pos1).getNom().equals("TAUPE GEANT")) {
                                                System.out.println(listJoueurFinal.get(k).getNomDeJoueur() + "utilise carte TAUPE GEANT");
                                                int adver = listJoueurFinal.get(k).sDetruireMenhir(listJoueurFinal, k);
                                                System.out.println(listJoueurFinal.get(k).getNomDeJoueur() + " veut detruire " + valeur1[0][j] + " menhirs de " + listJoueurFinal.get(adver).getNomDeJoueur());
                                                ((CarteAllie) listJoueurFinal.get(k).getListCarteAvance().get(pos1)).TaupeGeant(listJoueurFinal.get(k), valeur1[0][j], listJoueurFinal.get(adver));
                                                System.out.println("Donc " + listJoueurFinal.get(k).getNomDeJoueur() + " a " + listJoueurFinal.get(k).getChamp().getMenhir() + " Menhirs");
                                                System.out.println("Donc " + listJoueurFinal.get(adver).getNomDeJoueur() + " a " + listJoueurFinal.get(adver).getChamp().getMenhir() + " Menhirs");
                                                listJoueurFinal.get(k).getListCarteAvance().get(pos1).PoserCarte();
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                }
            }
            // afficher les resultats
            for (int n = 0; n < listJoueurFinal.size(); n++) {
                int nombreMenhirTour = listJoueurFinal.get(n).getChamp().getMenhir();
                listJoueurFinal.get(n).getCarteComptage().ajouterMenhirTotal(nombreMenhirTour);
                System.out.println(listJoueurFinal.get(n).getNomDeJoueur() + " a " + listJoueurFinal.get(n).getGraine().getTotal() + " graines");
                System.out.println(listJoueurFinal.get(n).getNomDeJoueur() + " a " + listJoueurFinal.get(n).getCarteComptage().getNombreMenhirTotal() + " menhirs sur sa comptage");
                System.out.println("");
            }
            System.out.println("Cette Manche est finie");
            System.out.println("\n");
            // redemarrer list carte
            game.redemarrerListCarteAvance(listCarteAvance);
        }
        System.out.println("");
        System.out.println("La partie est teminee");
        System.out.println("");
        System.out.println("afficher les resultats");
        // trouver le nombre de menhirs est le plus grand
        int max = game.trouveMaxMenhir(listJoueurFinal);
        // trier list Joueur par le nombre de menhirs
        listJoueurFinal = game.triParMenhirsAvance(listJoueurFinal);
        game.afficherResultatAvance(listJoueurFinal);
        System.out.println("");
        // si le premier joueur a plus menhirs que le deuxieme -> le premier est gagne
        if (listJoueurFinal.get(0).getCarteComptage().getNombreMenhirTotal() > listJoueurFinal.get(1).getCarteComptage().getNombreMenhirTotal()) {
            System.out.println(listJoueurFinal.get(0).getNomDeJoueur() + " a gagne");
        } else {
            // sinon, on creer list de joueur a le meme menhirs
            ArrayList<Joueur> listJoueurMemeMenhirs = game.trouveMemeMenhirs(max, listJoueurFinal);
            listJoueurMemeMenhirs = game.triParGraines(listJoueurMemeMenhirs);
            int maxGraines = listJoueurMemeMenhirs.get(0).getGraine().getTotal();
            // Trouver tous les joueurs ont le meme menhirs et le meme graines 
            ArrayList<Joueur> listJoueurMemeMenhirsMemeGraines = game.trouveMemeGraines(maxGraines, listJoueurMemeMenhirs);
            // Imprimer le gagnant  
            // verifier la taille de la liste joueur meme menhirs et meme graines 
            if (listJoueurMemeMenhirsMemeGraines.size() > 1) {
                System.out.println("Le game est null entre");
                game.afficherResultatAvance(listJoueurMemeMenhirsMemeGraines);
            }
            if (listJoueurMemeMenhirsMemeGraines.size() == 1) {
                System.out.println("Le gagnant est");
                game.afficherResultatAvance(listJoueurMemeMenhirsMemeGraines);
            }
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
