package fr.eni.papeterie.dal;

import java.util.List;

import fr.eni.papeterie.bo.Article;

/**
 * Développement d'une applicaion JAVA en couche
 * TP Papeterie
 * @author Jonathan CAMARA
 *	ENI - 2021
 *
 * ArticleDao.java
 */

/**
 * Interface DAO BO Article
 */

public interface ArticleDao {
	
	/**
	 * SELECT * FROM ARTICLES
	 * @return List<Article> liste des éléments de la table Articles
	 * @throws DALException
	 */
	public List<Article> selectAll() throws DALException;
	
	/**
	 * SELECT * FROM Articles WHERE marque=marque
	 * @param idArticle
	 * @return Article
	 * @throws DALException
	 */
	public List<Article> selectByMarque(String marque) throws DALException;
	
	/**
	 * SELECT * FROM Articles WHERE marque LIKE '%'+ motcle + '%' OR designation LIKE '%'+ motcle + '%'
	 * @param idArticle
	 * @return Article
	 * @throws DALException
	 */
	public List<Article> selectByMotCle(String motCle) throws DALException;
	
	/**
	 * SELECT * FROM Articles WHERE id=idArticle
	 * @param idArticle
	 * @return Article
	 * @throws DALException
	 */
	public Article selectById(int id) throws DALException;
	
	/**
	 * INSERT INTO Articles, met l'idArticle à jour avec l'id généré par la BDD
	 * @param a Article
	 * @return int id généré par la BDD
	 * @throws DALException
	 */
	public int insert(Article a) throws DALException;
	
	/**
	 * UPDATE Articles SET ... 
	 * @param a Article
	 * @return boolean success
	 * @throws DALException
	 */
	public boolean update(Article a) throws DALException;
	
	/**
	 * DELETE FROM Articles WHERE idArticle=
	 * @param idArticle
	 * @return boolean success (any element deleted)
	 * @throws DALException
	 */
	public boolean delete(int idArticle) throws DALException;
	
	/**
	 * DELETE FROM Articles
	 * @return boolean success (any element deleted)
	 * @throws DALException
	 */
	public boolean deleteAll() throws DALException;
	
	/**
	 * SELECT couleur FROM Articles WHERE couleur NOT NULL GROUP BY couleur 
	 * @return
	 * @throws DALException
	 */
	public String[] selectCouleur() throws DALException;
}