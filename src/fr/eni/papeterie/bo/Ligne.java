package fr.eni.papeterie.bo;

/**
 * Développement d'une applicaion JAVA en couche
 * TP Papeterie
 * @author Jonathan CAMARA
 *	ENI - 2021
 *
 * Ligne.java
 */

/**
 * Un ligne d'article avec les données associées pour passer une commande de celui-ci
 */

public class Ligne {
	
	//----- PUBLIC
	
	public Ligne(int qte, Article article) {
		super();
		this.qte = qte;
		this.article = article;
	}
	
	public int getQte() {
		return qte;
	}

	public void setQte(int qte) {
		this.qte = qte;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}
	
	public void deltaQuantite(int qte) {
		if(qte != 0) this.qte += qte;
	}
	
	public String toString() {
		return String.format("%s [ qte=%d, prix=%.2f, article=%s ]", this.getClass().getSimpleName(), this.qte, this.article.getPrixUnitaire(), this.article.toString() );
	}
	
	//----- PRIVATE

	private int qte = 0;

	private Article article = null;
}
