package fr.eni.papeterie.bll;

import java.util.List;

import fr.eni.papeterie.bo.Article;
import fr.eni.papeterie.bo.Ramette;
import fr.eni.papeterie.bo.Stylo;
import fr.eni.papeterie.dal.ArticleDao;
import fr.eni.papeterie.dal.DALException;
import fr.eni.papeterie.dal.DaoFactory;

public class CatalogueManager {
	
	//----- STATICS
	
	public static CatalogueManager getInstance() throws BLLException {
		if(manager == null) {
			manager = new CatalogueManager();
		}
		return manager;
	}
	
	private static CatalogueManager manager = null;
	
	//----- PUBLIC
	
	public void addArticle(Article a) throws BLLException {
		try {
			validerArticle(a);
			daoArticle.insert(a);
		} catch (DALException e) {
			throw new BLLException("Ajouter article / Echec / ", e);
		}
	}

	public List<Article> getCatalogue() throws BLLException {
		try {
			return daoArticle.selectAll();
		} catch (DALException e) {
			throw new BLLException("Catalogue article / Echec / ", e);
		}
	}

	public Article getArticle(int id) throws BLLException {
		try {
			return daoArticle.selectById(id);
		} catch (DALException e) {
			throw new BLLException("Obtenir article / Echec / ", e);
		}
	}
	
	public void updateArticle(Article a) throws BLLException {
		try {
			validerArticle(a);
			daoArticle.update(a);
		} catch (DALException e) {
			throw new BLLException("Mise à jour article / Echec / ", e);
		}
	}

	public void removeArticle(int index) throws BLLException {
		try {
			daoArticle.delete(index);
		} catch (DALException e) {
			throw new BLLException("Supprimer article / Echec / ", e);
		}
	}
	
	//----- PRIVATE
	private ArticleDao daoArticle = DaoFactory.getArticleDao();
	
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
