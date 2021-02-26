package fr.eni.papeterie.dal;

import fr.eni.papeterie.dal.jdbc.ArticleDaoJdbcImpl;

public class DaoFactory {
	
	public static ArticleDao getArticleDao() {
		return new ArticleDaoJdbcImpl();
	}
}
