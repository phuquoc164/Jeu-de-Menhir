package GUI;

import Carte.Carte;
import Carte.CarteIngredient;
import Game.Game;
import Game.Manche;
import Joueur.CarteComptage;
import Joueur.Champ;
import Joueur.Graine;
import Joueur.Joueur;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;

public class Partie extends Observable {

    private int choix;
    private Game game;
    private ArrayList<Joueur> listJoueur = new ArrayList<Joueur>();
    private int nombreDeJoueur;
    private Manche listeManche;
    private String modeJouer;
    private String niveau;
    private Manche manche;
    private boolean viewChanged = false;
    private ArrayList<Joueur> jouerGagne = new ArrayList<Joueur>();
    private ArrayList<Joueur> listeGagnant = new ArrayList<Joueur>();
    public int dem;
    private Carte carteEnCours;

    public void setChoix(int i) {
        this.choix = i;
    }

    public int getChoix() {
        return this.choix;
    }

    public Carte getCarteEnCours() {
        return carteEnCours;
    }

    public void setCarteEnCours(Carte carteEnCours) {
        this.carteEnCours = carteEnCours;
    }

    public ArrayList<Joueur> getListeGagnant() {
        return listeGagnant;
    }

    public void setListeGagnant(ArrayList<Joueur> listeGagnant) {
        this.listeGagnant = listeGagnant;
    }

    public boolean isViewChanged() {
        return viewChanged;
    }

    public void setViewChanged(boolean viewChanged) {
        this.viewChanged = viewChanged;
    }

    public Joueur getJoueurGagner(int i) {
        return this.jouerGagne.get(i);
    }

    public int getNombreDeJoueur() {
        return this.nombreDeJoueur;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public String getNiveau() {
        return this.niveau;
    }

    public void setNombreDeJoueur(int nombreDeJoueur) {
        this.nombreDeJoueur = nombreDeJoueur;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return this.game;
    }

    public ArrayList<Joueur> getJoueur_final() {
        return this.listJoueur;
    }

    public void setJoueur_final(ArrayList<Joueur> joueur_final) {
        this.listJoueur = joueur_final;
    }

    // on commence jouer
    public void start(int nbrJoueurJeu, String nom, int age, String modeJouer, String niveau) {
        try {
            this.nombreDeJoueur = nbrJoueurJeu;
            ArrayList<Joueur> joueur = new ArrayList<Joueur>(nbrJoueurJeu);
            String niveau1 = "Physique";
            Joueur jo = new Joueur(nom, age, niveau1);
            joueur.add(jo);
            for (int i = 1; i < nombreDeJoueur; i++) {
                String nomVirtuel = "JoueurVirtuel" + i;
                int ageVirtuel = rand(18, 30);
                Joueur jo1 = new Joueur(nomVirtuel, ageVirtuel, niveau);
                joueur.add(jo1);
            }
            ArrayList<Joueur> joueur_final = new ArrayList();
            this.modeJouer = modeJouer;
            if (modeJouer.equalsIgnoreCase("Rapide")) {
                Game game1 = new Game();
                game1.setPartieRapide(true);
                game1.setNombreDeJoueur(nombreDeJoueur);
                joueur_final = game1.construireTour(joueur);
                this.envoyerMessage("Le joueur " + joueur_final.get(0).getNomDeJoueur() + " est le premier joueur");
                // Initialiser 24 cartes ingredients
                ArrayList<CarteIngredient> listCarte_init = new ArrayList();
                for (int i = 0; i < 24; i++) {
                    CarteIngredient carte = new CarteIngredient(i);
                    listCarte_init.add(carte);
                }
                game1.creerListCarteIngredient(listCarte_init);
                //Commencer le game par placant champ et posant 2 graines 
                for (int i = 0; i < joueur_final.size(); i++) {
                    Champ champ = new Champ();
                    Graine graine = new Graine();
                    joueur_final.get(i).setChamp(champ);
                    joueur_final.get(i).setGraine(graine);
                    joueur_final.get(i).poserGraine();
                }
                // Distribuer cartes pour tous les joueurs
                for (int i = 0; i < joueur_final.size(); i++) {
                    game1.distribuerCarteIngredient(listCarte_init, joueur_final.get(i));
                }
                setJoueur_final(joueur_final);
                setGame(game1);
                this.niveau = niveau;
                this.updateVue("rapide");
                this.updateVue("finiRapide");

            } else if (modeJouer.equalsIgnoreCase("Avancee")) {
                Game game2 = new Game();
                game2.setPartieRapide(false);
                // initialiser 24 cartes Ingrdients
                ArrayList<CarteIngredient> listCarteIngredient = new ArrayList();
                for (int i = 0; i < 24; i++) {
                    CarteIngredient carte = new CarteIngredient(i);
                    listCarteIngredient.add(carte);
                }
                game2.creerListCarteIngredient(listCarteIngredient);

                // initialiser 24 cartesingredient et 6 carte Allie
                ArrayList<Carte> listCarteAvance = new ArrayList();
                game2.creerListCarteAvance(listCarteAvance);
                // creer liste Joueur 
                ArrayList<Joueur> listJoueurFinal = new ArrayList();
                listJoueurFinal = game2.construireTour(joueur);
                // creer une carte comptage pour chaque joueur
                for (int j = 0; j < listJoueurFinal.size(); j++) {
                    CarteComptage carteComptage = new CarteComptage();
                    listJoueurFinal.get(j).setCarteComptage(carteComptage);
                }
                for (int i = 0; i < nombreDeJoueur; i++) {
                    for (int n = 0; n < listCarteAvance.size(); n++) {
                        listCarteAvance.get(n).setEstChoisi(false);
                        listCarteAvance.get(n).setEstJoue(false);
                    }
                    Manche manche = new Manche();
                    // changer le joueur qui commence en premier chaque manche
                    if (i > 0) {
                        listJoueurFinal = game.changerManche(listJoueurFinal);
                        System.out.println(listJoueurFinal.get(0).getNomDeJoueur() + " va commencer cette manche");
                    }
                    // demander joueur physique vouler une carte Allie ou 2 graines
                    for (int j = 0; j < listJoueurFinal.size(); j++) {
                        if (listJoueurFinal.get(j).getNiveau().equalsIgnoreCase("Physique")) {
                            this.updateVue("tirerAllie");
                            while (!this.isViewChanged()) {
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                            }
                            this.setViewChanged(false);
                            if (this.choix == 1) {
                                listJoueurFinal.get(j).setAvoirCarteAllie(true);
                            } else {
                                listJoueurFinal.get(j).setAvoirCarteAllie(false);
                            }
                        } else {
                            // choisir aleatoire entre 0 et 1 pour prendre une carte Allie ou 2 graines pour joueur Virtuel
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
                            listJoueurFinal.get(j).getChamp().setZero();
                            listJoueurFinal.get(j).setGraine(graine);
                            listJoueurFinal.get(j).poserGraineAvance();
                        } else {
                            Champ champ = new Champ();
                            Graine graine = new Graine();
                            listJoueurFinal.get(j).setChamp(champ);
                            listJoueurFinal.get(j).getChamp().setZero();
                            listJoueurFinal.get(j).setGraine(graine);
                            listJoueurFinal.get(j).poserGraine();
                        }
                    }
                    // creer list carte pour chaque joueur          
                    for (int j = 0; j < listJoueurFinal.size(); j++) {
                        if (listJoueurFinal.get(j).getAvoirCarteAllie()) {
                            game2.distribuerCarteAvance(listCarteAvance, listJoueurFinal.get(j));
                        } else {
                            game2.distribuerCarteAvance(listCarteAvance, listJoueurFinal.get(j));
                        }
                    }

                    setJoueur_final(listJoueurFinal);
                    setGame(game2);
                    this.niveau = niveau;
                    this.updateVue("avancee");
                }
                this.updateVue("finiAvancee");
            }
        } catch (Exception e) {
            System.out.println("type input n'est pas correct");
            e.printStackTrace();
        }
    }

    public void updateVue(Object arg1) {
        this.setChanged();
        this.notifyObservers(arg1);

    }
    private String message;

    public String getMessage() {
        return message;
    }

    public String getModeJouer() {
        return this.modeJouer;
    }

    public void setModeJouer(String mode) {
        this.modeJouer = mode;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void envoyerMessage(String s) {
        System.out.println(s);
        this.message = s;
    }

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
