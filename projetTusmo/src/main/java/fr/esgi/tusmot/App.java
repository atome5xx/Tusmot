package fr.esgi.tusmot;

import java.util.Scanner;

public class App {

	// Attributs
	private static Scanner scanner = new Scanner(System.in);
	private static String mot;
	private static int nbEssaie = 0;
	private static int nbLettreJuste;

	public static void main(String[] args) {

		String motGagnant = "fraise";
		do {
			nbEssaie = nbEssaie + 1;
			nbLettreJuste = 0;
			do {
				System.out.println("Veuillez entrer un mot : ");
				mot = scanner.nextLine();
			} while (!validerMot(motGagnant, mot));

			System.out.println(mot);
			// je passe les lettres de mon mot un par un
			for (int i = 0; i < mot.length(); i++) {
				// je vérifie si la lettre appartient au mot recherché
				if (appartenirAuMot(mot.charAt(i), motGagnant)) {
					// si la lettre est a la bonne place j'incrémente le nombre de lettre juste de 1
					if (situerCorrectement(mot.charAt(i), motGagnant.charAt(i))) {
						System.out.println("'" + mot.charAt(i) + "'est dans le mot et à la bonne place/");
						nbLettreJuste = nbLettreJuste + 1;
					} else {
						if (supprimerDoublon(motGagnant, mot, mot.charAt(i), i)) {
							System.out.println("'" + mot.charAt(i) + "'n'est pas bon/ ");
						} else {
							System.out.println("'" + mot.charAt(i) + "'est dans le mot mais à la mauvaise place/ ");
						}
					}
				} else {
					System.out.println("'" + mot.charAt(i) + "'n'est pas dans le mot/ ");
				}
			}
		} while (nbLettreJuste != motGagnant.length() && nbEssaie < 6);
		if (gagnerPartie(nbLettreJuste, motGagnant)) {
			System.out.println("Vous avez gagné en " + nbEssaie + " essaie(s)!!!");
		} else {
			System.out.println("Perdu :'(");
		}

		// Fermer le scanner
		scanner.close();
	}

	/**
	 * Indique si le mot rentré par le joueur est valide (nombre de caractère)
	 * 
	 * @param motJoueur
	 * @param motGagnant
	 * @return
	 */
	private static boolean validerMot(String motGagnant, String motJoueur) {
		if (motJoueur.length() == motGagnant.length()) {
			return true;
		}
		return false;
	}

	/**
	 * Indique si le caractère est présent
	 * 
	 * @param motJoueur
	 * @param motGagnant
	 * @return
	 */
	private static boolean appartenirAuMot(char caractere, String motGagnant) {
		if (motGagnant.contains(String.valueOf(caractere))) {
			return true;
		}
		return false;
	}

	/**
	 * Indique si le caractère est à la bonne place
	 * 
	 * @param motJoueur
	 * @param motGagnant @
	 */
	private static boolean situerCorrectement(char caractere, char caractereGagnant) {
		if (caractere == caractereGagnant) {
			return true;
		}
		return false;
	}

	/**
	 * Indique si le joueur à gagné
	 * 
	 * @param motJoueur
	 * @param motGagnant @
	 */
	private static boolean gagnerPartie(int nbLettreJuste, String motGagnant) {
		if (nbLettreJuste == motGagnant.length()) {
			return true;
		}
		return false;
	}

	/**
	 * Indique le nombre d'occurence d'une lettre dans un mot
	 * 
	 * @param mot
	 * @param lettre
	 */
	private static int compterLettre(String mot, char lettre) {
		int count = 0;
		for (int i = 0; i < mot.length(); i++) {
			if (mot.charAt(i) == lettre) {
				count++;
			}
		}
		return count;
	}

	/**
	 * Indique si la lettre n'est pas en trop
	 * 
	 * @param motGagnant
	 * @param motJoueur
	 * @param lettre
	 * @param indice
	 */
	private static boolean supprimerDoublon(String motGagnant, String motJoueur, char lettre, int indice) {
		// autant du même caractere dans chaque mot, on les laisse
		if (compterLettre(motGagnant, lettre) == compterLettre(motJoueur, lettre)) {
			return false;
		}
		// plus de cractere dans le mot gagnant on laisse
		else if (compterLettre(motGagnant, lettre) > compterLettre(motJoueur, lettre)) {
			return false;
		}
		// plus de caractere dans le mot du joueur il faut en supprimé
		else {
			int difference = compterLettre(motJoueur, lettre) - compterLettre(motGagnant, lettre);
			while (difference > 0) {
				int positionLettre = motJoueur.indexOf(lettre);
				if (positionLettre == indice) {
					return true;
				}
				if (!situerCorrectement(motGagnant.charAt(positionLettre), motJoueur.charAt(positionLettre))) {
					difference--;
					motJoueur = motJoueur.substring(0, positionLettre) + " " + motJoueur.substring(positionLettre + 1);
					motGagnant = motGagnant.substring(0, positionLettre) + " "
							+ motGagnant.substring(positionLettre + 1);
				}
			}
			return false;
		}
	}

}