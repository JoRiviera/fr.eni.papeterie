package fr.eni.papeterie.bo;

/**
 * Développement d'une applicaion JAVA en couche
 * TP Papeterie
 * @author Jonathan CAMARA
 *	ENI - 2021
 *
 * Article.java
 */

/**
 * Classe abstraite qui sera instanciée par tous les articles de la base clients
 */
public abstract class Article {
	
	//----- PUBLIC
	
	public String toString() {
		return String.format("%s [idArticle=%d, reference=%s, marque = %s, designation=%s, prixUnitaire=%.2f, qteStock=%d] %s",
							this.getClass().getSuperclass().getSimpleName(), this.idArticle, this.reference, this.marque, this.designation, this.prixUnitaire, this.qteStock, this.getClass().getSimpleName());
	}
	
	public int getIdArticle() {
		return idArticle;
	}

	public void setIdArticle(int idArticle) {
		this.idArticle = idArticle;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getMarque() {
		return marque;
	}

	public void setMarque(String marque) {
		this.marque = marque;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public float getPrixUnitaire() {
		return prixUnitaire;
	}

	public void setPrixUnitaire(float prixUnitaire) {
		this.prixUnitaire = prixUnitaire;
	}

	public int getQteStock() {
		return qteStock;
	}

	public void setQteStock(int qteStock) {
		this.qteStock = qteStock;
	}
	
	//----- PRIVATE API
	
	// Constructors
	
	public Article() {
		super();
	}
	
	public Article(String marque, String ref, String designation, float pu, int qte) {
		this.marque = marque;
		this.reference = ref;
		this.designation = designation;
		this.prixUnitaire = pu;
		this.qteStock = qte;
	};

	public Article(int idArticle, String marque, String ref, String designation, float pu, int qte) {
		this(marque, ref, designation, pu, qte);
		this.idArticle = idArticle;
	};
	
	// Properties


	protected int idArticle = 0;
	protected String reference = "";
	protected String marque = "";
	protected String designation = "";
	protected float prixUnitaire = 0f;
	protected int qteStock = 0;
	
}
