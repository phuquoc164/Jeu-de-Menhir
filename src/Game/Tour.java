package Game;

public class Tour {

    private String nom;

    public String getTour() {
        return this.nom;
    }

    // constructeur
    public Tour(int numTour) {
        if (numTour == 0) {
            this.nom = "Printemps";
        } else if (numTour == 1) {
            this.nom = "Ete";
        } else if (numTour == 2) {
            this.nom = "Automne";
        } else if (numTour == 3) {
            this.nom = "Hiver";
        }
    }
}
