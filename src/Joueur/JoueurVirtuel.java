package Joueur;

public class JoueurVirtuel extends Joueur {

    private Strategie strategie;

    // constructeur
    public JoueurVirtuel(String nomDeJoueur, int age, String niveau) {
        super(nomDeJoueur, age, niveau);
    }

    // etablir strategie
    @Override
    public void setStrategie(Strategie strategie) {
        this.strategie = strategie;
    }

    // prendre strategie
    @Override
    public Strategie getStrategie() {
        return this.strategie;
    }

}
