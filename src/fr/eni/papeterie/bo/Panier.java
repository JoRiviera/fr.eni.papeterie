package fr.eni.papeterie.bo;

import java.util.ArrayList;
import java.util.List;

/**
 * Développement d'une applicaion JAVA en couche
 * TP Papeterie
 * @author Jonathan CAMARA
 *	ENI - 2021
 *
 * Panier.java
 */

/**
 * Panier client avant commande
 */

public class Panier {
	
	//---- PUBLIC
	
	public Panier() {
		super();
		this.lignes = new ArrayList<>();
	}
	
	public float getMontant() {
		return montant;
	}
	
	/**
	 * Ligne du panier
	 * @param index zero based dans la collection de ligne
	 * @return Ligne
	 */
	public Ligne getLigne(int index) {
		return this.lignes.get(index);
	}
	
	/**
	 * Collection des lignes du panier
	 * @return
	 */
	public List<Ligne> getLignesPanier(){
		return this.lignes;
	}
	
	/**
	 * Ajout d'une ligne à la collection du panier.
	 * @param article
	 * @param qte
	 * @return success de l'ajout
	 */
	public boolean addLigne(Article article, int qte) {
		
		if( qte <= 0 ){
			System.out.println("Ajout de ligne : Opération impossible ! - Quantité nulle ou négative");
			return false;
		} 
		if( article == null ) {
			System.out.println("Ajout de ligne : Opération impossible ! - Article non référencé");
			return false;
		}
		
		int indexLigneArticle = this.indexOfLigneArticle(article);
		if( indexLigneArticle == -1 ) {
			boolean addSuccess = false;
			 try{
				 addSuccess = this.lignes.add(new Ligne(qte, article));
			 } catch(UnsupportedOperationException e) {
				 System.out.println("Ajout de ligne : Opération impossible !");
			 } catch(ClassCastException e) {
				 System.out.println("Ajout de ligne : Opération impossible ! Ce n'est pas un article.");
			 } catch(NullPointerException e) {
				 System.out.println("Ajout de ligne : Opération impossible ! Object nul.");
			 } catch(IllegalArgumentException e) {
				 System.out.println("Ajout de ligne : Opération impossible ! Ce n'est pas un article.");
			 }
			 return addSuccess;
		} else {
			this.lignes.get(indexLigneArticle).deltaQuantite(qte);
			return true;
		}
	}
	
	/**
	 * Supression d'une ligne du panier
	 * @param index
	 * @return success de la suppression
	 */
	public boolean removeLigne(int index) {
		if(index < 0 || index >= this.lignes.size()) {
			System.out.println("Suppression de ligne : Opération impossible ! - Index hors-limite");
			return false;
		} else {
			try {
				this.lignes.remove(index);
				return true;
			} catch(UnsupportedOperationException e){
				System.out.println("Suppression de ligne : Opération impossible !");
				return false;
			} catch(IndexOutOfBoundsException e) {
				System.out.println("Suppression de ligne : Opération impossible ! - Index hors-limite");
				return false;
			}
		}
	}
	
	/**
	 * Modification de la quantité d'une ligne du panier
	 * @param index
	 * @param newQte nouvelle quantité
	 * @return success de la modification
	 */
	public boolean updateLigne(int index, int newQte){
		if(index < 0 || index >= this.lignes.size()) {
			System.out.println("Mise à jour de ligne : Opération impossible ! - Index hors-limite");
			return false;
		} else if( newQte < 0 ){
			System.out.println("Mise à jour de ligne : Opération impossible ! - Quantité négative");
			return false;
		} else if( newQte == 0 ){
			return this.removeLigne(index);
		} else {
			try {
				this.lignes.get(index).setQte(newQte);
				return true;
			} catch(IndexOutOfBoundsException e) {
				System.out.println("Mise à jour de ligne : Opération impossible ! - Index hors-limite");
				return false;
			}
		}
	}
	
	public String toString() {
		
		String output = String.format("%s:\n\n", this.getClass().getSimpleName());
		int maxLoop = this.lignes.size();
		for(int l = 0; l < maxLoop; l++) {
			output = output.concat( String.format("ligne %d : 	%s\n", l, this.lignes.get(l).toString()));
		}
		output = output.concat( String.format("Valeur du panier : %.2f\n", this.montant) );
		return output;
	}
	
	//----- PRIVATE

	private float montant = 0.0f;
	private List<Ligne> lignes;
	
	/**
	 * Retourne l'index (base 0) de la ligne où se trouve l'article, -1 si l'article n'est pas dans la liste.
	 * @param article
	 * @return index de la Ligne Article
	 */
	private int indexOfLigneArticle(Article article) {
		
		int maxLoop = this.lignes.size();
		for(int l = 0; l < maxLoop; l++) {
			if(this.lignes.get(l).getArticle() == article) {
				return l;
			}
		}
		return -1;
	}
}
