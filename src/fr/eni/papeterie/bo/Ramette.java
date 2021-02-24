package fr.eni.papeterie.bo;

/**
 * Développement d'une applicaion JAVA en couche
 * TP Papeterie
 * @author Jonathan CAMARA
 *	ENI - 2021
 *
 * Ramette.java
 */

/**
 * Articles de la base cliens de type Ramette
 */

public class Ramette extends Article {
	
	//----- PUBLIC
	
	public Ramette(){
	 super();
	}
	
	public Ramette(int idArticle, String marque, String ref, String designation, float pu, int qte, int grammage) {
		super(idArticle, marque, ref, designation, pu, qte);
		this.grammage = grammage;
	}
	
	public Ramette(String marque, String ref, String designation, float pu, int qte, int grammage) {
		super(marque, ref, designation, pu, qte);
		this.grammage = grammage;
	}

	public String toString() {
		return String.format("%s [Grammage=%sg/m²]", super.toString(), this.grammage);
	};
	
	public int getGrammage() {
		return grammage;
	}

	public void setGrammage(int grammage) {
		this.grammage = grammage;
	}
	
	//----- PRIVATE

	private int grammage = 0;
	
}
