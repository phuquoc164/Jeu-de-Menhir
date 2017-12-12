package Joueur;

import Carte.CarteIngredient;
import Carte.Carte;
import java.util.ArrayList;
import java.util.Scanner;

// les methodes set et get etablissent et prennent les valeurs des attributs
public class Joueur {

    Strategie strategie;
    private String nomDeJoueur;
    private boolean avoirCarteAllie;
    private String niveau;
    private int age;
    private Graine graine;
    private CarteComptage carteComptage;
    private Champ champ;
    private ArrayList<CarteIngredient> listCarte = new ArrayList();
    private ArrayList<Carte> listCarteAvance = new ArrayList();

    // constructeur
    public Joueur(String nomDeJoueur, int age, String niveau) {
        this.nomDeJoueur = nomDeJoueur;
        this.age = age;
        this.niveau = niveau;
    }

    public String getNiveau() {
        return this.niveau;
    }

    public Graine getGraine() {
        return this.graine;
    }

    public Champ getChamp() {
        return this.champ;
    }

    public CarteComptage getCarteComptage() {
        return carteComptage;
    }

    public void setCarteComptage(CarteComptage carteComptage) {
        this.carteComptage = carteComptage;
    }

    public String getNomDeJoueur() {
        return this.nomDeJoueur;
    }

    public int getAge() {
        return this.age;
    }

    public void setListCarte(ArrayList<CarteIngredient> listCarte) {
        this.listCarte = listCarte;
    }

    public ArrayList<Carte> getListCarteAvance() {
        return this.listCarteAvance;
    }

    public void setListCarteAvance(ArrayList<Carte> listCarteAvance) {
        this.listCarteAvance = listCarteAvance;
    }

    public boolean getAvoirCarteAllie() {
        return avoirCarteAllie;
    }

    public void setAvoirCarteAllie(boolean avoirCarteAllie) {
        this.avoirCarteAllie = avoirCarteAllie;
    }

    // cette methode imprime list carte (partie rapide) sur la console
    public void ImprimerListCarte() {
        for (int i = 0; i < this.listCarte.size(); i++) {
            System.out.print(this.listCarte.get(i).getNumero() + " ");
            System.out.print(this.listCarte.get(i).getNom() + " ");
            System.out.println("");
        }
    }

    // cette methode imprime list carte (regle avance) sur la console
    public void ImprimerListCarteAvance() {
        for (int i = 0; i < this.listCarteAvance.size(); i++) {
            System.out.print(this.listCarteAvance.get(i).getNumero() + " ");
            System.out.print(this.listCarteAvance.get(i).getNom() + " ");
            System.out.println("");
        }
    }

    public ArrayList<CarteIngredient> getListCarte() {
        return this.listCarte;
    }

    // donner chaque joueur 2 graine au debut de la partie rapide
    public void poserGraine() {
        graine.setTotal(2);
    }

    public void poserGraineAvance() {
        graine.setTotal(0);
    }

    public void setGraine(Graine graine) {
        this.graine = graine;
    }

    public void setChamp(Champ champ) {
        this.champ = champ;
    }

    public void setStrategie(Strategie strategie) {
        this.strategie = strategie;
    }

    public Strategie getStrategie() {
        return this.strategie;
    }

    //joueur physique chosit une carte joué (partie rapide)
    public int choisirCarte(ArrayList<CarteIngredient> listCarte) {
        System.out.println("Choisir une carte que vous n'avez pas encore joué entre:");
        for (int i = 0; i < listCarte.size(); i++) {
            boolean check = listCarte.get(i).getJouer();
            if (check == false) {
                System.out.println("Carte " + listCarte.get(i).getNumero() + " avec le numero " + i);
            }
        }
        Scanner scannumcarte = new Scanner(System.in);
        System.out.println("Quelle carte vous voulez jouer? Entrer le numero de carte: ");
        int carteChoisi = scannumcarte.nextInt();
        listCarte.get(carteChoisi).PoserCarte();
        return carteChoisi;
    }

    //joueur physique chosit une carte joué (regle avance)
    public int choisirCarteAvance(ArrayList<Carte> listCarteAvance) {
        int pos = 0;
        System.out.println("Choisir une carte que vous n'avez pas encore joué entre:");
        for (int i = 0; i < listCarteAvance.size(); i++) {
            boolean check = listCarteAvance.get(i).getJouer();
            if ((check == false) && (listCarteAvance.get(i) instanceof CarteIngredient)) {
                System.out.println("Carte " + listCarteAvance.get(i).getNumero() + " " + listCarteAvance.get(i).getNom() + " avec le numero " + i);
            }
        }
        Scanner scannumcarte = new Scanner(System.in);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Quelle carte Ingredient vous voulez jouer? Entrer le numero de carte: ");
        int carteChoisi = scannumcarte.nextInt();
        listCarteAvance.get(carteChoisi).PoserCarte();
        return carteChoisi;
    }

    // joueur physique choisit force
    public String JouerCarte(CarteIngredient carte, int saison) {
        int[][] valeur;
        valeur = carte.getValeur();
        System.out.println("Ceci les valeurs de chaque force");
        System.out.println("Geant: " + valeur[0][saison]);
        System.out.println("Engrais: " + valeur[1][saison]);
        System.out.println("Farfadet: " + valeur[2][saison]);
        Scanner scanforce = new Scanner(System.in);
        System.out.println("Vous choisissez quel force?");
        String force = scanforce.nextLine();
        return force;
    }

    // joueur physique choisit l'adver qu'il prend les graines
    public int volerJoueur(ArrayList<Joueur> listJoueur, Joueur j, int pos) {
        System.out.println("Voici la liste d'autre joueurs");
        for (int k = 0; k < listJoueur.size(); k++) {
            if (k != pos) {
                System.out.println("Entrer numero " + k + " pour: " + listJoueur.get(k).getNomDeJoueur() + " avec " + listJoueur.get(k).getGraine().getTotal() + " graines");
            }
        }
        Scanner scanadver = new Scanner(System.in);
        System.out.println("Vous voulez voler à quel joueur? Entrez le numero de joueur: ");
        int adver = scanadver.nextInt();
        return adver;
    }

    // // joueur physique choisit l'adver qu'il detruit les menhirs
    public int detruireMenhir(ArrayList<Joueur> listJoueur, int pos) {
        System.out.println("Voici la liste d'autre joueurs");
        for (int k = 0; k < listJoueur.size(); k++) {
            if (k != pos) {
                System.out.println("Entrer numero " + k + " pour: " + listJoueur.get(k).getNomDeJoueur() + " avec " + listJoueur.get(k).getChamp().getMenhir() + " Menhirs Aldults");
            }
        }
        Scanner scanadver = new Scanner(System.in);
        System.out.println("Vous voulez detruire menhir à quel jouer? Entrez le numero de joueur: ");
        int adver = scanadver.nextInt();
        return adver;
    }

    // joueur virtuel choisit l'adver qu'il detruit les menhirs
    public int sDetruireMenhir(ArrayList<Joueur> listJoueur, int pos) {
        return this.strategie.sDetruireMenhir(listJoueur, pos);
    }

    // joueur virtuel choisit une carte joue (partie rapide)
    public int choisiVirtuel() {
        int numChoisi = this.strategie.sChoisir(this.getListCarte());
        return numChoisi;
    }

    // joueur virtuel choisit une carte joue (regle avance)
    public int choisiVirtuelAvance() {
        return this.strategie.sChoisirAvance(this.getListCarteAvance());
    }

    // joueur virtuel choisit force (partie rapide)
    public String joueVirtuel(CarteIngredient carte, int saison, int numGraine) {
        String force = this.strategie.sJouer(carte, saison, this.getGraine().getTotal());
        return force;
    }
    
    // joueur virtuel choisit force (regle avance)
    public String joueVirtuelAvance(Carte carte, int saison, int numGraine) {
        return this.strategie.sJouerAvance(carte, saison, this.getGraine().getTotal());
    }

    // joueur virtuel vole lé graines
    public int volerVirtuel(ArrayList<Joueur> listJouer, int pos, int saison) {
        int adver = this.strategie.sVoler(listJouer, pos, saison);
        return adver;
    }
}
