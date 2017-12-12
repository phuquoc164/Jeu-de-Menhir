package Console;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // Initialiser le game
        Scanner scannerString = new Scanner(System.in);
        Scanner scannerInt = new Scanner(System.in);
        System.out.println("---------Menhir-------");
        System.out.println("Saisir votre nom");
        String nom = scannerString.nextLine();
        System.out.println("Saisir votre age");
        int age = scannerInt.nextInt();
        System.out.println("Saisir le nombre de joueurs = ");
        int nombreDeJoueur = scannerInt.nextInt();
        System.out.println("Choisir le niveau de joueur Virtuel : Debutant ou Avance ");
        String niveau = scannerString.nextLine();
        System.out.println("Choisir la mode de jouer Rapide ou Avance: ");
        String partieRapide = scannerString.nextLine();
        if (partieRapide.equalsIgnoreCase("Rapide")) {
            PartieRapide.runPartieRapide(nom, age, nombreDeJoueur, niveau);
        } else {
            PartieAvance.runPartieAvance(nom, age, nombreDeJoueur, niveau);
        }
        System.out.println("");
        System.out.println("Le jeux est fini!!!");
    }
}
