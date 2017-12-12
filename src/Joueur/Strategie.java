package Joueur;

import Carte.CarteIngredient;
import Carte.Carte;
import java.util.ArrayList;

public interface Strategie {

    // joueur virtuel (partie Rapide) choisit carte
    public int sChoisir(ArrayList<CarteIngredient> listCarte);

    // joueur virtuel (regle avance) choisit carte
    public int sChoisirAvance(ArrayList<Carte> listCarte);

    // joueur virtuel detruit menhir
    public int sDetruireMenhir(ArrayList<Joueur> listJoueur, int pos);

    // joueur virtuel (partie rapide) choisit force
    public String sJouer(CarteIngredient carte, int saison, int numGraine);

    // joueur virtuel (regle avance) choisit force
    public String sJouerAvance(Carte carte, int saison, int numGraine);

    // joueur virtuel vole graines
    public int sVoler(ArrayList<Joueur> listJoueur, int pos, int saison);
}
