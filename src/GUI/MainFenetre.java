// cette classe decrie ce qu'il passe dans une partie rapide et dans une manche de regle avancee
package GUI;

import Carte.CarteAllie;
import Carte.*;
import Carte.CarteIngredient;
import Game.Game;
import Game.Tour;
import Joueur.Avance;
import Joueur.Debutant;
import Joueur.Joueur;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;

public class MainFenetre implements Observer {

    private Game game;
    private ArrayList<CarteIngredient> listCarteIngredient = new ArrayList<CarteIngredient>();
    private ArrayList<Carte> listCarteAvance = new ArrayList<Carte>();
    private JFrame mainFrame;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JButton jButton1;
    private JButton jButton2;
    private JButton jButton3;
    private JButton jButton4;
    private JLabel jLabelAilee;
    private JButton jButton5;
    private JButton jButton6;
    private JButton jButton7;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;
    private JScrollPane jScrollPane3;
    private JTextArea jTextArea1;
    private JTable jTable1;
    private JTable jTable2;
    private final String column[] = {"NAME", "GRAINE", "MENHIR"};
    private final String column1[] = {"NAME", "GRAINE", "MENHIR", "MENHIR (COMPTAGE)"};
    private boolean choisirCarte = false;
    private boolean choisirForce = false;
    private String force;
    private int numCarteJoue;
    int m = 0;
    private boolean check = false;

    
    // constructeur
    public MainFenetre(Partie p) {
        mainFrame = new JFrame("Menhir");
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setLayout(new AbsoluteLayout());
        mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
        mainFrame.pack();

    }

    // initialiser une partie : rapide ou avance
    public void initialize(Partie p) {
        // on construit l'interface
        jLabel1 = new JLabel("Partie : " + p.getModeJouer());
        jLabel2 = new JLabel("Niveau : " + p.getNiveau());
        jLabel3 = new JLabel();
        jLabel4 = new JLabel();

        jLabel1.setFont(new Font("Times New Roman", 1, 16));
        mainFrame.add(jLabel1, new AbsoluteConstraints(20, 30, 130, 30));
        jLabel2.setFont(new Font("Times New Roman", 1, 16));
        mainFrame.add(jLabel2, new AbsoluteConstraints(181, 30, 130, 30));

        jLabel3.setFont(new Font("Times New Roman", 1, 16));
        jLabel3.setText("Tour : Printemps");
        mainFrame.add(jLabel3, new AbsoluteConstraints(400, 30, 148, 30));
        jLabel4.setFont(new Font("Times New Roman", 1, 16));
        jLabel4.setText(p.getJoueur_final().get(0).getNomDeJoueur());
        mainFrame.add(jLabel4, new AbsoluteConstraints(600, 30, 200, 30));

        jLabel5 = new JLabel();
        jLabel6 = new JLabel();
        mainFrame.add(jLabel5, new AbsoluteConstraints(21, 172, 120, 120));
        mainFrame.add(jLabel6, new AbsoluteConstraints(480, 430, 120, 120));

        jButton5 = new JButton("Geant");
        jButton5.setFont(new java.awt.Font("Times New Roman", 1, 16));
        jButton6 = new JButton("Engrais");
        jButton6.setFont(new java.awt.Font("Times New Roman", 1, 16));
        jButton7 = new JButton("Farfadet");
        jButton7.setFont(new java.awt.Font("Times New Roman", 1, 16));
        mainFrame.add(jButton5, new AbsoluteConstraints(159, 563, 110, -1));
        mainFrame.add(jButton6, new AbsoluteConstraints(159, 603, 110, -1));
        mainFrame.add(jButton7, new AbsoluteConstraints(159, 643, 110, -1));

        jTextArea1 = new JTextArea(20, 5);
        jScrollPane1 = new JScrollPane(jTextArea1);
        mainFrame.add(jScrollPane1, new AbsoluteConstraints(159, 130, 376, 291));
        mainFrame.pack();
        mainFrame.setVisible(true);
    }

    // cette methode nous aide à chercher image de la carte
    private static ImageIcon createImageIcon(String path, String description) {
        java.net.URL imgURL = MainFenetre.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        final Partie p = (Partie) o;
        // partie rapide
        if (arg == "rapide") {
            initialize(p);
            String[][] data;
            int numeroDeJoueurPhysique = 0;
            for (int i = 0; i < p.getJoueur_final().size(); i++) {
                if (p.getJoueur_final().get(i).getNiveau().equalsIgnoreCase("Physique")) {
                    numeroDeJoueurPhysique = i;
                }
            }
            //initialiser le tableau NAME, GRAINES ET MENHIRS
            data = new String[p.getJoueur_final().size()][3];
            for (int i = 0; i < p.getJoueur_final().size(); i++) {
                data[i][0] = p.getJoueur_final().get(i).getNomDeJoueur() + "-" + p.getJoueur_final().get(i).getAge() + " ans";
                data[i][1] = p.getJoueur_final().get(i).getGraine().getTotal() + "";
                data[i][2] = p.getJoueur_final().get(i).getChamp().getMenhir() + "";
            }
            DefaultTableModel model = new DefaultTableModel(data, column);
            jTable1 = new JTable();
            jTable1.setModel(model);
            jScrollPane2 = new JScrollPane(jTable1);
            mainFrame.add(jScrollPane2, new AbsoluteConstraints(547, 130, 376, 291));
            mainFrame.pack();
            mainFrame.setVisible(true);
            game = p.getGame();

            // SET IMAGES POUR LES CARTES
            listCarteIngredient = p.getJoueur_final().get(numeroDeJoueurPhysique).getListCarte();
            ImageIcon icon = createImageIcon(p.getJoueur_final().get(numeroDeJoueurPhysique).getListCarte().get(0).getImage(), "Carte1");
            // ImageIcon icon = new ImageIcon("/graphique_MVC/carte lo02/ingredient/chant de sirene 1.png");
            jButton1 = new JButton("", icon);
            ImageIcon icon1 = createImageIcon(p.getJoueur_final().get(numeroDeJoueurPhysique).getListCarte().get(1).getImage(), "Carte2");
            jButton2 = new JButton("", icon1);
            ImageIcon icon2 = createImageIcon(p.getJoueur_final().get(numeroDeJoueurPhysique).getListCarte().get(2).getImage(), "Carte3");
            jButton3 = new JButton("", icon2);
            ImageIcon icon3 = createImageIcon(p.getJoueur_final().get(numeroDeJoueurPhysique).getListCarte().get(3).getImage(), "Carte4");
            jButton4 = new JButton("", icon3);
            mainFrame.add(jButton1, new AbsoluteConstraints(289, 553, 120, 120));
            mainFrame.add(jButton2, new AbsoluteConstraints(415, 553, 120, 120));
            mainFrame.add(jButton3, new AbsoluteConstraints(541, 553, 120, 120));
            mainFrame.add(jButton4, new AbsoluteConstraints(667, 553, 120, 120));
            mainFrame.pack();
            mainFrame.setVisible(true);

            for (int i = 0; i < 4; i++) {
                Tour tour = new Tour(i);
                jLabel3.setText("Tour : " + tour.getTour());
                JOptionPane.showMessageDialog(mainFrame, "Tour " + tour.getTour() + " va commencer");
                jTextArea1.setText(p.getJoueur_final().get(0).getNomDeJoueur() + " commence tour " + tour.getTour() + "\n");
                for (int j = 0; j < p.getJoueur_final().size(); j++) {
                    jLabel4.setText(p.getJoueur_final().get(j).getNomDeJoueur());
                    if (p.getJoueur_final().get(j).getNiveau().equalsIgnoreCase("Physique")) {
                        jTextArea1.setText(jTextArea1.getText() + "Choisissez une carte que vous voulez" + "\n");
                        // CHOISIR CARTE 
                        while (choisirCarte == false) {

                            jButton1.addActionListener(new ActionListener() {

                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    if ((listCarteIngredient.get(0).getJouer() == false) && (choisirCarte == false)) {
                                        jButton1.setEnabled(false);
                                        jButton1.setVisible(false);
                                        numCarteJoue = 0;
                                        listCarteIngredient.get(0).PoserCarte();
                                        choisirCarte = true;
                                        jLabel6.setIcon(icon);
                                        jLabel6.setVisible(true);
                                    }
                                }
                            });

                            jButton2.addActionListener(new ActionListener() {

                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    if ((listCarteIngredient.get(1).getJouer() == false) && (choisirCarte == false)) {
                                        jButton2.setEnabled(false);
                                        jButton2.setVisible(false);
                                        numCarteJoue = 1;
                                        listCarteIngredient.get(1).PoserCarte();
                                        choisirCarte = true;
                                        jLabel6.setIcon(icon1);
                                        jLabel6.setVisible(true);
                                    }
                                }

                            });

                            jButton3.addActionListener(new ActionListener() {

                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    if ((listCarteIngredient.get(2).getJouer() == false) && (choisirCarte == false)) {
                                        jButton3.setVisible(false);
                                        jButton3.setEnabled(false);
                                        numCarteJoue = 2;
                                        listCarteIngredient.get(2).PoserCarte();
                                        choisirCarte = true;
                                        jLabel6.setIcon(icon2);
                                        jLabel6.setVisible(true);
                                    }
                                }

                            });

                            jButton4.addActionListener(new ActionListener() {

                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    if ((listCarteIngredient.get(3).getJouer() == false) && (choisirCarte == false)) {
                                        jButton4.setVisible(false);
                                        jButton4.setEnabled(false);
                                        numCarteJoue = 3;
                                        listCarteIngredient.get(3).PoserCarte();
                                        choisirCarte = true;
                                        jLabel6.setIcon(icon3);
                                        jLabel6.setVisible(true);
                                    }
                                }
                            });
                        }
                        jTextArea1.setText(jTextArea1.getText() + "Choisissez une force que vous voulez" + "\n");

                        // CHOISIR FORCE
                        while (choisirForce == false) {
                            jButton5.addActionListener(new ActionListener() {

                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    if (choisirForce == false) {
                                        jButton5.setEnabled(false);
                                        force = "Geant";
                                        choisirForce = true;
                                    }
                                }

                            });

                            jButton6.addActionListener(new ActionListener() {

                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    if (choisirForce == false) {
                                        jButton6.setEnabled(false);
                                        force = "Engrais";
                                        choisirForce = true;
                                    }
                                }

                            });

                            jButton7.addActionListener(new ActionListener() {

                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    if (choisirForce == false) {
                                        jButton7.setEnabled(false);
                                        force = "Farfadet";
                                        choisirForce = true;
                                    }
                                }

                            });

                        }
                        // UTILISER LA CARTE ET LA FORCE POUR CALCULER LES GRAINES ET LES MENHIRS
                        int[][] valeur = p.getJoueur_final().get(j).getListCarte().get(numCarteJoue).getValeur();
                        // FORCE GEANT 
                        if (force.equalsIgnoreCase("Geant")) {
                            p.getJoueur_final().get(j).getListCarte().get(numCarteJoue).effectGeant(p.getJoueur_final().get(j), valeur[0][i]);
                            model.setValueAt(p.getJoueur_final().get(j).getGraine().getTotal(), j, 1);
                            jTextArea1.setText(jTextArea1.getText() + " Vous avez plus " + valeur[0][i] + " graines \n");
                        }// FORCE ENGRAIS
                        else if (force.equalsIgnoreCase("Engrais")) {
                            p.getJoueur_final().get(j).getListCarte().get(numCarteJoue).effectEngrais(p.getJoueur_final().get(j), valeur[1][i]);
                            model.setValueAt(p.getJoueur_final().get(j).getGraine().getTotal(), j, 1);
                            model.setValueAt(p.getJoueur_final().get(j).getChamp().getMenhir(), j, 2);

                            jTextArea1.setText(jTextArea1.getText() + " Vous avez " + valeur[1][i] + " menhirs \n");
                        }//FORCE FARFADET 
                        else if (force.equalsIgnoreCase("Farfadet")) {
                            int adver = 0;
                            String name = "";
                            name = JOptionPane.showInputDialog("Vous voulez prendre les graines de quel joueur?");
                            for (int k = 0; k < p.getJoueur_final().size(); k++) {
                                if (p.getJoueur_final().get(k).getNomDeJoueur().equalsIgnoreCase(name)) {
                                    adver = k;
                                }
                            }
                            p.getJoueur_final().get(j).getListCarte().get(numCarteJoue).effectFarfadet(p.getJoueur_final().get(j), p.getJoueur_final().get(adver), valeur[2][i]);
                            model.setValueAt(p.getJoueur_final().get(j).getGraine().getTotal(), j, 1);
                            model.setValueAt(p.getJoueur_final().get(adver).getGraine().getTotal(), adver, 1);
                            jTextArea1.setText(jTextArea1.getText() + "Vous prennez " + valeur[2][i] + " graines de " + p.getJoueur_final().get(adver).getNomDeJoueur() + "\n");
                        }
                        jLabel6.setVisible(false);
                        jButton5.setEnabled(true);
                        jButton6.setEnabled(true);
                        jButton7.setEnabled(true);

                    }
                    // JOUEUR VIRTUEL (NIVEAU DEBUTANT) JOUE
                    if (p.getJoueur_final().get(j).getNiveau().equalsIgnoreCase("Debutant")) {
                        p.getJoueur_final().get(j).setStrategie(new Debutant());
                        int numChoisi = p.getJoueur_final().get(j).choisiVirtuel();
                        jTextArea1.setText(jTextArea1.getText() + p.getJoueur_final().get(j).getNomDeJoueur() + " choisi carte " + p.getJoueur_final().get(j).getListCarte().get(numChoisi).getNom() + "\n");
                        // SET IMAGE POUR LA CARTE DE JOUEUR VIRTUEL 
                        ImageIcon icon5 = createImageIcon(p.getJoueur_final().get(j).getListCarte().get(numChoisi).getImage(), "carteJoueurVirtuel");
                        jLabel5.setIcon(icon5);
                        jLabel5.setVisible(true);
                        force = p.getJoueur_final().get(j).joueVirtuel(p.getJoueur_final().get(j).getListCarte().get(numChoisi), i, p.getJoueur_final().get(j).getGraine().getTotal());
                        jTextArea1.setText(jTextArea1.getText() + p.getJoueur_final().get(j).getNomDeJoueur() + " a choisi " + force + "\n");
                        int[][] valeur = p.getJoueur_final().get(j).getListCarte().get(numChoisi).getValeur();
                        // FORCE GEANT
                        if (force.equalsIgnoreCase("Geant")) {
                            p.getJoueur_final().get(j).getListCarte().get(numChoisi).effectGeant(p.getJoueur_final().get(j), valeur[0][i]);
                            JOptionPane.showMessageDialog(mainFrame, p.getJoueur_final().get(j).getNomDeJoueur() + " choisit la carte " + p.getJoueur_final().get(j).getListCarte().get(numChoisi).getNom()
                                    + " avec valeur " + valeur[0][i] + " de Geant");
                            model.setValueAt(p.getJoueur_final().get(j).getGraine().getTotal(), j, 1);
                        }// FORCE ENGRAIS
                        else if (force.equalsIgnoreCase("Engrais")) {
                            p.getJoueur_final().get(j).getListCarte().get(numChoisi).effectEngrais(p.getJoueur_final().get(j), valeur[1][i]);
                            JOptionPane.showMessageDialog(mainFrame, p.getJoueur_final().get(j).getNomDeJoueur() + " choisit la carte " + p.getJoueur_final().get(j).getListCarte().get(numChoisi).getNom()
                                    + " avec valeur " + valeur[1][i] + " de Engrais");
                            model.setValueAt(p.getJoueur_final().get(j).getGraine().getTotal(), j, 1);
                            model.setValueAt(p.getJoueur_final().get(j).getChamp().getMenhir(), j, 2);
                        } // FORCE FARFADET
                        else if (force.equalsIgnoreCase("Farfadet")) {
                            int adver = p.getJoueur_final().get(j).volerVirtuel(p.getJoueur_final(), j, i);
                            jTextArea1.setText(jTextArea1.getText() + p.getJoueur_final().get(j).getNomDeJoueur() + " choisit voler les graines de " + p.getJoueur_final().get(adver).getNomDeJoueur() + "\n");
                            p.getJoueur_final().get(j).getListCarte().get(numChoisi).effectFarfadet(p.getJoueur_final().get(j), p.getJoueur_final().get(adver), valeur[2][i]);
                            JOptionPane.showMessageDialog(mainFrame, p.getJoueur_final().get(j).getNomDeJoueur() + " vole " + valeur[2][i] + " graines de " + p.getJoueur_final().get(adver).getNomDeJoueur());
                            model.setValueAt(p.getJoueur_final().get(j).getGraine().getTotal(), j, 1);
                            model.setValueAt(p.getJoueur_final().get(adver).getGraine().getTotal(), adver, 1);
                        }
                        jLabel5.setVisible(false);
                    }
                    // JOUEUR VIRTUEL (NIVEAU AVANCE) JOUE
                    if (p.getJoueur_final().get(j).getNiveau().equalsIgnoreCase("Avance")) {
                        p.getJoueur_final().get(j).setStrategie(new Avance());
                        int numChoisi = p.getJoueur_final().get(j).choisiVirtuel();
                        jTextArea1.setText(jTextArea1.getText() + p.getJoueur_final().get(j).getNomDeJoueur() + " choisi carte " + p.getJoueur_final().get(j).getListCarte().get(numChoisi).getNom() + "\n");
                        // SET IMAGE POUR LA CARTE DE JOUEUR VIRTUEL 
                        ImageIcon icon5 = createImageIcon(p.getJoueur_final().get(j).getListCarte().get(numChoisi).getImage(), "carteJoueurVirtuel");
                        jLabel5.setIcon(icon5);
                        jLabel5.setVisible(true);
                        force = p.getJoueur_final().get(j).joueVirtuel(p.getJoueur_final().get(j).getListCarte().get(numChoisi), i, p.getJoueur_final().get(j).getGraine().getTotal());
                        jTextArea1.setText(jTextArea1.getText() + p.getJoueur_final().get(j).getNomDeJoueur() + " a choisi " + force + "\n");
                        int[][] valeur = p.getJoueur_final().get(j).getListCarte().get(numChoisi).getValeur();
                        // FORCE GEANT
                        if (force.equalsIgnoreCase("Geant")) {
                            p.getJoueur_final().get(j).getListCarte().get(numChoisi).effectGeant(p.getJoueur_final().get(j), valeur[0][i]);
                            JOptionPane.showMessageDialog(mainFrame, p.getJoueur_final().get(j).getNomDeJoueur() + " choisit la carte " + p.getJoueur_final().get(j).getListCarte().get(numChoisi).getNom()
                                    + " avec valeur " + valeur[0][i] + " de Geant");
                            model.setValueAt(p.getJoueur_final().get(j).getGraine().getTotal(), j, 1);
                        } // FORCE ENGRAIS
                        else if (force.equalsIgnoreCase("Engrais")) {
                            p.getJoueur_final().get(j).getListCarte().get(numChoisi).effectEngrais(p.getJoueur_final().get(j), valeur[1][i]);
                            JOptionPane.showMessageDialog(mainFrame, p.getJoueur_final().get(j).getNomDeJoueur() + " choisit la carte " + p.getJoueur_final().get(j).getListCarte().get(numChoisi).getNom()
                                    + " avec valeur " + valeur[1][i] + " de Engrais");
                            model.setValueAt(p.getJoueur_final().get(j).getGraine().getTotal(), j, 1);
                            model.setValueAt(p.getJoueur_final().get(j).getChamp().getMenhir(), j, 2);
                        } // FORCE FARFADET
                        else if (force.equalsIgnoreCase("Farfadet")) {
                            int adver = p.getJoueur_final().get(j).volerVirtuel(p.getJoueur_final(), j, i);
                            jTextArea1.setText(jTextArea1.getText() + p.getJoueur_final().get(j).getNomDeJoueur() + " choisit voler les graines de " + p.getJoueur_final().get(adver).getNomDeJoueur() + "\n");
                            p.getJoueur_final().get(j).getListCarte().get(numChoisi).effectFarfadet(p.getJoueur_final().get(j), p.getJoueur_final().get(adver), valeur[2][i]);
                            JOptionPane.showMessageDialog(mainFrame, p.getJoueur_final().get(j).getNomDeJoueur() + " vole " + valeur[2][i] + " graines de " + p.getJoueur_final().get(adver).getNomDeJoueur());
                            model.setValueAt(p.getJoueur_final().get(j).getGraine().getTotal(), j, 1);
                            model.setValueAt(p.getJoueur_final().get(adver).getGraine().getTotal(), adver, 1);
                        }
                        jLabel5.setVisible(false);
                    }
                }
                JOptionPane.showMessageDialog(mainFrame, "Tour " + tour.getTour() + " a fini");
                jTextArea1.setText("");
                choisirCarte = false;
                choisirForce = false;
            }
        } else if (arg == "finiRapide") {
            // Trouver le joueur qui a plus menhirs 
            int max = game.trouveMaxMenhir(p.getJoueur_final());
            // Trier la list de joueurs par l'ordre descendant de menhirs
            ArrayList<Joueur> joueur_final_fini = game.triParMenhirs(p.getJoueur_final());
            // Trouver tous les joueurs qui ont le meme menhirs avec le max 
            ArrayList<Joueur> joueur_meme_menhirs = game.trouveMemeMenhirs(max, joueur_final_fini);
            // Trier la liste de meme menhirs par graines
            joueur_meme_menhirs = game.triParGraines(joueur_meme_menhirs);
            // Le max graines maintenant est les graines de joueur(0) dans la list
            int maxGraines = joueur_meme_menhirs.get(0).getGraine().getTotal();
            // Trouver tous les joueurs ont le meme menhirs et le meme graines 
            ArrayList<Joueur> joueur_meme_menhirs_meme_graines = game.trouveMemeGraines(maxGraines, joueur_meme_menhirs);
            // Imprimer le gagnant  
            // verifier la taille de la liste joueur meme menhirs et meme graines 
            if (joueur_meme_menhirs_meme_graines.size() > 1) {
                String list = joueur_meme_menhirs_meme_graines.get(0).getNomDeJoueur();
                for (int i = 1; i < joueur_meme_menhirs_meme_graines.size() - 1; i++) {
                    list = list + ", " + joueur_meme_menhirs_meme_graines.get(i).getNomDeJoueur();
                }
                list = list + " et " + joueur_meme_menhirs_meme_graines.get(joueur_meme_menhirs_meme_graines.size() - 1).getNomDeJoueur();
                JOptionPane.showMessageDialog(mainFrame, "la Partie est null entre " + list);
            }
            if (joueur_meme_menhirs_meme_graines.size() == 1) {
                JOptionPane.showMessageDialog(mainFrame, "Le gagnant est " + joueur_meme_menhirs_meme_graines.get(0).getNomDeJoueur() + " avec "
                        + joueur_meme_menhirs_meme_graines.get(0).getChamp().getMenhir() + " Menhirs et " + joueur_meme_menhirs_meme_graines.get(0).getGraine().getTotal() + " graines");
            }
            // verifier que joueur physique veut jouer l'autre game
            int output = JOptionPane.showConfirmDialog(mainFrame, "Vous voulez jouer l'autre game?", "", JOptionPane.YES_NO_OPTION);
            if (output == JOptionPane.YES_OPTION) {
                mainFrame.dispose();
                Controlleur c = new Controlleur();
                c.init();
            } else if (output == JOptionPane.NO_OPTION) {
                System.exit(0);
            }

//            mainFrame.setVisible(true);

            // regle avance
        } else if (arg == "tirerAllie") {
            Object[] options = {"Carte alliees", "2 graines"};
            int n = JOptionPane.showOptionDialog(mainFrame, "Voulez-vous une carte Alliees ou 2 Graines", "",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            p.setChoix(n + 1);
            p.setViewChanged(true);
        } else if (arg == "avancee") {
            initialize(p);
            String[][] data1;
            jLabel7 = new JLabel();
            m++;
            jLabel7.setText("Manche : " + m);
            jLabel7.setFont(new Font("Times New Roman", 1, 16));
            mainFrame.add(jLabel7, new AbsoluteConstraints(400, 30, 148, 30));
            jLabel3.setText("Tour : Printemps");
            mainFrame.add(jLabel3, new AbsoluteConstraints(600, 30, 148, 30));
            jLabel4.setText(p.getJoueur_final().get(0).getNomDeJoueur());
            mainFrame.add(jLabel4, new AbsoluteConstraints(800, 30, 200, 30));

            int numeroDeJoueurPhysique = 0;
            for (int i = 0; i < p.getJoueur_final().size(); i++) {
                if (p.getJoueur_final().get(i).getNiveau().equalsIgnoreCase("Physique")) {
                    numeroDeJoueurPhysique = i;
                }
            }
            // set informations pour le tableau
            data1 = new String[p.getJoueur_final().size()][4];
            for (int i = 0; i < p.getJoueur_final().size(); i++) {
                data1[i][0] = p.getJoueur_final().get(i).getNomDeJoueur() + "-" + p.getJoueur_final().get(i).getAge() + " ans";
                data1[i][1] = p.getJoueur_final().get(i).getGraine().getTotal() + "";
                data1[i][2] = p.getJoueur_final().get(i).getChamp().getMenhir() + "";
                data1[i][3] = p.getJoueur_final().get(i).getCarteComptage().getNombreMenhirTotal() + "";
            }
            DefaultTableModel model = new DefaultTableModel(data1, column1);
            jTable2 = new JTable();
            jTable2.setModel(model);
            jScrollPane3 = new JScrollPane(jTable2);
            mainFrame.add(jScrollPane3, new AbsoluteConstraints(547, 130, 500, 291));

            // set images pour les cartes
            ImageIcon icon4 = new ImageIcon();
            game = p.getGame();
            listCarteAvance = p.getJoueur_final().get(numeroDeJoueurPhysique).getListCarteAvance();
            ImageIcon icon = createImageIcon(p.getJoueur_final().get(numeroDeJoueurPhysique).getListCarteAvance().get(0).getImage(), "Carte1");
            jButton1 = new JButton("", icon);
            ImageIcon icon1 = createImageIcon(p.getJoueur_final().get(numeroDeJoueurPhysique).getListCarteAvance().get(1).getImage(), "Carte2");
            jButton2 = new JButton("", icon1);
            ImageIcon icon2 = createImageIcon(p.getJoueur_final().get(numeroDeJoueurPhysique).getListCarteAvance().get(2).getImage(), "Carte3");
            jButton3 = new JButton("", icon2);
            ImageIcon icon3 = createImageIcon(p.getJoueur_final().get(numeroDeJoueurPhysique).getListCarteAvance().get(3).getImage(), "Carte4");
            jButton4 = new JButton("", icon3);
            mainFrame.add(jButton1, new AbsoluteConstraints(289, 553, 120, 120));
            mainFrame.add(jButton2, new AbsoluteConstraints(415, 553, 120, 120));
            mainFrame.add(jButton3, new AbsoluteConstraints(541, 553, 120, 120));
            mainFrame.add(jButton4, new AbsoluteConstraints(667, 553, 120, 120));
            if (listCarteAvance.size() == 5) {
                icon4 = createImageIcon(p.getJoueur_final().get(numeroDeJoueurPhysique).getListCarteAvance().get(4).getImage(), "CarteAille");
                jLabelAilee = new JLabel(icon4);
                mainFrame.add(jLabelAilee, new AbsoluteConstraints(795, 553, 120, 120));
                jLabelAilee.setVisible(true);
            }
            mainFrame.pack();
            mainFrame.setVisible(true);

            for (int j = 0; j < 4; j++) {
                Tour tour = new Tour(j);
                jLabel3.setText("Tour : " + tour.getTour());
                JOptionPane.showMessageDialog(mainFrame, "Tour " + tour.getTour() + " va commencer");
                jTextArea1.setText(p.getJoueur_final().get(0).getNomDeJoueur() + " commence tour " + tour.getTour() + "\n");
                for (int k = 0; k < p.getJoueur_final().size(); k++) {
                    jLabel4.setText(p.getJoueur_final().get(k).getNomDeJoueur());

                    if (p.getJoueur_final().get(k).getNiveau().equalsIgnoreCase("Physique")) {
                        jTextArea1.setText(jTextArea1.getText() + "Choisissez une carte que vous voulez" + "\n");
                        // joueur physique choisit la carte Ingredient et sa force
                        while (choisirCarte == false) {

                            jButton1.addActionListener(new ActionListener() {

                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    if ((listCarteAvance.get(0).getJouer() == false) && (choisirCarte == false)) {
                                        jButton1.setEnabled(false);
                                        jButton1.setVisible(false);
                                        numCarteJoue = 0;
                                        listCarteAvance.get(0).PoserCarte();
                                        choisirCarte = true;
                                        jLabel6.setIcon(icon);
                                        jLabel6.setVisible(true);
                                    }
                                }
                            });

                            jButton2.addActionListener(new ActionListener() {

                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    if ((listCarteAvance.get(1).getJouer() == false) && (choisirCarte == false)) {
                                        jButton2.setEnabled(false);
                                        jButton2.setVisible(false);
                                        numCarteJoue = 1;
                                        listCarteAvance.get(1).PoserCarte();
                                        choisirCarte = true;
                                        jLabel6.setIcon(icon1);
                                        jLabel6.setVisible(true);
                                    }
                                }

                            });

                            jButton3.addActionListener(new ActionListener() {

                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    if ((listCarteAvance.get(2).getJouer() == false) && (choisirCarte == false)) {
                                        jButton3.setVisible(false);
                                        jButton3.setEnabled(false);
                                        numCarteJoue = 2;
                                        listCarteAvance.get(2).PoserCarte();
                                        choisirCarte = true;
                                        jLabel6.setIcon(icon2);
                                        jLabel6.setVisible(true);
                                    }
                                }

                            });

                            jButton4.addActionListener(new ActionListener() {

                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    if ((listCarteAvance.get(3).getJouer() == false) && (choisirCarte == false)) {
                                        jButton4.setVisible(false);
                                        jButton4.setEnabled(false);
                                        numCarteJoue = 3;
                                        listCarteAvance.get(3).PoserCarte();
                                        choisirCarte = true;
                                        jLabel6.setIcon(icon3);
                                        jLabel6.setVisible(true);
                                    }
                                }
                            });
                        }
                        jTextArea1.setText(jTextArea1.getText() + "Choisissez une force que vous voulez" + "\n");
                        while (choisirForce == false) {
                            jButton5.addActionListener(new ActionListener() {

                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    if (choisirForce == false) {
                                        jButton5.setEnabled(false);
                                        force = "Geant";
                                        choisirForce = true;
                                    }
                                }

                            });

                            jButton6.addActionListener(new ActionListener() {

                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    if (choisirForce == false) {
                                        jButton6.setEnabled(false);
                                        force = "Engrais";
                                        choisirForce = true;
                                    }
                                }

                            });

                            jButton7.addActionListener(new ActionListener() {

                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    if (choisirForce == false) {
                                        jButton7.setEnabled(false);
                                        force = "Farfadet";
                                        choisirForce = true;
                                    }
                                }

                            });

                        }
                        int[][] valeur = p.getJoueur_final().get(k).getListCarteAvance().get(numCarteJoue).getValeur();
                        if (force.equalsIgnoreCase("Geant")) {
                            ((CarteIngredient) p.getJoueur_final().get(k).getListCarteAvance().get(numCarteJoue)).effectGeant(p.getJoueur_final().get(k), valeur[0][j]);
                            model.setValueAt(p.getJoueur_final().get(k).getGraine().getTotal(), k, 1);
                            jTextArea1.setText(jTextArea1.getText() + " Vous avez plus " + valeur[0][j] + " graines \n");
                        } else if (force.equalsIgnoreCase("Engrais")) {
                            ((CarteIngredient) p.getJoueur_final().get(k).getListCarteAvance().get(numCarteJoue)).effectEngrais(p.getJoueur_final().get(k), valeur[1][j]);
                            model.setValueAt(p.getJoueur_final().get(k).getGraine().getTotal(), k, 1);
                            model.setValueAt(p.getJoueur_final().get(k).getChamp().getMenhir(), k, 2);
                            jTextArea1.setText(jTextArea1.getText() + " Vous avez " + valeur[1][j] + " menhirs \n");
                        } else if (force.equalsIgnoreCase("Farfadet")) {
                            int adver = 0;
                            String name = "";
                            name = JOptionPane.showInputDialog("Vous voulez prendre les graines de quel joueur?");
                            for (int h = 0; h < p.getJoueur_final().size(); h++) {
                                if (p.getJoueur_final().get(h).getNomDeJoueur().equalsIgnoreCase(name)) {
                                    adver = h;
                                }
                            }
                            int pos1 = p.getJoueur_final().get(adver).getListCarteAvance().size() - 1;
                            if ((p.getJoueur_final().get(adver).getAvoirCarteAllie()) && (p.getJoueur_final().get(adver).getListCarteAvance().get(pos1).getNom().equals("CHIEN DE GARDE")) && (p.getJoueur_final().get(adver).getListCarteAvance().get(pos1).getJouer() == false)) {
                                int[][] valeur1 = p.getJoueur_final().get(adver).getListCarteAvance().get(pos1).getValeur();
                                ((CarteAllie) p.getJoueur_final().get(adver).getListCarteAvance().get(pos1)).ChienDeGarde(p.getJoueur_final().get(k), valeur[2][j], p.getJoueur_final().get(adver), valeur1[0][j]);
                                JOptionPane.showMessageDialog(mainFrame, p.getJoueur_final().get(adver).getNomDeJoueur() + " utilise la carte CHIEN DE GARDE avec la valeur " + valeur1[0][j]);
                                jTextArea1.setText(jTextArea1.getText() + "Donc vous avez " + p.getJoueur_final().get(k).getGraine().getTotal() + " graines" + "\n"
                                        + p.getJoueur_final().get(adver).getNomDeJoueur() + " a " + p.getJoueur_final().get(adver).getGraine().getTotal() + " graines" + "\n");
                                p.getJoueur_final().get(adver).getListCarteAvance().get(pos1).PoserCarte();
                            } else {
                                // sinon, joueur physique peut prendre les graines normalement
                                ((CarteIngredient) p.getJoueur_final().get(k).getListCarteAvance().get(numCarteJoue)).effectFarfadet(p.getJoueur_final().get(k), p.getJoueur_final().get(adver), valeur[2][j]);
                                jTextArea1.setText(jTextArea1.getText() + "Donc vous avez " + p.getJoueur_final().get(k).getGraine().getTotal() + " graines" + "\n");
                                jTextArea1.setText(jTextArea1.getText() + p.getJoueur_final().get(adver).getNomDeJoueur() + " a " + p.getJoueur_final().get(adver).getGraine().getTotal() + " graines" + "\n");
                            }
                            model.setValueAt(p.getJoueur_final().get(k).getGraine().getTotal(), k, 1);
                            model.setValueAt(p.getJoueur_final().get(adver).getGraine().getTotal(), adver, 1);
                        }
                        jLabel6.setVisible(false);
                        jButton5.setEnabled(true);
                        jButton6.setEnabled(true);
                        jButton7.setEnabled(true);
                        // verifier joueur physique avoir carte Allie qui n'utilise pas
                        if (p.getJoueur_final().get(k).getAvoirCarteAllie()) {
                            if (p.getJoueur_final().get(k).getListCarteAvance().get(4).getJouer() == false) {
                                int output = JOptionPane.showConfirmDialog(mainFrame, "Vous voulez utiliser carte aille?", "", JOptionPane.YES_NO_OPTION);

                                if (output == JOptionPane.YES_OPTION) {
                                    int[][] valeur1 = p.getJoueur_final().get(k).getListCarteAvance().get(4).getValeur();
                                    if (p.getJoueur_final().get(k).getListCarteAvance().get(4).getNom().equals("TAUPE GEANT")) {
                                        int adver = 0;
                                        String name = "";
                                        name = JOptionPane.showInputDialog("Vous voulez détruire les menhirs de quel joueur?");
                                        for (int h = 0; h < p.getJoueur_final().size(); h++) {
                                            if (p.getJoueur_final().get(h).getNomDeJoueur().equalsIgnoreCase(name)) {
                                                adver = h;
                                            }
                                            ((CarteAllie) p.getJoueur_final().get(k).getListCarteAvance().get(4)).TaupeGeant(p.getJoueur_final().get(k), valeur1[0][j], p.getJoueur_final().get(adver));
                                            p.getJoueur_final().get(k).getListCarteAvance().get(4).PoserCarte();
                                            jTextArea1.setText(jTextArea1.getText() + p.getJoueur_final().get(adver).getNomDeJoueur() + " a " + p.getJoueur_final().get(k).getChamp().getMenhir() + " Menhirs" + "\n");
                                            model.setValueAt(p.getJoueur_final().get(adver).getChamp().getMenhir(), adver, 2);
                                            jLabelAilee.setVisible(false);
                                        }
                                    } else {
                                        JOptionPane.showMessageDialog(mainFrame, "Il n'y a pas personne qui utilise la force Farfadet" + "\n" + "Vous avez perdu la Carte Chien De Garde");
                                        p.getJoueur_final().get(k).getListCarteAvance().get(4).PoserCarte();
                                        jLabelAilee.setVisible(false);
                                    }
                                } else if (output == JOptionPane.NO_OPTION) {
                                }
                            }
                            if ((j == 4) && (p.getJoueur_final().get(k).getListCarteAvance().get(4).getJouer() == false)) {
                                jLabelAilee.setVisible(false);
                            }
                        }

                    } else {
                        // joueur Virtuel debutant
                        if (p.getJoueur_final().get(k).getNiveau().toUpperCase().equals("DEBUTANT")) {
                            p.getJoueur_final().get(k).setStrategie(new Debutant());
                            // choisir carte
                            int numChoisi = p.getJoueur_final().get(k).choisiVirtuelAvance();
                            jTextArea1.setText(jTextArea1.getText() + p.getJoueur_final().get(k).getNomDeJoueur() + " a choisi carte " + p.getJoueur_final().get(k).getListCarteAvance().get(numChoisi).getNom() + "\n");
                            ImageIcon icon5 = createImageIcon(p.getJoueur_final().get(k).getListCarteAvance().get(numChoisi).getImage(), "carteJoueurVirtuel");
                            jLabel5.setIcon(icon5);
                            jLabel5.setVisible(true);
                            String force = p.getJoueur_final().get(k).joueVirtuelAvance(p.getJoueur_final().get(k).getListCarteAvance().get(numChoisi), j, p.getJoueur_final().get(k).getGraine().getTotal());
                            int[][] valeur = p.getJoueur_final().get(k).getListCarteAvance().get(numChoisi).getValeur();
                            jTextArea1.setText(jTextArea1.getText() + p.getJoueur_final().get(k).getNomDeJoueur() + " a choisi " + force + "\n");
                            if (force.toUpperCase().equals("GEANT")) {
                                ((CarteIngredient) p.getJoueur_final().get(k).getListCarteAvance().get(numChoisi)).effectGeant(p.getJoueur_final().get(k), valeur[0][j]);

                                JOptionPane.showMessageDialog(mainFrame, p.getJoueur_final().get(k).getNomDeJoueur() + " choisit la carte " + p.getJoueur_final().get(k).getListCarteAvance().get(numChoisi).getNom()
                                        + " avec valeur " + valeur[0][j] + " de Geant");
                                model.setValueAt(p.getJoueur_final().get(k).getGraine().getTotal(), k, 1);
                            } else if (force.toUpperCase().equals("ENGRAIS")) {
                                ((CarteIngredient) p.getJoueur_final().get(k).getListCarteAvance().get(numChoisi)).effectEngrais(p.getJoueur_final().get(k), valeur[1][j]);

                                JOptionPane.showMessageDialog(mainFrame, p.getJoueur_final().get(k).getNomDeJoueur() + " choisit la carte " + p.getJoueur_final().get(k).getListCarteAvance().get(numChoisi).getNom()
                                        + " avec valeur " + valeur[1][j] + " de Engrais");
                                model.setValueAt(p.getJoueur_final().get(k).getGraine().getTotal(), k, 1);
                                model.setValueAt(p.getJoueur_final().get(k).getChamp().getMenhir(), k, 2);
                            } else if (force.toUpperCase().equals("FARFADET")) {
                                // chercher l'adver pour voler les graines
                                int adver = p.getJoueur_final().get(k).volerVirtuel(p.getJoueur_final(), k, j);
                                int pos1 = p.getJoueur_final().get(adver).getListCarteAvance().size() - 1;
                                // verifier adver avoir une carte Chien De Garde qui n'utilise pas
                                if ((p.getJoueur_final().get(adver).getAvoirCarteAllie()) && (p.getJoueur_final().get(adver).getListCarteAvance().get(pos1).getNom().equals("CHIEN DE GARDE")) && (p.getJoueur_final().get(adver).getListCarteAvance().get(pos1).getJouer() == false)) {
                                    int[][] valeur1 = p.getJoueur_final().get(adver).getListCarteAvance().get(pos1).getValeur();
                                    // verifier adver est joueur physique
                                    if (p.getJoueur_final().get(adver).getNiveau().equalsIgnoreCase("Physique")) {
                                        JOptionPane.showMessageDialog(mainFrame, p.getJoueur_final().get(k).getNomDeJoueur() + " choisit la carte " + p.getJoueur_final().get(k).getListCarteAvance().get(numChoisi).getNom()
                                                + " avec valeur " + valeur[2][j] + " de Farfadet");
                                        int output = JOptionPane.showConfirmDialog(mainFrame, p.getJoueur_final().get(k).getNomDeJoueur() + " veut prendre vos" + valeur[2][j] + " graines" + "\n"
                                                + "Mais vous avez une carte Allie CHIEN DE GARDE avec la valeur = " + valeur1[0][j] + "\n" + "Vous voulez utiliser carte Chien De Garde?", "", JOptionPane.YES_NO_OPTION);
                                        if (output == JOptionPane.YES_OPTION) {
                                            ((CarteAllie) p.getJoueur_final().get(adver).getListCarteAvance().get(pos1)).ChienDeGarde(p.getJoueur_final().get(k), valeur[2][j], p.getJoueur_final().get(adver), valeur1[0][j]);
                                            jTextArea1.setText(jTextArea1.getText() + "Vous avez utilisé la carte CHIEN DE GARDE" + "\n");
                                            jTextArea1.setText(jTextArea1.getText() + "Donc vous avez " + p.getJoueur_final().get(adver).getGraine().getTotal() + " graines" + "\n");
                                            jTextArea1.setText(jTextArea1.getText() + p.getJoueur_final().get(k).getNomDeJoueur() + " a " + p.getJoueur_final().get(k).getGraine().getTotal() + " graines" + "\n");
                                            p.getJoueur_final().get(adver).getListCarteAvance().get(pos1).PoserCarte();
                                            jLabelAilee.setVisible(false);
                                            model.setValueAt(p.getJoueur_final().get(k).getGraine().getTotal(), k, 1);
                                            model.setValueAt(p.getJoueur_final().get(adver).getGraine().getTotal(), adver, 1);
                                        } else if (output == JOptionPane.NO_OPTION) {
                                            ((CarteIngredient) p.getJoueur_final().get(k).getListCarteAvance().get(numChoisi)).effectFarfadet(p.getJoueur_final().get(k), p.getJoueur_final().get(adver), valeur[2][j]);
                                            jTextArea1.setText(jTextArea1.getText() + "Vous n'avez pas utilisé la carte CHIEN DE GARDE" + "\n");
                                            jTextArea1.setText(jTextArea1.getText() + "Donc vous avez " + p.getJoueur_final().get(adver).getGraine().getTotal() + " graines" + "\n");
                                            jTextArea1.setText(jTextArea1.getText() + p.getJoueur_final().get(k).getNomDeJoueur() + " a " + p.getJoueur_final().get(k).getGraine().getTotal() + " graines" + "\n");
//                                            changerTableFarfadet(p.getJoueur_final().get(k), k, p.getJoueur_final().get(adver), adver);
                                            model.setValueAt(p.getJoueur_final().get(k).getGraine().getTotal(), k, 1);
                                            model.setValueAt(p.getJoueur_final().get(adver).getGraine().getTotal(), adver, 1);
                                        }

                                    } else {
                                        // verifier si valeur de chien de garde est pas egale a 0
                                        if (valeur1[0][j] != 0) {
                                            JOptionPane.showMessageDialog(mainFrame, p.getJoueur_final().get(k).getNomDeJoueur() + " choisit la carte " + p.getJoueur_final().get(k).getListCarteAvance().get(numChoisi).getNom()
                                                    + " avec valeur " + valeur[2][j] + " de Farfadet" + "\n"
                                                    + p.getJoueur_final().get(k).getNomDeJoueur() + " veut prendre " + valeur[2][j] + " graines de" + p.getJoueur_final().get(adver).getNomDeJoueur());
                                            jTextArea1.setText(jTextArea1.getText() + p.getJoueur_final().get(k).getNomDeJoueur() + " veut prendre " + valeur[2][j] + " graines de" + p.getJoueur_final().get(adver).getNomDeJoueur() + "\n");
                                            jTextArea1.setText(jTextArea1.getText() + p.getJoueur_final().get(adver).getNomDeJoueur() + " utilise la carte CHIEN DE GARDE" + "\n");
                                            ((CarteAllie) p.getJoueur_final().get(adver).getListCarteAvance().get(pos1)).ChienDeGarde(p.getJoueur_final().get(k), valeur[2][j], p.getJoueur_final().get(adver), valeur1[0][j]);
                                            jTextArea1.setText(jTextArea1.getText() + "Donc " + p.getJoueur_final().get(k).getNomDeJoueur() + " avez " + p.getJoueur_final().get(k).getGraine().getTotal() + " graines");
                                            jTextArea1.setText(jTextArea1.getText() + "Donc " + p.getJoueur_final().get(adver).getNomDeJoueur() + " avez " + p.getJoueur_final().get(adver).getGraine().getTotal() + " graines");
                                            p.getJoueur_final().get(adver).getListCarteAvance().get(pos1).PoserCarte();
                                            model.setValueAt(p.getJoueur_final().get(k).getGraine().getTotal(), k, 1);
                                            model.setValueAt(p.getJoueur_final().get(adver).getGraine().getTotal(), adver, 1);
                                        } else {
                                            JOptionPane.showMessageDialog(mainFrame, p.getJoueur_final().get(k).getNomDeJoueur() + " choisit la carte " + p.getJoueur_final().get(k).getListCarteAvance().get(numChoisi).getNom()
                                                    + " avec valeur " + valeur[2][j] + " de Farfadet" + "\n"
                                                    + p.getJoueur_final().get(k).getNomDeJoueur() + " veut prendre " + valeur[2][j] + " graines de" + p.getJoueur_final().get(adver).getNomDeJoueur());
                                            jTextArea1.setText(jTextArea1.getText() + p.getJoueur_final().get(k).getNomDeJoueur() + " veut prendre " + valeur[2][j] + " graines de" + p.getJoueur_final().get(adver).getNomDeJoueur() + "\n");
                                            jTextArea1.setText(jTextArea1.getText() + p.getJoueur_final().get(adver).getNomDeJoueur() + " n'utilise pas la carte CHIEN DE GARDE" + "\n");
                                            ((CarteIngredient) p.getJoueur_final().get(k).getListCarteAvance().get(numChoisi)).effectFarfadet(p.getJoueur_final().get(k), p.getJoueur_final().get(adver), valeur[2][j]);
                                            jTextArea1.setText(jTextArea1.getText() + "Donc " + p.getJoueur_final().get(k).getNomDeJoueur() + " avez " + p.getJoueur_final().get(k).getGraine().getTotal() + " graines" + "\n");
                                            jTextArea1.setText(jTextArea1.getText() + "Donc " + p.getJoueur_final().get(adver).getNomDeJoueur() + " avez " + p.getJoueur_final().get(adver).getGraine().getTotal() + " graines" + "\n");
                                            model.setValueAt(p.getJoueur_final().get(k).getGraine().getTotal(), k, 1);
                                            model.setValueAt(p.getJoueur_final().get(adver).getGraine().getTotal(), adver, 1);
                                        }
                                    }
                                } else {
                                    if (p.getJoueur_final().get(adver).getNiveau().equalsIgnoreCase("Physique")) {
                                        JOptionPane.showMessageDialog(mainFrame, p.getJoueur_final().get(k).getNomDeJoueur() + " choisit la carte " + p.getJoueur_final().get(k).getListCarteAvance().get(numChoisi).getNom()
                                                + " avec valeur " + valeur[2][j] + " de Farfadet" + "\n"
                                                + p.getJoueur_final().get(k).getNomDeJoueur() + " veut prendre vos " + valeur[2][j] + " graines");
                                        jTextArea1.setText(jTextArea1.getText() + p.getJoueur_final().get(k).getNomDeJoueur() + " veut prendre vos " + valeur[2][j] + " graines" + "\n");
                                        ((CarteIngredient) p.getJoueur_final().get(k).getListCarteAvance().get(numChoisi)).effectFarfadet(p.getJoueur_final().get(k), p.getJoueur_final().get(adver), valeur[2][j]);
                                        jTextArea1.setText(jTextArea1.getText() + "Donc vous avez " + p.getJoueur_final().get(adver).getGraine().getTotal() + " graines" + "\n");
                                        jTextArea1.setText(jTextArea1.getText() + p.getJoueur_final().get(k).getNomDeJoueur() + " a " + p.getJoueur_final().get(k).getGraine().getTotal() + " graines" + "\n");
                                        model.setValueAt(p.getJoueur_final().get(k).getGraine().getTotal(), k, 1);
                                        model.setValueAt(p.getJoueur_final().get(adver).getGraine().getTotal(), adver, 1);
                                    } else {
                                        JOptionPane.showMessageDialog(mainFrame, p.getJoueur_final().get(k).getNomDeJoueur() + " choisit la carte " + p.getJoueur_final().get(k).getListCarteAvance().get(numChoisi).getNom()
                                                + " avec valeur " + valeur[2][j] + " de Farfadet" + "\n"
                                                + p.getJoueur_final().get(k).getNomDeJoueur() + " veut prendre " + valeur[2][j] + " graines de" + p.getJoueur_final().get(adver).getNomDeJoueur());
                                        jTextArea1.setText(jTextArea1.getText() + p.getJoueur_final().get(k).getNomDeJoueur() + " veut prendre " + valeur[2][j] + " graines de" + p.getJoueur_final().get(adver).getNomDeJoueur() + "\n");
                                        ((CarteIngredient) p.getJoueur_final().get(k).getListCarteAvance().get(numChoisi)).effectFarfadet(p.getJoueur_final().get(k), p.getJoueur_final().get(adver), valeur[2][j]);
                                        jTextArea1.setText(jTextArea1.getText() + "Donc " + p.getJoueur_final().get(k).getNomDeJoueur() + " avez " + p.getJoueur_final().get(k).getGraine().getTotal() + " graines" + "\n");
                                        jTextArea1.setText(jTextArea1.getText() + "Donc " + p.getJoueur_final().get(adver).getNomDeJoueur() + " avez " + p.getJoueur_final().get(adver).getGraine().getTotal() + " graines" + "\n");
                                        model.setValueAt(p.getJoueur_final().get(k).getGraine().getTotal(), k, 1);
                                        model.setValueAt(p.getJoueur_final().get(adver).getGraine().getTotal(), adver, 1);
                                    }
                                }

                            }
                            // determiner utiliser carte Taupe geant ou pas (j est la saison)
                            if ((j == 3) && (p.getJoueur_final().get(k).getAvoirCarteAllie())) {
                                int pos1 = 0;
                                for (int n = 0; n < p.getJoueur_final().get(k).getListCarteAvance().size(); n++) {
                                    if (p.getJoueur_final().get(k).getListCarteAvance().get(n) instanceof CarteAllie) {
                                        pos1 = n;
                                    }
                                }
                                if (p.getJoueur_final().get(k).getListCarteAvance().get(pos1).getNom().equals("TAUPE GEANT") && (p.getJoueur_final().get(k).getListCarteAvance().get(pos1).getJouer() == false)) {

                                    int adver = p.getJoueur_final().get(k).sDetruireMenhir(p.getJoueur_final(), k);
                                    int[][] valeur1 = p.getJoueur_final().get(k).getListCarteAvance().get(pos1).getValeur();
                                    JOptionPane.showMessageDialog(mainFrame, p.getJoueur_final().get(k).getNomDeJoueur() + " utilise carte TAUPE GEANT" + "\n"
                                            + p.getJoueur_final().get(k).getNomDeJoueur() + " veut detruire " + valeur1[0][j] + " menhirs de " + p.getJoueur_final().get(adver).getNomDeJoueur());
                                    ((CarteAllie) p.getJoueur_final().get(k).getListCarteAvance().get(pos1)).TaupeGeant(p.getJoueur_final().get(k), valeur1[0][j], p.getJoueur_final().get(adver));
                                    jTextArea1.setText(jTextArea1.getText() + p.getJoueur_final().get(k).getNomDeJoueur() + " a " + p.getJoueur_final().get(k).getChamp().getMenhir() + " Menhirs" + "\n");
                                    jTextArea1.setText(jTextArea1.getText() + p.getJoueur_final().get(adver).getNomDeJoueur() + " a " + p.getJoueur_final().get(adver).getChamp().getMenhir() + " Menhirs" + "\n");
                                    p.getJoueur_final().get(k).getListCarteAvance().get(pos1).PoserCarte();
                                    model.setValueAt(p.getJoueur_final().get(adver).getChamp().getMenhir(), adver, 2);
                                }
                            }
                            jLabel5.setVisible(false);
                        } else {
                            if (p.getJoueur_final().get(k).getNiveau().toUpperCase().equalsIgnoreCase("AVANCE")) {
                                p.getJoueur_final().get(k).setStrategie(new Avance());
                                int numChoisi = p.getJoueur_final().get(k).choisiVirtuelAvance();
                                ImageIcon icon5 = createImageIcon(p.getJoueur_final().get(k).getListCarteAvance().get(numChoisi).getImage(), "carteJoueurVirtuel");
                                jLabel5.setIcon(icon5);
                                jLabel5.setVisible(true);
                                jTextArea1.setText(jTextArea1.getText() + p.getJoueur_final().get(k).getNomDeJoueur() + " a choisi carte " + p.getJoueur_final().get(k).getListCarteAvance().get(numChoisi).getNom() + "\n");
                                String force = p.getJoueur_final().get(k).joueVirtuelAvance(p.getJoueur_final().get(k).getListCarteAvance().get(numChoisi), j, p.getJoueur_final().get(k).getGraine().getTotal());
                                jTextArea1.setText(jTextArea1.getText() + p.getJoueur_final().get(k).getNomDeJoueur() + " a choisi " + force + "\n");
                                int[][] valeur = p.getJoueur_final().get(k).getListCarteAvance().get(numChoisi).getValeur();
                                if (force.toUpperCase().equals("GEANT")) {
                                    ((CarteIngredient) p.getJoueur_final().get(k).getListCarteAvance().get(numChoisi)).effectGeant(p.getJoueur_final().get(k), valeur[0][j]);

                                    JOptionPane.showMessageDialog(mainFrame, p.getJoueur_final().get(k).getNomDeJoueur() + " choisit la carte " + p.getJoueur_final().get(k).getListCarteAvance().get(numChoisi).getNom()
                                            + " avec valeur " + valeur[0][j] + " de Geant");
                                    model.setValueAt(p.getJoueur_final().get(k).getGraine().getTotal(), k, 1);
                                } else if (force.toUpperCase().equals("ENGRAIS")) {
                                    ((CarteIngredient) p.getJoueur_final().get(k).getListCarteAvance().get(numChoisi)).effectEngrais(p.getJoueur_final().get(k), valeur[1][j]);

                                    JOptionPane.showMessageDialog(mainFrame, p.getJoueur_final().get(k).getNomDeJoueur() + " choisit la carte " + p.getJoueur_final().get(k).getListCarteAvance().get(numChoisi).getNom()
                                            + " avec valeur " + valeur[1][j] + " de Engrais");
                                    model.setValueAt(p.getJoueur_final().get(k).getGraine().getTotal(), k, 1);
                                    model.setValueAt(p.getJoueur_final().get(k).getChamp().getMenhir(), k, 2);
                                } else if (force.toUpperCase().equals("FARFADET")) {

                                    // chercher adver et determiner que l'adver a carte chien de garde ou pas
                                    int adver = p.getJoueur_final().get(k).volerVirtuel(p.getJoueur_final(), k, j);
                                    int pos1 = p.getJoueur_final().get(adver).getListCarteAvance().size() - 1;
                                    if ((p.getJoueur_final().get(adver).getAvoirCarteAllie()) && (p.getJoueur_final().get(adver).getListCarteAvance().get(pos1).getNom().equals("CHIEN DE GARDE")) && (p.getJoueur_final().get(adver).getListCarteAvance().get(pos1).getJouer() == false)) {
                                        int[][] valeur1 = p.getJoueur_final().get(adver).getListCarteAvance().get(pos1).getValeur();
                                        if (p.getJoueur_final().get(adver).getNiveau().equalsIgnoreCase("Physique")) {
                                            JOptionPane.showMessageDialog(mainFrame, p.getJoueur_final().get(k).getNomDeJoueur() + " choisit la carte " + p.getJoueur_final().get(k).getListCarteAvance().get(numChoisi).getNom()
                                                    + " avec valeur " + valeur[2][j] + " de Farfadet");
                                            int output = JOptionPane.showConfirmDialog(mainFrame, p.getJoueur_final().get(k).getNomDeJoueur() + " veut prendre vos" + valeur[2][j] + " graines" + "\n"
                                                    + "Mais vous avez une carte Allie CHIEN DE GARDE avec la valeur = " + valeur1[0][j] + "\n" + "Vous voulez utiliser carte Chien De Garde?", "", JOptionPane.YES_NO_OPTION);

                                            if (output == JOptionPane.YES_OPTION) {
                                                ((CarteAllie) p.getJoueur_final().get(adver).getListCarteAvance().get(pos1)).ChienDeGarde(p.getJoueur_final().get(k), valeur[2][j], p.getJoueur_final().get(adver), valeur1[0][j]);
                                                jTextArea1.setText(jTextArea1.getText() + "Vous avez utilisé la carte CHIEN DE GARDE" + "\n");
                                                jTextArea1.setText(jTextArea1.getText() + "Donc vous avez " + p.getJoueur_final().get(adver).getGraine().getTotal() + " graines" + "\n");
                                                jTextArea1.setText(jTextArea1.getText() + p.getJoueur_final().get(k).getNomDeJoueur() + " a " + p.getJoueur_final().get(k).getGraine().getTotal() + " graines" + "\n");
                                                p.getJoueur_final().get(adver).getListCarteAvance().get(pos1).PoserCarte();
                                                jLabelAilee.setVisible(false);
                                                model.setValueAt(p.getJoueur_final().get(k).getGraine().getTotal(), k, 1);
                                                model.setValueAt(p.getJoueur_final().get(adver).getGraine().getTotal(), adver, 1);
                                            } else if (output == JOptionPane.NO_OPTION) {
                                                ((CarteIngredient) p.getJoueur_final().get(k).getListCarteAvance().get(numChoisi)).effectFarfadet(p.getJoueur_final().get(k), p.getJoueur_final().get(adver), valeur[2][j]);
                                                jTextArea1.setText(jTextArea1.getText() + "Vous n'avez pas utilisé la carte CHIEN DE GARDE" + "\n");
                                                jTextArea1.setText(jTextArea1.getText() + "Donc vous avez " + p.getJoueur_final().get(adver).getGraine().getTotal() + " graines" + "\n");
                                                jTextArea1.setText(jTextArea1.getText() + p.getJoueur_final().get(k).getNomDeJoueur() + " a " + p.getJoueur_final().get(k).getGraine().getTotal() + " graines" + "\n");
                                                model.setValueAt(p.getJoueur_final().get(k).getGraine().getTotal(), k, 1);
                                                model.setValueAt(p.getJoueur_final().get(adver).getGraine().getTotal(), adver, 1);
                                            }
                                        } else {
                                            JOptionPane.showMessageDialog(mainFrame, p.getJoueur_final().get(k).getNomDeJoueur() + " choisit la carte " + p.getJoueur_final().get(k).getListCarteAvance().get(numChoisi).getNom()
                                                    + " avec valeur " + valeur[2][j] + " de Farfadet" + "\n"
                                                    + p.getJoueur_final().get(k).getNomDeJoueur() + " veut prendre " + valeur[2][j] + " graines de" + p.getJoueur_final().get(adver).getNomDeJoueur());
                                            jTextArea1.setText(jTextArea1.getText() + p.getJoueur_final().get(k).getNomDeJoueur() + " veut prendre " + valeur[2][j] + " graines de" + p.getJoueur_final().get(adver).getNomDeJoueur() + "\n");
                                            if (p.getJoueur_final().get(adver).getGraine().getTotal() == 0) {
                                                ((CarteIngredient) p.getJoueur_final().get(k).getListCarteAvance().get(numChoisi)).effectFarfadet(p.getJoueur_final().get(k), p.getJoueur_final().get(adver), valeur[2][j]);
                                                jTextArea1.setText(jTextArea1.getText() + p.getJoueur_final().get(adver).getNomDeJoueur() + " n'utilise pas la carte CHIEN DE GARDE" + "\n");
                                                ((CarteIngredient) p.getJoueur_final().get(k).getListCarteAvance().get(numChoisi)).effectFarfadet(p.getJoueur_final().get(k), p.getJoueur_final().get(adver), valeur[2][j]);
                                                jTextArea1.setText(jTextArea1.getText() + "Donc " + p.getJoueur_final().get(k).getNomDeJoueur() + " avez " + p.getJoueur_final().get(k).getGraine().getTotal() + " graines" + "\n");
                                                jTextArea1.setText(jTextArea1.getText() + "Donc " + p.getJoueur_final().get(adver).getNomDeJoueur() + " avez " + p.getJoueur_final().get(adver).getGraine().getTotal() + " graines" + "\n");
                                                model.setValueAt(p.getJoueur_final().get(k).getGraine().getTotal(), k, 1);
                                                model.setValueAt(p.getJoueur_final().get(adver).getGraine().getTotal(), adver, 1);
                                            } else {
                                                if ((valeur[2][j] > 0) && (valeur1[0][j] != 0)) {
                                                    jTextArea1.setText(jTextArea1.getText() + p.getJoueur_final().get(adver).getNomDeJoueur() + " utilise la carte CHIEN DE GARDE" + "\n");
                                                    ((CarteAllie) p.getJoueur_final().get(adver).getListCarteAvance().get(pos1)).ChienDeGarde(p.getJoueur_final().get(k), valeur[2][j], p.getJoueur_final().get(adver), valeur1[0][j]);
                                                    jTextArea1.setText(jTextArea1.getText() + "Donc " + p.getJoueur_final().get(k).getNomDeJoueur() + " avez " + p.getJoueur_final().get(k).getGraine().getTotal() + " graines");
                                                    jTextArea1.setText(jTextArea1.getText() + "Donc " + p.getJoueur_final().get(adver).getNomDeJoueur() + " avez " + p.getJoueur_final().get(adver).getGraine().getTotal() + " graines");
                                                    p.getJoueur_final().get(adver).getListCarteAvance().get(pos1).PoserCarte();
                                                    model.setValueAt(p.getJoueur_final().get(k).getGraine().getTotal(), k, 1);
                                                    model.setValueAt(p.getJoueur_final().get(adver).getGraine().getTotal(), adver, 1);
                                                } else {
                                                    ((CarteIngredient) p.getJoueur_final().get(k).getListCarteAvance().get(numChoisi)).effectFarfadet(p.getJoueur_final().get(k), p.getJoueur_final().get(adver), valeur[2][j]);
                                                    jTextArea1.setText(jTextArea1.getText() + p.getJoueur_final().get(adver).getNomDeJoueur() + " n'utilise pas la carte CHIEN DE GARDE" + "\n");
                                                    jTextArea1.setText(jTextArea1.getText() + "Donc " + p.getJoueur_final().get(k).getNomDeJoueur() + " avez " + p.getJoueur_final().get(k).getGraine().getTotal() + " graines" + "\n");
                                                    jTextArea1.setText(jTextArea1.getText() + "Donc " + p.getJoueur_final().get(adver).getNomDeJoueur() + " avez " + p.getJoueur_final().get(adver).getGraine().getTotal() + " graines" + "\n");
                                                    model.setValueAt(p.getJoueur_final().get(k).getGraine().getTotal(), k, 1);
                                                    model.setValueAt(p.getJoueur_final().get(adver).getGraine().getTotal(), adver, 1);
                                                }
                                            }
                                        }
                                    } else {
                                        if (p.getJoueur_final().get(adver).getNiveau().equalsIgnoreCase("Physique")) {
                                            JOptionPane.showMessageDialog(mainFrame, p.getJoueur_final().get(k).getNomDeJoueur() + " choisit la carte " + p.getJoueur_final().get(k).getListCarteAvance().get(numChoisi).getNom()
                                                    + " avec valeur " + valeur[2][j] + " de Farfadet" + "\n"
                                                    + p.getJoueur_final().get(k).getNomDeJoueur() + " veut prendre vos " + valeur[2][j] + " graines");
                                            jTextArea1.setText(jTextArea1.getText() + p.getJoueur_final().get(k).getNomDeJoueur() + " veut prendre vos " + valeur[2][j] + " graines" + "\n");
                                            ((CarteIngredient) p.getJoueur_final().get(k).getListCarteAvance().get(numChoisi)).effectFarfadet(p.getJoueur_final().get(k), p.getJoueur_final().get(adver), valeur[2][j]);
                                            jTextArea1.setText(jTextArea1.getText() + "Donc vous avez " + p.getJoueur_final().get(adver).getGraine().getTotal() + " graines" + "\n");
                                            jTextArea1.setText(jTextArea1.getText() + p.getJoueur_final().get(k).getNomDeJoueur() + " a " + p.getJoueur_final().get(k).getGraine().getTotal() + " graines" + "\n");
                                            model.setValueAt(p.getJoueur_final().get(k).getGraine().getTotal(), k, 1);
                                            model.setValueAt(p.getJoueur_final().get(adver).getGraine().getTotal(), adver, 1);
                                        } else {
                                            JOptionPane.showMessageDialog(mainFrame, p.getJoueur_final().get(k).getNomDeJoueur() + " choisit la carte " + p.getJoueur_final().get(k).getListCarteAvance().get(numChoisi).getNom()
                                                    + " avec valeur " + valeur[2][j] + " de Farfadet" + "\n"
                                                    + p.getJoueur_final().get(k).getNomDeJoueur() + " veut prendre " + valeur[2][j] + " graines de" + p.getJoueur_final().get(adver).getNomDeJoueur());
                                            jTextArea1.setText(jTextArea1.getText() + p.getJoueur_final().get(k).getNomDeJoueur() + " veut prendre " + valeur[2][j] + " graines de" + p.getJoueur_final().get(adver).getNomDeJoueur() + "\n");
                                            ((CarteIngredient) p.getJoueur_final().get(k).getListCarteAvance().get(numChoisi)).effectFarfadet(p.getJoueur_final().get(k), p.getJoueur_final().get(adver), valeur[2][j]);
                                            jTextArea1.setText(jTextArea1.getText() + "Donc " + p.getJoueur_final().get(k).getNomDeJoueur() + " avez " + p.getJoueur_final().get(k).getGraine().getTotal() + " graines" + "\n");
                                            jTextArea1.setText(jTextArea1.getText() + "Donc " + p.getJoueur_final().get(adver).getNomDeJoueur() + " avez " + p.getJoueur_final().get(adver).getGraine().getTotal() + " graines" + "\n");
                                            model.setValueAt(p.getJoueur_final().get(k).getGraine().getTotal(), k, 1);
                                            model.setValueAt(p.getJoueur_final().get(adver).getGraine().getTotal(), adver, 1);
                                        }
                                    }
                                }
                                if (p.getJoueur_final().get(k).getAvoirCarteAllie() && (p.getJoueur_final().get(k).getListCarteAvance().get(4).getJouer() == false)) {
                                    int pos1 = 0;
                                    for (int n = 0; n < p.getJoueur_final().get(k).getListCarteAvance().size(); n++) {
                                        if (p.getJoueur_final().get(k).getListCarteAvance().get(n) instanceof CarteAllie) {
                                            pos1 = n;
                                        }
                                    }
                                    int[][] valeur1 = p.getJoueur_final().get(k).getListCarteAvance().get(pos1).getValeur();
                                    if (valeur1[0][3] != 0) {
                                        if (j == 3) {
                                            if (p.getJoueur_final().get(k).getListCarteAvance().get(pos1).getNom().equals("TAUPE GEANT")) {
                                                int adver = p.getJoueur_final().get(k).sDetruireMenhir(p.getJoueur_final(), k);
                                                JOptionPane.showMessageDialog(mainFrame, p.getJoueur_final().get(k).getNomDeJoueur() + " utilise carte TAUPE GEANT" + "\n"
                                                        + p.getJoueur_final().get(k).getNomDeJoueur() + " veut detruire " + valeur1[0][j] + " menhirs de " + p.getJoueur_final().get(adver).getNomDeJoueur());
                                                ((CarteAllie) p.getJoueur_final().get(k).getListCarteAvance().get(pos1)).TaupeGeant(p.getJoueur_final().get(k), valeur1[0][j], p.getJoueur_final().get(adver));
                                                jTextArea1.setText(jTextArea1.getText() + p.getJoueur_final().get(k).getNomDeJoueur() + " a " + p.getJoueur_final().get(k).getChamp().getMenhir() + " Menhirs" + "\n");
                                                jTextArea1.setText(jTextArea1.getText() + p.getJoueur_final().get(adver).getNomDeJoueur() + " a " + p.getJoueur_final().get(adver).getChamp().getMenhir() + " Menhirs" + "\n");
                                                p.getJoueur_final().get(k).getListCarteAvance().get(pos1).PoserCarte();
                                                model.setValueAt(p.getJoueur_final().get(adver).getChamp().getMenhir(), adver, 2);
                                            }
                                        }
                                    } else {
                                        int pos2 = 1;
                                        // car je pense que le menhir est pousse quand la saison est automne ou hiver
                                        if ((valeur1[0][2] >= valeur[0][1]) || (valeur1[0][2] > 0)) {
                                            pos2 = 2;
                                        }
                                        if (j == pos2) {
                                            if (p.getJoueur_final().get(k).getListCarteAvance().get(pos1).getNom().equals("TAUPE GEANT")) {
                                                int adver = p.getJoueur_final().get(k).sDetruireMenhir(p.getJoueur_final(), k);
                                                JOptionPane.showMessageDialog(mainFrame, p.getJoueur_final().get(k).getNomDeJoueur() + " utilise carte TAUPE GEANT" + "\n"
                                                        + p.getJoueur_final().get(k).getNomDeJoueur() + " veut detruire " + valeur1[0][j] + " menhirs de " + p.getJoueur_final().get(adver).getNomDeJoueur());
                                                ((CarteAllie) p.getJoueur_final().get(k).getListCarteAvance().get(pos1)).TaupeGeant(p.getJoueur_final().get(k), valeur1[0][j], p.getJoueur_final().get(adver));
                                                jTextArea1.setText(jTextArea1.getText() + p.getJoueur_final().get(k).getNomDeJoueur() + " a " + p.getJoueur_final().get(k).getChamp().getMenhir() + " Menhirs" + "\n");
                                                jTextArea1.setText(jTextArea1.getText() + p.getJoueur_final().get(adver).getNomDeJoueur() + " a " + p.getJoueur_final().get(adver).getChamp().getMenhir() + " Menhirs" + "\n");
                                                p.getJoueur_final().get(k).getListCarteAvance().get(pos1).PoserCarte();
                                                model.setValueAt(p.getJoueur_final().get(adver).getChamp().getMenhir(), adver, 2);

                                            }
                                        }
                                    }
                                }
                                jLabel5.setVisible(false);
                            }
                        }
                    }
                }
                JOptionPane.showMessageDialog(mainFrame, "Tour " + tour.getTour() + " a fini");
                jTextArea1.setText("");
                choisirCarte = false;
                choisirForce = false;
            }
            JOptionPane.showMessageDialog(mainFrame, "Manche " + m + " a fini");
            jLabel7.setText("");
            jLabel3.setText("");
            jLabel4.setText("");

            for (int k = 0; k < p.getJoueur_final().size(); k++) {
                p.getJoueur_final().get(k).getCarteComptage().ajouterMenhirTotal(p.getJoueur_final().get(k).getChamp().getMenhir());
            }
            for (int k = 0; k < p.getJoueur_final().size(); k++) {
                model.setValueAt(p.getJoueur_final().get(k).getCarteComptage().getNombreMenhirTotal(), k, 3);
            }
            check = true;
            p.getGame().redemarrerListCarteAvance(listCarteAvance);
//            jLabelAilee.setIcon(null);
//            jLabelAilee.repaint();
//            mainFrame.revalidate();
//            mainFrame.repaint();
            model.setRowCount(0);
        } else if (arg == "finiAvancee") {
            System.out.println("");
            System.out.println("La partie est teminee");
            System.out.println("");
            System.out.println("afficher les resultats");
            // trouver le nombre de menhirs est le plus grand
            int max = game.trouveMaxMenhir(p.getJoueur_final());
            // trier list Joueur par le nombre de menhirs
            ArrayList<Joueur> listJoueurFinal = game.triParMenhirsAvance(p.getJoueur_final());
            game.afficherResultatAvance(listJoueurFinal);
            System.out.println("");
            // si le premier joueur a plus menhirs que le deuxieme -> le premier est gagne
            if (listJoueurFinal.get(0).getCarteComptage().getNombreMenhirTotal() > listJoueurFinal.get(1).getCarteComptage().getNombreMenhirTotal()) {
                System.out.println(listJoueurFinal.get(0).getNomDeJoueur() + " a gagne");
                JOptionPane.showMessageDialog(mainFrame, listJoueurFinal.get(0).getNomDeJoueur() + " a gagne");
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
                    String list = "";
                    for (int i = 0; i < listJoueurMemeMenhirsMemeGraines.size(); i++) {
                        list += listJoueurMemeMenhirsMemeGraines.get(i).getNomDeJoueur() + " a " + listJoueurMemeMenhirsMemeGraines.get(i).getCarteComptage().getNombreMenhirTotal() + " menhirs et " + listJoueurMemeMenhirsMemeGraines.get(i).getGraine().getTotal() + " graines (les graines dans la derniere manche).";
                    }
                    JOptionPane.showMessageDialog(mainFrame, "Le game est null entre " + list);
                }
                if (listJoueurMemeMenhirsMemeGraines.size() == 1) {
                    String list = "";
                    for (int i = 0; i < listJoueurMemeMenhirsMemeGraines.size(); i++) {
                        list += listJoueurMemeMenhirsMemeGraines.get(i).getNomDeJoueur() + " a " + listJoueurMemeMenhirsMemeGraines.get(i).getCarteComptage().getNombreMenhirTotal() + " menhirs et " + listJoueurMemeMenhirsMemeGraines.get(i).getGraine().getTotal() + " graines (les graines dans la derniere manche).";
                    }
                    System.out.println("Le gagnant est");
                    JOptionPane.showMessageDialog(mainFrame, "Le gagnant est " + list);
                }
            }
            int output = JOptionPane.showConfirmDialog(mainFrame, "Vous voulez jouer l'autre game?", "", JOptionPane.YES_NO_OPTION);
            if (output == JOptionPane.YES_OPTION) {
                mainFrame.dispose();
                Controlleur c = new Controlleur();
                c.init();
            } else if (output == JOptionPane.NO_OPTION) {
                System.exit(0);
            }
        }

    }
}
