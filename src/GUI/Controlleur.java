
package GUI;

public class Controlleur {
    private Partie partie;
	private int nbrJoueur;
	private String nom;
	private int age;
	private String mode;
	private boolean readyToPlay = false;
        private String niveau;
        public void setParametrePartie(int nbrJoueur, String nom, int age, String mode,String niveau) {
		this.nbrJoueur = nbrJoueur;
		this.nom = nom;
		this.age = age;
		this.mode = mode;
		this.readyToPlay = true;
                this.niveau = niveau;
	}
        
        // commencer demo de ce jeu
        public static void main(String[] args) {
		Controlleur c = new Controlleur();
		c.init();
	}

        // appeler interface pour saisir les informations de joueur physique et apres les sauvegarder, appeler interface pour jouer 
        public void init() {
		this.partie = new Partie();
		if (!this.readyToPlay) {
			Main_Menu mm = new Main_Menu(this);
			mm.setVisible(true);
                        MainFenetre main = new MainFenetre(this.partie);
		this.partie.addObserver(main);
		this.partie.getNombreDeJoueur();
		}
		
		while (!(this.readyToPlay)) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (this.readyToPlay) {
			this.partie.start(this.nbrJoueur, this.nom, this.age, this.mode,this.niveau);
		}
	}
        
        // prendre partie
        public Partie getPartie() {
		return partie;
	}

        
        // etablir partie
	public void setPartie(Partie partie) {
		this.partie = partie;
	}
}
