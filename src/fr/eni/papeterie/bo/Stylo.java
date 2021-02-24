package fr.eni.papeterie.bo;

/**
 * Développement d'une applicaion JAVA en couche
 * TP Papeterie
 * @author Jonathan CAMARA
 *	ENI - 2021
 *
 * Stylo.java
 */

/**
 * Articles de la base cliens de type stylo
 */

public class Stylo extends Article {
	
	//----- PUBLIC
	
	public Stylo(){
	 super();
	}

	public Stylo(int idArticle, String marque, String ref, String designation, float pu, int qte, String couleur) {
		super(idArticle, marque, ref, designation, pu, qte);
		this.couleur = couleur;
	}
	
	public Stylo(String marque, String ref, String designation, float pu, int qte, String couleur) {
		super(marque, ref, designation, pu, qte);
		this.couleur = couleur;
	}

	public String toString() {
		return String.format("%s [Couleur=%s]", super.toString(), this.couleur);
	};
	
	public String getCouleur() {
		return couleur;
	}

	public void setCouleur(String couleur) {
		this.couleur = couleur;
	}
	
	//----- PRIVATE
	
	private String couleur = "";
	
}
