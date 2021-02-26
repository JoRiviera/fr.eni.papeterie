package fr.eni.papeterie.dal;

import fr.eni.papeterie.dal.jdbc.ArticleDaoJdbcImpl;

/**
 * Développement d'une applicaion JAVA en couche
 * TP Papeterie
 * @author Jonathan CAMARA
 *	ENI - 2021
 *
 * DaoFactory.java
 */

public class DaoFactory {
	
	public static ArticleDao getArticleDao() {
		return new ArticleDaoJdbcImpl();
	}
}
