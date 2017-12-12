
package GUI;

import Game.Game;
import Joueur.Joueur;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;


public class Main_Menu extends JFrame implements ActionListener, ItemListener {

    private Game game;
    private ArrayList<Joueur> joueur_final;
    private int age;
    private int nombreDeJoueur;
    private String nom;
    private String niveau;
    private String mode;
    private Controlleur controlleur;
    private final Partie partie;

    

    // prendre l'age de joueur
    public int getAge() {
        return this.age;
    }

    // etablir l'age de joueur
    public void setAge(int age) {
        this.age = age;
    }

    // prendre nom de joueur
    public String getNom() {
        return this.nom;
    }

    //
    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNiveau() {
        return this.niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public String getMode() {
        return this.mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

 
    
    Integer[] nombreJoueur1 = {2, 3, 4, 5, 6};

    JTextField nomInput = new JTextField(20), ageInput = new JTextField(2);

    JRadioButton debutant = new JRadioButton("Debutant"), avance = new JRadioButton("Avance");
    JComboBox nombreJoueur = new JComboBox(nombreJoueur1);

    JCheckBox partieRapide = new JCheckBox("Partie Rapide"), regleAvance = new JCheckBox("Regles Avances");

    JButton commencerButton = new JButton("Commencer"), annulerButton = new JButton("Annuler");
    
    // construire l'interface pour saisir les informations 
    private void initialize() {
        JPanel panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout());
        setTitle("Entrer informations pour jouer");
        addItem(panel1, new JLabel("Nom:"), 0, 0, 1, 1, GridBagConstraints.EAST);
        addItem(panel1, new JLabel("Age:"), 0, 1, 1, 1, GridBagConstraints.EAST);

        addItem(panel1, nomInput, 1, 0, 2, 1, GridBagConstraints.WEST);
        addItem(panel1, ageInput, 1, 1, 1, 1, GridBagConstraints.WEST);

        Box sizeBox = Box.createVerticalBox();
        ButtonGroup sizeGroup = new ButtonGroup();
        sizeGroup.add(debutant);
        sizeGroup.add(avance);
        debutant.addItemListener(this);
        avance.addItemListener(this);
        sizeBox.add(debutant);
        sizeBox.add(avance);
        sizeBox.setBorder(BorderFactory.createTitledBorder("Niveau"));
        addItem(panel1, sizeBox, 0, 3, 1, 1, GridBagConstraints.NORTH);

        Box styleBox = Box.createVerticalBox();

        ButtonGroup styleGroup = new ButtonGroup();
        styleBox.add(nombreJoueur);
        nombreJoueur.addActionListener(this);
        styleBox.setBorder(BorderFactory.createTitledBorder("Nombre Joueurs"));
        addItem(panel1, styleBox, 1, 3, 1, 1, GridBagConstraints.NORTH);

        Box topBox = Box.createVerticalBox();
        ButtonGroup topGroup = new ButtonGroup();
        topGroup.add(partieRapide);
        topGroup.add(regleAvance);
        partieRapide.addItemListener(this);
        regleAvance.addItemListener(this);
        topBox.add(partieRapide);
        topBox.add(regleAvance);
        topBox.setBorder(BorderFactory.createTitledBorder("Mode"));
        addItem(panel1, topBox, 2, 3, 1, 1, GridBagConstraints.NORTH);

        Box buttonBox = Box.createHorizontalBox();
        buttonBox.add(commencerButton);
        commencerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
				infoJouer();
				setDispose();
			}
		});
        buttonBox.add(Box.createHorizontalStrut(20));
        buttonBox.add(annulerButton);
        commencerButton.addActionListener(this);
        annulerButton.addActionListener(this);
        addItem(panel1, buttonBox, 2, 4, 1, 1, GridBagConstraints.NORTH);

        this.add(panel1);
    }

    public Main_Menu(Controlleur controlleur) {

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.controlleur = controlleur; 
        this.partie = this.controlleur.getPartie();
        initialize();
        
        this.pack();
        this.setVisible(true);
    }
    private void setDispose() {
        this.dispose();
    }
    private void addItem(JPanel p, JComponent c, int x, int y, int width, int height, int align) {
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = x;
        gc.gridy = y;
        gc.gridwidth = width;
        gc.gridheight = height;
        gc.weightx = 100.0;
        gc.weighty = 100.0;
        gc.insets = new Insets(5, 5, 5, 5);
        gc.anchor = align;
        gc.fill = GridBagConstraints.NONE;
        p.add(c, gc);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        }
//    public void setGame(Game g) {
//        this.game = g;
//    }

    public void setListJoueur(ArrayList<Joueur> joueur_final) {
        this.joueur_final = joueur_final;
    }

//    public Game getGame() {
//        return this.game;
//    }
    public ArrayList<Joueur> getListJoueur() {
        return this.joueur_final;
    }

    private Integer choisirNombre() {
        Integer nombre = nombreJoueur.getSelectedIndex();
        return nombre;
    }
    public void infoJouer() {
            String mode = choisirMode();
            Integer nombreDeJoueurChoisir = nombreJoueur1[choisirNombre()];
            String niveau = choisirNiveau();
            int nombreDeJoueur = nombreDeJoueurChoisir;
            ArrayList<Joueur> joueur = new ArrayList();
            String nom = nomInput.getText();

            String ageString = ageInput.getText();
            Integer age = Integer.parseInt(ageString);
            controlleur.setParametrePartie(nombreDeJoueur, nom, age, mode, niveau);
    }
    private String choisirMode() {
        String mode = (partieRapide.isSelected() ? "Rapide" : "Avancee");
        return mode;
    }

    private String choisirNiveau() {
        String niveau = (debutant.isSelected() ? "Debutant" : "Avance");
        return niveau;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        choisirMode();
        choisirNiveau();
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