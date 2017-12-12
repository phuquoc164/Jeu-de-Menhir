package Game;

import Carte.CarteIngredient;
import Carte.Carte;
import Carte.CarteAllie;
import Joueur.*;
import java.util.ArrayList;
import java.util.Random;

public class Game {

    private boolean partieRapide;
    private int nombreDeJoueur;
    private String niveau;
    private Manche manche;
    private boolean type;

    public void setNombreDeJoueur(int nombreDeJoueur) {
        this.nombreDeJoueur = nombreDeJoueur;
    }

    public int getNombreDeJoueur() {
        return this.nombreDeJoueur;
    }

    public void setPartieRapide(boolean partieRapide) {
        this.partieRapide = partieRapide;
    }

    public boolean getPartieRapide() {
        return this.partieRapide;
    }

    // distribuer carte dans la partie rapide
    public void distribuerCarteIngredient(ArrayList<CarteIngredient> listCarte_init, Joueur j) {
        ArrayList<CarteIngredient> listCarte = new ArrayList();
        int count = 0;
        int numCarte;
        while (count < 4) {
            numCarte = rand(0, 23);
            if (listCarte_init.get(numCarte).getChoisir() == false) {
                listCarte.add(listCarte_init.get(numCarte));
                listCarte_init.get(numCarte).ChoisirCarte();
                count++;
            } else {
                numCarte = rand(0, 23);
            }
        }
        j.setListCarte(listCarte);
    }

    // distribuer les cartes dans la partie avance, il y a plus de une carte Allie
    public void distribuerCarteAvance(ArrayList<Carte> listCarteAvance, Joueur joueur) {
        ArrayList<Carte> listCarte = new ArrayList();
        int count = 0;
        int numCarte;
        while (count < 4) {
            numCarte = rand(0, 23);
            if (listCarteAvance.get(numCarte).getChoisir() == false) {
                listCarte.add(listCarteAvance.get(numCarte));
                listCarteAvance.get(numCarte).ChoisirCarte();
                count++;
            } else {
                numCarte = rand(0, 23);
            }
        }
        if (joueur.getAvoirCarteAllie()) {
            numCarte = rand(24, 29);
            count = 0;
            while (count < 1) {
                if (listCarteAvance.get(numCarte).getChoisir() == false) {
                    listCarte.add(listCarteAvance.get(numCarte));
                    listCarteAvance.get(numCarte).ChoisirCarte();
                    count++;
                } else {
                    numCarte = rand(24, 29);
                }
            }

        }

        joueur.setListCarteAvance(listCarte);
    }

    // afficher les resultat à la fin de la partie rapide
    public void afficherResultat(ArrayList<Joueur> listJoueur) {
        for (int i = 0; i < listJoueur.size(); i++) {
            System.out.println(listJoueur.get(i).getNomDeJoueur() + " a " + listJoueur.get(i).getChamp().getMenhir() + " menhirs et " + listJoueur.get(i).getGraine().getTotal() + " graines.");
        }
    }

    // apres une manche, on doit redemarrer list carte dans la partie avance pour la manche prochaine
    public void redemarrerListCarteAvance(ArrayList<Carte> listCarte) {
        for (int n = 0; n < listCarte.size(); n++) {
            listCarte.get(n).setEstChoisi(false);
            listCarte.get(n).setEstJoue(false);
        }
    }

    // afficher les resultat à la fin de la partie avance
    public void afficherResultatAvance(ArrayList<Joueur> listJoueur) {
        for (int i = 0; i < listJoueur.size(); i++) {
            System.out.println(listJoueur.get(i).getNomDeJoueur() + " a " + listJoueur.get(i).getCarteComptage().getNombreMenhirTotal() + " menhirs et " + listJoueur.get(i).getGraine().getTotal() + " graines (les graines dans la derniere manche).");
        }
    }

    // trouver max Menhir dans la partie Rapide
    public int trouveMaxMenhir(ArrayList<Joueur> listJoueur) {
        int max = listJoueur.get(0).getChamp().getMenhir();
        for (int i = 1; i < listJoueur.size(); i++) {
            if (listJoueur.get(i).getChamp().getMenhir() >= max) {
                max = listJoueur.get(i).getChamp().getMenhir();
            }
        }
        return max;
    }

    //trouver max Menhir dans la partie Avance
    public int trouveMaxMenhirAvance(ArrayList<Joueur> listJoueur) {
        int max = listJoueur.get(0).getCarteComptage().getNombreMenhirTotal();
        for (int i = 1; i < listJoueur.size(); i++) {
            if (listJoueur.get(i).getCarteComptage().getNombreMenhirTotal() >= max) {
                max = listJoueur.get(i).getCarteComptage().getNombreMenhirTotal();
            }
        }
        return max;
    }

    // trouver max graine
    public int trouveMaxGraine(ArrayList<Joueur> listJoueur) {
        int max = listJoueur.get(0).getGraine().getTotal();
        for (int i = 1; i < listJoueur.size(); i++) {
            if (listJoueur.get(0).getGraine().getTotal() >= max) {
                max = listJoueur.get(0).getGraine().getTotal();
            }
        }
        return max;
    }

    // trier list Joueur par le nombre de menhirs dans la partie Rapide
    public ArrayList<Joueur> triParMenhirs(ArrayList<Joueur> listJoueur) {
        for (int i = 0; i < listJoueur.size() - 1; i++) {
            for (int j = i + 1; j < listJoueur.size(); j++) {
                if (listJoueur.get(j).getChamp().getMenhir() > listJoueur.get(i).getChamp().getMenhir()) {
                    Joueur tg = listJoueur.get(i);
                    listJoueur.set(i, listJoueur.get(j));
                    listJoueur.set(j, tg);
                }
            }
        }
        return listJoueur;
    }

    // trier list joueur par le nombre de menhirs dans la partie Avance
    public ArrayList<Joueur> triParMenhirsAvance(ArrayList<Joueur> listJoueur) {
        for (int i = 0; i < listJoueur.size() - 1; i++) {
            for (int j = i + 1; j < listJoueur.size(); j++) {
                if (listJoueur.get(j).getCarteComptage().getNombreMenhirTotal() > listJoueur.get(i).getCarteComptage().getNombreMenhirTotal()) {
                    Joueur tg = listJoueur.get(i);
                    listJoueur.set(i, listJoueur.get(j));
                    listJoueur.set(j, tg);
                }
            }
        }
        return listJoueur;
    }

    // trier par graine
    public ArrayList<Joueur> triParGraines(ArrayList<Joueur> listJoueur) {
        for (int i = 0; i < listJoueur.size() - 1; i++) {
            for (int j = i + 1; j < listJoueur.size(); j++) {
                if (listJoueur.get(j).getGraine().getTotal() > listJoueur.get(i).getGraine().getTotal()) {
                    Joueur tg = listJoueur.get(i);
                    listJoueur.set(i, listJoueur.get(j));
                    listJoueur.set(j, tg);
                }
            }
        }
        return listJoueur;
    }

    // trouver les joueurs avoir le meme nombre de menhirs dans la partie Rapide
    public ArrayList<Joueur> trouveMemeMenhirs(int max, ArrayList<Joueur> listJoueur) {
        ArrayList<Joueur> listJoueurMemeMenhirs = new ArrayList();
        for (int i = 0; i < listJoueur.size(); i++) {
            if (listJoueur.get(i).getChamp().getMenhir() == max) {
                listJoueurMemeMenhirs.add(listJoueur.get(i));
            }
        }

        return listJoueurMemeMenhirs;
    }

    // trouver les joueurs avoir le meme nombre de menhirs dans la partie Avance
    public ArrayList<Joueur> trouveMemeMenhirsAvance(int max, ArrayList<Joueur> listJoueur) {
        ArrayList<Joueur> listJoueurMemeMenhirs = new ArrayList();
        for (int i = 0; i < listJoueur.size(); i++) {
            if (listJoueur.get(i).getCarteComptage().getNombreMenhirTotal() == max) {
                listJoueurMemeMenhirs.add(listJoueur.get(i));
            }
        }

        return listJoueurMemeMenhirs;
    }

    // trouver les joueurs avoir le meme nombre de graines
    public ArrayList<Joueur> trouveMemeGraines(int max, ArrayList<Joueur> listJoueur) {
        ArrayList<Joueur> listJoueurMemeMenhirsMemeGraines = new ArrayList();
        for (int i = 0; i < listJoueur.size(); i++) {
            if (listJoueur.get(i).getGraine().getTotal() == max) {
                listJoueurMemeMenhirsMemeGraines.add(listJoueur.get(i));
            }
        }

        return listJoueurMemeMenhirsMemeGraines;
    }

    // creer list Carte Ingredient
    public void creerListCarteIngredient(ArrayList<CarteIngredient> listCarteIngredient) {

        listCarteIngredient.get(0).setNom("RAYON DE LUNE");
        listCarteIngredient.get(0).setImage("/GUI/image/ingredient/rayon de lune 1.png");
        int[][] valeur0 = new int[3][4];
        valeur0[0][0] = 1;
        valeur0[0][1] = 1;
        valeur0[0][2] = 1;
        valeur0[0][3] = 1;
        valeur0[1][0] = 2;
        valeur0[1][1] = 0;
        valeur0[1][2] = 1;
        valeur0[1][3] = 1;
        valeur0[2][0] = 2;
        valeur0[2][1] = 0;
        valeur0[2][2] = 2;
        valeur0[2][3] = 0;
        listCarteIngredient.get(0).setValeur(valeur0);

        listCarteIngredient.get(1).setNom("RAYON DE LUNE");
        listCarteIngredient.get(1).setImage("/GUI/image/ingredient/rayon de lune 2.png");
        int[][] valeur1 = new int[3][4];
        valeur1[0][0] = 2;
        valeur1[0][1] = 0;
        valeur1[0][2] = 1;
        valeur1[0][3] = 1;
        valeur1[1][0] = 1;
        valeur1[1][1] = 3;
        valeur1[1][2] = 0;
        valeur1[1][3] = 0;
        valeur1[2][0] = 0;
        valeur1[2][1] = 1;
        valeur1[2][2] = 2;
        valeur1[2][3] = 1;
        listCarteIngredient.get(1).setValeur(valeur1);

        listCarteIngredient.get(2).setNom("RAYON DE LUNE");
        listCarteIngredient.get(2).setImage("/GUI/image/ingredient/rayon de lune 3.png");
        int[][] valeur2 = new int[3][4];
        valeur2[0][0] = 0;
        valeur2[0][1] = 0;
        valeur2[0][2] = 4;
        valeur2[0][3] = 0;
        valeur2[1][0] = 0;
        valeur2[1][1] = 2;
        valeur2[1][2] = 2;
        valeur2[1][3] = 0;
        valeur2[2][0] = 0;
        valeur2[2][1] = 0;
        valeur2[2][2] = 1;
        valeur2[2][3] = 3;
        listCarteIngredient.get(2).setValeur(valeur2);

        listCarteIngredient.get(3).setNom("CHANT DE SIRENE");
        listCarteIngredient.get(3).setImage("/GUI/image/ingredient/chant de sirene 1.png");
        int[][] valeur3 = new int[3][4];
        valeur3[0][0] = 1;
        valeur3[0][1] = 3;
        valeur3[0][2] = 1;
        valeur3[0][3] = 0;
        valeur3[1][0] = 1;
        valeur3[1][1] = 2;
        valeur3[1][2] = 1;
        valeur3[1][3] = 1;
        valeur3[2][0] = 0;
        valeur3[2][1] = 1;
        valeur3[2][2] = 4;
        valeur3[2][3] = 0;
        listCarteIngredient.get(3).setValeur(valeur3);

        listCarteIngredient.get(4).setNom("CHANT DE SIRENE");
        listCarteIngredient.get(4).setImage("/GUI/image/ingredient/chant de sirene 2.png");
        int[][] valeur4 = new int[3][4];
        valeur4[0][0] = 2;
        valeur4[0][1] = 1;
        valeur4[0][2] = 1;
        valeur4[0][3] = 1;
        valeur4[1][0] = 1;
        valeur4[1][1] = 0;
        valeur4[1][2] = 2;
        valeur4[1][3] = 2;
        valeur4[2][0] = 3;
        valeur4[2][1] = 0;
        valeur4[2][2] = 0;
        valeur4[2][3] = 2;
        listCarteIngredient.get(4).setValeur(valeur4);

        listCarteIngredient.get(5).setNom("CHANT DE SIRENE");
        listCarteIngredient.get(5).setImage("/GUI/image/ingredient/chant de sirene 3.png");
        int[][] valeur5 = new int[3][4];
        valeur5[0][0] = 1;
        valeur5[0][1] = 2;
        valeur5[0][2] = 2;
        valeur5[0][3] = 0;
        valeur5[1][0] = 1;
        valeur5[1][1] = 1;
        valeur5[1][2] = 2;
        valeur5[1][3] = 1;
        valeur5[2][0] = 2;
        valeur5[2][1] = 0;
        valeur5[2][2] = 1;
        valeur5[2][3] = 2;
        listCarteIngredient.get(5).setValeur(valeur5);

        listCarteIngredient.get(6).setNom("LARMES DE DRYADE");
        listCarteIngredient.get(6).setImage("/GUI/image/ingredient/larmes de dryade 1.png");
        int[][] valeur6 = new int[3][4];
        valeur6[0][0] = 2;
        valeur6[0][1] = 1;
        valeur6[0][2] = 1;
        valeur6[0][3] = 2;
        valeur6[1][0] = 1;
        valeur6[1][1] = 1;
        valeur6[1][2] = 1;
        valeur6[1][3] = 3;
        valeur6[2][0] = 2;
        valeur6[2][1] = 0;
        valeur6[2][2] = 0;
        valeur6[2][3] = 2;
        listCarteIngredient.get(6).setValeur(valeur6);

        listCarteIngredient.get(7).setNom("LARMES DE DRYADE");
        listCarteIngredient.get(7).setImage("/GUI/image/ingredient/larmes de dryade 2.png");
        int[][] valeur7 = new int[3][4];
        valeur7[0][0] = 0;
        valeur7[0][1] = 3;
        valeur7[0][2] = 0;
        valeur7[0][3] = 3;
        valeur7[1][0] = 2;
        valeur7[1][1] = 1;
        valeur7[1][2] = 3;
        valeur7[1][3] = 0;
        valeur7[2][0] = 1;
        valeur7[2][1] = 1;
        valeur7[2][2] = 3;
        valeur7[2][3] = 1;
        listCarteIngredient.get(7).setValeur(valeur7);

        listCarteIngredient.get(8).setNom("LARMES DE DRYADE");
        listCarteIngredient.get(8).setImage("/GUI/image/ingredient/larmes de dryade 3.png");
        int[][] valeur8 = new int[3][4];
        valeur8[0][0] = 1;
        valeur8[0][1] = 2;
        valeur8[0][2] = 1;
        valeur8[0][3] = 2;
        valeur8[1][0] = 1;
        valeur8[1][1] = 0;
        valeur8[1][2] = 1;
        valeur8[1][3] = 4;
        valeur8[2][0] = 2;
        valeur8[2][1] = 4;
        valeur8[2][2] = 0;
        valeur8[2][3] = 0;
        listCarteIngredient.get(8).setValeur(valeur8);

        listCarteIngredient.get(9).setNom("FONTAINE D'EAU PURE");
        listCarteIngredient.get(9).setImage("/GUI/image/ingredient/fontaine d'eau pure 1.png");
        int[][] valeur9 = new int[3][4];
        valeur9[0][0] = 1;
        valeur9[0][1] = 3;
        valeur9[0][2] = 1;
        valeur9[0][3] = 2;
        valeur9[1][0] = 2;
        valeur9[1][1] = 1;
        valeur9[1][2] = 2;
        valeur9[1][3] = 2;
        valeur9[2][0] = 0;
        valeur9[2][1] = 0;
        valeur9[2][2] = 3;
        valeur9[2][3] = 4;
        listCarteIngredient.get(9).setValeur(valeur9);

        listCarteIngredient.get(10).setNom("FONTAINE D'EAU PURE");
        listCarteIngredient.get(10).setImage("/GUI/image/ingredient/fontaine d'eau pure 2.png");
        int[][] valeur10 = new int[3][4];
        valeur10[0][0] = 2;
        valeur10[0][1] = 2;
        valeur10[0][2] = 0;
        valeur10[0][3] = 3;
        valeur10[1][0] = 1;
        valeur10[1][1] = 1;
        valeur10[1][2] = 4;
        valeur10[1][3] = 1;
        valeur10[2][0] = 1;
        valeur10[2][1] = 2;
        valeur10[2][2] = 1;
        valeur10[2][3] = 3;
        listCarteIngredient.get(10).setValeur(valeur10);

        listCarteIngredient.get(11).setNom("FONTAINE D'EAU PURE");
        listCarteIngredient.get(11).setImage("/GUI/image/ingredient/fontaine d'eau pure 3.png");
        int[][] valeur11 = new int[3][4];
        valeur11[0][0] = 2;
        valeur11[0][1] = 2;
        valeur11[0][2] = 3;
        valeur11[0][3] = 1;
        valeur11[1][0] = 2;
        valeur11[1][1] = 3;
        valeur11[1][2] = 0;
        valeur11[1][3] = 3;
        valeur11[2][0] = 1;
        valeur11[2][1] = 1;
        valeur11[2][2] = 3;
        valeur11[2][3] = 3;
        listCarteIngredient.get(11).setValeur(valeur11);

        listCarteIngredient.get(12).setNom("POUDRE D'OR");
        listCarteIngredient.get(12).setImage("/GUI/image/ingredient/poudre d'or 1.png");
        int[][] valeur12 = new int[3][4];
        valeur12[0][0] = 2;
        valeur12[0][1] = 2;
        valeur12[0][2] = 3;
        valeur12[0][3] = 1;
        valeur12[1][0] = 2;
        valeur12[1][1] = 3;
        valeur12[1][2] = 0;
        valeur12[1][3] = 3;
        valeur12[2][0] = 1;
        valeur12[2][1] = 1;
        valeur12[2][2] = 3;
        valeur12[2][3] = 3;
        listCarteIngredient.get(12).setValeur(valeur12);

        listCarteIngredient.get(13).setNom("POUDRE D'OR");
        listCarteIngredient.get(13).setImage("/GUI/image/ingredient/poudre d'or 2.png");
        int[][] valeur13 = new int[3][4];
        valeur13[0][0] = 2;
        valeur13[0][1] = 2;
        valeur13[0][2] = 2;
        valeur13[0][3] = 2;
        valeur13[1][0] = 0;
        valeur13[1][1] = 4;
        valeur13[1][2] = 4;
        valeur13[1][3] = 0;
        valeur13[2][0] = 1;
        valeur13[2][1] = 3;
        valeur13[2][2] = 2;
        valeur13[2][3] = 2;
        listCarteIngredient.get(13).setValeur(valeur13);

        listCarteIngredient.get(14).setNom("POUDRE D'OR");
        listCarteIngredient.get(14).setImage("/GUI/image/ingredient/poudre d'or 3.png");
        int[][] valeur14 = new int[3][4];
        valeur14[0][0] = 3;
        valeur14[0][1] = 1;
        valeur14[0][2] = 3;
        valeur14[0][3] = 1;
        valeur14[1][0] = 1;
        valeur14[1][1] = 4;
        valeur14[1][2] = 2;
        valeur14[1][3] = 1;
        valeur14[2][0] = 2;
        valeur14[2][1] = 4;
        valeur14[2][2] = 1;
        valeur14[2][3] = 1;
        listCarteIngredient.get(14).setValeur(valeur14);

        listCarteIngredient.get(15).setNom("RACINES D'ARC-EN-CIEL");
        listCarteIngredient.get(15).setImage("/GUI/image/ingredient/racines d'arc-en-ciel 1.png");
        int[][] valeur15 = new int[3][4];
        valeur15[0][0] = 4;
        valeur15[0][1] = 1;
        valeur15[0][2] = 1;
        valeur15[0][3] = 1;
        valeur15[1][0] = 1;
        valeur15[1][1] = 2;
        valeur15[1][2] = 1;
        valeur15[1][3] = 3;
        valeur15[2][0] = 1;
        valeur15[2][1] = 2;
        valeur15[2][2] = 2;
        valeur15[2][3] = 2;
        listCarteIngredient.get(15).setValeur(valeur15);

        listCarteIngredient.get(16).setNom("RACINES D'ARC-EN-CIEL");
        listCarteIngredient.get(16).setImage("/GUI/image/ingredient/racines d'arc-en-ciel 2.png");
        int[][] valeur16 = new int[3][4];
        valeur16[0][0] = 2;
        valeur16[0][1] = 3;
        valeur16[0][2] = 2;
        valeur16[0][3] = 0;
        valeur16[1][0] = 0;
        valeur16[1][1] = 4;
        valeur16[1][2] = 3;
        valeur16[1][3] = 0;
        valeur16[2][0] = 2;
        valeur16[2][1] = 1;
        valeur16[2][2] = 1;
        valeur16[2][3] = 3;
        listCarteIngredient.get(16).setValeur(valeur16);

        listCarteIngredient.get(17).setNom("RACINES D'ARC-EN-CIEL");
        listCarteIngredient.get(17).setImage("/GUI/image/ingredient/racines d'arc-en-ciel 3.png");
        int[][] valeur17 = new int[3][4];
        valeur17[0][0] = 2;
        valeur17[0][1] = 2;
        valeur17[0][2] = 3;
        valeur17[0][3] = 0;
        valeur17[1][0] = 1;
        valeur17[1][1] = 1;
        valeur17[1][2] = 1;
        valeur17[1][3] = 4;
        valeur17[2][0] = 2;
        valeur17[2][1] = 0;
        valeur17[2][2] = 3;
        valeur17[2][3] = 2;
        listCarteIngredient.get(17).setValeur(valeur17);

        listCarteIngredient.get(18).setNom("ESPRIT DE DOLMEN");
        listCarteIngredient.get(18).setImage("/GUI/image/ingredient/esprit de dolmen 1.png");
        int[][] valeur18 = new int[3][4];
        valeur18[0][0] = 3;
        valeur18[0][1] = 1;
        valeur18[0][2] = 4;
        valeur18[0][3] = 1;
        valeur18[1][0] = 2;
        valeur18[1][1] = 1;
        valeur18[1][2] = 3;
        valeur18[1][3] = 3;
        valeur18[2][0] = 2;
        valeur18[2][1] = 3;
        valeur18[2][2] = 2;
        valeur18[2][3] = 2;
        listCarteIngredient.get(18).setValeur(valeur18);

        listCarteIngredient.get(19).setNom("ESPRIT DE DOLMEN");
        listCarteIngredient.get(19).setImage("/GUI/image/ingredient/esprit de dolmen 2.png");
        int[][] valeur19 = new int[3][4];
        valeur19[0][0] = 2;
        valeur19[0][1] = 4;
        valeur19[0][2] = 1;
        valeur19[0][3] = 2;
        valeur19[1][0] = 2;
        valeur19[1][1] = 2;
        valeur19[1][2] = 2;
        valeur19[1][3] = 3;
        valeur19[2][0] = 1;
        valeur19[2][1] = 4;
        valeur19[2][2] = 3;
        valeur19[2][3] = 1;
        listCarteIngredient.get(19).setValeur(valeur19);

        listCarteIngredient.get(20).setNom("ESPRIT DE DOLMEN");
        listCarteIngredient.get(20).setImage("/GUI/image/ingredient/esprit de dolmen 3.png");
        int[][] valeur20 = new int[3][4];
        valeur20[0][0] = 3;
        valeur20[0][1] = 3;
        valeur20[0][2] = 3;
        valeur20[0][3] = 0;
        valeur20[1][0] = 1;
        valeur20[1][1] = 3;
        valeur20[1][2] = 3;
        valeur20[1][3] = 2;
        valeur20[2][0] = 2;
        valeur20[2][1] = 3;
        valeur20[2][2] = 1;
        valeur20[2][3] = 3;
        listCarteIngredient.get(20).setValeur(valeur20);

        listCarteIngredient.get(21).setNom("RIRES DE FEES");
        listCarteIngredient.get(21).setImage("/GUI/image/ingredient/rires de fees 1.png");
        int[][] valeur21 = new int[3][4];
        valeur21[0][0] = 1;
        valeur21[0][1] = 2;
        valeur21[0][2] = 2;
        valeur21[0][3] = 1;
        valeur21[1][0] = 1;
        valeur21[1][1] = 2;
        valeur21[1][2] = 3;
        valeur21[1][3] = 0;
        valeur21[2][0] = 0;
        valeur21[2][1] = 2;
        valeur21[2][2] = 2;
        valeur21[2][3] = 2;
        listCarteIngredient.get(21).setValeur(valeur21);

        listCarteIngredient.get(22).setNom("RIRES DE FEES");
        listCarteIngredient.get(22).setImage("/GUI/image/ingredient/rires de fees 2.png");
        int[][] valeur22 = new int[3][4];
        valeur22[0][1] = 0;
        valeur22[0][2] = 1;
        valeur22[0][3] = 1;
        valeur22[1][0] = 1;
        valeur22[1][1] = 1;
        valeur22[1][2] = 3;
        valeur22[1][3] = 1;
        valeur22[2][0] = 0;
        valeur22[2][1] = 0;
        valeur22[2][2] = 3;
        valeur22[2][3] = 3;
        listCarteIngredient.get(22).setValeur(valeur22);

        listCarteIngredient.get(23).setNom("RIRES DE FEES");
        listCarteIngredient.get(23).setImage("/GUI/image/ingredient/rires de fees 3.png");
        int[][] valeur23 = new int[3][4];
        valeur23[0][0] = 2;
        valeur23[0][1] = 0;
        valeur23[0][2] = 1;
        valeur23[0][3] = 3;
        valeur23[1][0] = 0;
        valeur23[1][1] = 3;
        valeur23[1][2] = 0;
        valeur23[1][3] = 3;
        valeur23[2][0] = 1;
        valeur23[2][1] = 2;
        valeur23[2][2] = 2;
        valeur23[2][3] = 1;
        listCarteIngredient.get(23).setValeur(valeur23);
    }

    // creer list carte Avance, il y a les cartes Allies
    public void creerListCarteAvance(ArrayList<Carte> listCarteAvance) {
        ArrayList<CarteIngredient> listCarteIngredient = new ArrayList();
        for (int i = 0; i < 24; i++) {
            CarteIngredient carte = new CarteIngredient(i);
            listCarteIngredient.add(carte);
        }
        this.creerListCarteIngredient(listCarteIngredient);
        for (int i = 0; i < 24; i++) {
            listCarteAvance.add(listCarteIngredient.get(i));
        }
        for (int i = 24; i < 30; i++) {
            CarteAllie carte = new CarteAllie(i);
            listCarteAvance.add(carte);
        }

        listCarteAvance.get(24).setNom("TAUPE GEANT");
        listCarteAvance.get(24).setImage("/GUI/image/aille/taupe geant 1.png");
        int[][] valeur0 = new int[1][4];
        valeur0[0][0] = 1;
        valeur0[0][1] = 1;
        valeur0[0][2] = 1;
        valeur0[0][3] = 1;
        listCarteAvance.get(24).setValeur(valeur0);

        listCarteAvance.get(25).setNom("TAUPE GEANT");
        listCarteAvance.get(25).setImage("/GUI/image/aille/taupe geant 2.png");
        int[][] valeur1 = new int[1][4];
        valeur1[0][0] = 0;
        valeur1[0][1] = 2;
        valeur1[0][2] = 2;
        valeur1[0][3] = 0;
        listCarteAvance.get(25).setValeur(valeur1);

        listCarteAvance.get(26).setNom("TAUPE GEANT");
        listCarteAvance.get(26).setImage("/GUI/image/aille/taupe geant 3.png");
        int[][] valeur2 = new int[1][4];
        valeur2[0][0] = 0;
        valeur2[0][1] = 1;
        valeur2[0][2] = 2;
        valeur2[0][3] = 1;
        listCarteAvance.get(26).setValeur(valeur2);

        listCarteAvance.get(27).setNom("CHIEN DE GARDE");
        listCarteAvance.get(27).setImage("/GUI/image/aille/chien de garde 1.png");
        int[][] valeur3 = new int[1][4];
        valeur3[0][0] = 2;
        valeur3[0][1] = 0;
        valeur3[0][2] = 2;
        valeur3[0][3] = 0;
        listCarteAvance.get(27).setValeur(valeur3);

        listCarteAvance.get(28).setNom("CHIEN DE GARDE");
        listCarteAvance.get(28).setImage("/GUI/image/aille/chien de garde 2.png");
        int[][] valeur4 = new int[1][4];
        valeur4[0][0] = 1;
        valeur4[0][1] = 2;
        valeur4[0][2] = 0;
        valeur4[0][3] = 1;
        listCarteAvance.get(28).setValeur(valeur4);

        listCarteAvance.get(29).setNom("CHIEN DE GARDE");
        listCarteAvance.get(29).setImage("/GUI/image/aille/chien de garde 3.png");
        int[][] valeur5 = new int[1][4];
        valeur5[0][0] = 0;
        valeur5[0][1] = 1;
        valeur5[0][2] = 3;
        valeur5[0][3] = 0;
        listCarteAvance.get(29).setValeur(valeur5);
    }

    // determiner le joueur jouer en premier
    public ArrayList<Joueur> construireTour(ArrayList<Joueur> joueur) {
        // Trouver le joueur le plus jeune 
        int min = joueur.get(0).getAge();
        int pos = 0;
        for (int i = 1; i < joueur.size(); i++) {
            int age_jeune;
            age_jeune = joueur.get(i).getAge();
            if (age_jeune < min) {
                min = joueur.get(i).getAge();
                pos = i;
            }
        }
        // Construire le tour de joueur 
        ArrayList<Joueur> joueur_final = new ArrayList();
        joueur_final.add(joueur.get(pos));
        for (int j = pos + 1; j < joueur.size(); j++) {
            joueur_final.add(joueur.get(j));

        }
        for (int j = 0; j < pos; j++) {
            joueur_final.add(joueur.get(j));
        }
        return joueur_final;
    }
   

    // on change le premier joueur apres finir chaque manche
    public ArrayList<Joueur> changerManche(ArrayList<Joueur> listJoueur) {
        ArrayList<Joueur> listJoueurFinal = new ArrayList();
        for (int i = 1; i < listJoueur.size(); i++) {
            listJoueurFinal.add(listJoueur.get(i));
        }
        listJoueurFinal.add(listJoueur.get(0));
        return listJoueurFinal;
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
