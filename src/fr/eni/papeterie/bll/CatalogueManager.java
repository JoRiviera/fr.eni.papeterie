package fr.eni.papeterie.bll;

import java.util.List;

import fr.eni.papeterie.bo.Article;
import fr.eni.papeterie.bo.Ramette;
import fr.eni.papeterie.bo.Stylo;
import fr.eni.papeterie.dal.ArticleDao;
import fr.eni.papeterie.dal.DALException;
import fr.eni.papeterie.dal.DaoFactory;

/**
 * Développement d'une applicaion JAVA en couche
 * TP Papeterie
 * @author Jonathan CAMARA
 *	ENI - 2021
 *
 * CatalogueManager.java
 */

/**
 * Traitement des BO vers la DAL
 */

public class CatalogueManager {
	
	//----- STATICS
	
	/**
	 * Singleton
	 * @return CatalogueManager
	 * @throws BLLException
	 */
	public static CatalogueManager getInstance() throws BLLException {
		if(manager == null) {
			manager = new CatalogueManager();
		}
		return manager;
	}
	
	private static CatalogueManager manager = null;
	
	//----- PUBLIC
	
	/**
	 * Ajout article en BDD (après validation des champs)
	 * @param a Article
	 * @throws BLLException
	 */
	public void addArticle(Article a) throws BLLException {
		try {
			validerArticle(a);
			daoArticle.insert(a);
		} catch (DALException e) {
			throw new BLLException("Ajouter article / Echec / ", e);
		}
	}
	
	/**
	 * Liste des articles en BDD
	 * @return List<Article>
	 * @throws BLLException
	 */
	public List<Article> getCatalogue() throws BLLException {
		try {
			return daoArticle.selectAll();
		} catch (DALException e) {
			throw new BLLException("Catalogue article / Echec / ", e);
		}
	}
	
	/**
	 * Retour d'un article des la BDD avec son id
	 * @param idArticle
	 * @return Article
	 * @throws BLLException
	 */
	public Article getArticle(int id) throws BLLException {
		try {
			return daoArticle.selectById(id);
		} catch (DALException e) {
			throw new BLLException("Obtenir article / Echec / ", e);
		}
	}
	
	/**
	 * Mise à jour article dans la BDD (après validation des champs)
	 * @param a Article
	 * @throws BLLException
	 */
	public void updateArticle(Article a) throws BLLException {
		try {
			validerArticle(a);
			daoArticle.update(a);
		} catch (DALException e) {
			throw new BLLException("Mise à jour article / Echec / ", e);
		}
	}

	/**
	 * Suppression d'un article en BDD avec son id
	 * @param index int
	 * @throws BLLException
	 */
	public void removeArticle(int index) throws BLLException {
		try {
			daoArticle.delete(index);
		} catch (DALException e) {
			throw new BLLException("Supprimer article / Echec / ", e);
		}
	}
	
	//----- PRIVATE
	private ArticleDao daoArticle = DaoFactory.getArticleDao();
	
	/**
	 * Validation des champs de l'article avant insertion en BDD
	 * @param a Article
	 * @throws BLLException
	 */
	private void validerArticle(Article a) throws BLLException {
		if( a.getIdArticle() < 0  ) {
			throw new BLLException("Champ Id Article Invalide.");
		}
		if( a.getReference() == null || a.getReference().equals("") ){
			throw new BLLException("Champ Référence Article Invalide.");
		}
		if( a.getMarque() == null || a.getMarque().equals("")) {
			throw new BLLException("Champ Marque Invalide.");
		}
		if( a.getDesignation() == null || a.getDesignation().equals("")) {
			throw new BLLException("Champ Id Designation Invalide.");
		}
		if( a.getPrixUnitaire() <= 0f  ) {
			throw new BLLException("Champ Prix Unitaire Invalide.");
		}
		if( a.getQteStock() < 0  ) {
			throw new BLLException("Champ Stock Invalide.");
		}
		
		if( a instanceof Stylo) {
			if( ((Stylo) a).getCouleur() == null || ((Stylo) a).getCouleur().equals("")) {
				throw new BLLException("Champ Couleur Invalide.");
			}
		}
		if( a instanceof Ramette) {
			if( ((Ramette) a).getGrammage() <= 0) {
				throw new BLLException("Champ Grammage Invalide.");
			}
		}
	}
	
	private CatalogueManager() {
		super();
	}
	
}
