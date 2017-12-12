// cette class presente les attributs d'une carte
// methode set et get pour saisir les valeurs des attributs et prendre les valeurs des attributs
package Carte;

public class Carte {

    private int numero;
    private String nom;

    
    protected String image;

    private boolean estJoue = false;
    private boolean estChoisi = false;
    private int[][] valeur;

    // prendre numero de la carte
    public int getNumero() {
        return this.numero;
    }

    // prendre nom de la carte
    public String getNom() {
        return this.nom;
    }

    // etablir nom de la carte
    public void setNom(String nom) {
        this.nom = nom;
    }

    // prendre valeur de la carte
    public int[][] getValeur() {
        return valeur;
    }
    // etablir valeur de la carte
    public void setValeur(int[][] valeur) {
        this.valeur = valeur;
    }
    //cette methode verifie le joueur joue cette carte ou pas
    public boolean getJouer() {
        return this.estJoue;
    }

    // cette methode verifie que une carte est choisi ou pas
    public boolean getChoisir() {
        return this.estChoisi;
    }

    // si une carte est choisi, set valeur true
    public void ChoisirCarte() {
        this.estChoisi = true;
    }

    // si une carte est joue, set valeur estJoue egale true
    public void PoserCarte() {
        this.estJoue = true;
    }

    // set valeur de estJoue
    public void setEstJoue(boolean estJoue) {
        this.estJoue = estJoue;
    }

    // set valeur de estChoisi
    public void setEstChoisi(boolean estChoisi) {
        this.estChoisi = estChoisi;
    }

    // prendre l'url de image
    public String getImage() {
        return image;
    }

    // etablir l'url de image
    public void setImage(String image) {
        this.image = image;
    }
    
    // constructeur
    public Carte(int numero) {
        this.numero = numero;
    }
}
