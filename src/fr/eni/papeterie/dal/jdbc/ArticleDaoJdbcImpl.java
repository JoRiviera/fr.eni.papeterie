package fr.eni.papeterie.dal.jdbc;

import fr.eni.papeterie.bo.Article;
import fr.eni.papeterie.bo.Stylo;
import fr.eni.papeterie.dal.ArticleDao;
import fr.eni.papeterie.dal.DALException;
import fr.eni.papeterie.bo.Ramette;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 * Développement d'une applicaion JAVA en couche
 * TP Papeterie
 * @author Jonathan CAMARA
 *	ENI - 2021
 *
 * ArticleDaoJdbcImpl.java
 */

/**
 * Implémentation du driver JDBC - Couche DAL BO Article
 */

public class ArticleDaoJdbcImpl implements ArticleDao {
	
	// ----- STATICS
	
	// REQUETES PREPAREES

	private final static String sqlSelectById = "SELECT * FROM Articles WHERE idArticle = ?";
	private final static String sqlSelectByMarque = "SELECT * FROM Articles WHERE marque = ?";
	private final static String sqlSelectByMotCle = "SELECT * FROM Articles WHERE marque LIKE ? OR designation LIKE ? ";
	private final static String sqlSelectAll = "SELECT * FROM Articles";
	
	private final static String sqlUpdate = "UPDATE Articles SET reference=?,marque=?,designation=?,prixUnitaire=?,qteStock=?,grammage=?,couleur=? "
											+ "WHERE idArticle=?";
	private final static String sqlInsert = "INSERT INTO Articles(reference, marque, designation, prixUnitaire, qteStock, grammage, couleur, type) "
											+ "VALUES(?,?,?,?,?,?,?,?)";
	private final static String sqlDelete =  "DELETE FROM Articles WHERE idArticle = ?";
	
	private final static String sqlCouleur = "SELECT couleur FROM Articles"
											+ " WHERE couleur IS NOT NULL"
											+ " GROUP BY couleur";
	// Types d'articles
	private final static String STYLO = "stylo";
	private final static String RAMETTE = "ramette";
	
	// ------ PUBLIC
	
	public ArticleDaoJdbcImpl() {
		super();
	}
	
	@Override
	public List<Article> selectAll() throws DALException {
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<Article> liste = new ArrayList<>();
		try {
			connection = JdbcTools.getConnection();
			stmt = connection.createStatement();
			rs = stmt.executeQuery(sqlSelectAll);
			while( rs.next() ) {
				String type = rs.getString("type").trim();
				if( type.equalsIgnoreCase(STYLO) ) {
					liste.add(new Stylo( rs.getInt("idArticle"),rs.getString("marque"), rs.getString("reference"), rs.getString("designation"), rs.getFloat("prixUnitaire"), rs.getInt("qteStock"), rs.getString("couleur")));
				}
				if( type.equalsIgnoreCase(RAMETTE) ) {
					liste.add(new Ramette( rs.getInt("idArticle"),rs.getString("marque"), rs.getString("reference"), rs.getString("designation"), rs.getFloat("prixUnitaire"), rs.getInt("qteStock"), rs.getInt("grammage")));
				}
			}
		} catch(SQLException e) {
			throw new DALException("selectAll a échoué : ", e);
		} finally {
			if(stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					throw new DALException("Echec de la fermeture : ", e);
				}
			}
		}
		JdbcTools.closeConnection();
		return liste;
	}
	
	@Override
	public List<Article> selectByMarque(String marque) throws DALException {
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Article> liste = new ArrayList<>();
		try {
			connection = JdbcTools.getConnection();
			stmt = connection.prepareStatement(sqlSelectByMarque);
			stmt.setString(1, marque);
			rs = stmt.executeQuery();
			if( rs.next() ) {
				String type = rs.getString("type").trim();
				if( type.equalsIgnoreCase(STYLO) ) {
					liste.add(new Stylo( rs.getInt("idArticle"),rs.getString("marque"), rs.getString("reference"), rs.getString("designation"), rs.getFloat("prixUnitaire"), rs.getInt("qteStock"), rs.getString("couleur")));
				}
				if( type.equalsIgnoreCase(RAMETTE) ) {
					liste.add(new Ramette( rs.getInt("idArticle"),rs.getString("marque"), rs.getString("reference"), rs.getString("designation"), rs.getFloat("prixUnitaire"), rs.getInt("qteStock"), rs.getInt("grammage"))) ;
				}
			}
		} catch(SQLException e) {
			throw new DALException("selectById a échoué : ", e);
		} finally {
			if(stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					throw new DALException("Echec de la fermeture : ", e);
				}
			}
		}
		JdbcTools.closeConnection();
		return liste;
	}

	@Override
	public List<Article> selectByMotCle(String motCle) throws DALException {
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Article> liste = new ArrayList<>();
		try {
			connection = JdbcTools.getConnection();
			stmt = connection.prepareStatement(sqlSelectByMotCle);
			stmt.setString(1, "%" + motCle + "%");
			stmt.setString(2, "%" + motCle + "%");
			rs = stmt.executeQuery();
			if( rs.next() ) {
				String type = rs.getString("type").trim();
				if( type.equalsIgnoreCase(STYLO) ) {
					liste.add(new Stylo( rs.getInt("idArticle"),rs.getString("marque"), rs.getString("reference"), rs.getString("designation"), rs.getFloat("prixUnitaire"), rs.getInt("qteStock"), rs.getString("couleur")));
				}
				if( type.equalsIgnoreCase(RAMETTE) ) {
					liste.add(new Ramette( rs.getInt("idArticle"),rs.getString("marque"), rs.getString("reference"), rs.getString("designation"), rs.getFloat("prixUnitaire"), rs.getInt("qteStock"), rs.getInt("grammage"))) ;
				}
			}
		} catch(SQLException e) {
			throw new DALException("selectById a échoué : ", e);
		} finally {
			if(stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					throw new DALException("Echec de la fermeture : ", e);
				}
			}
		}
		JdbcTools.closeConnection();
		return liste;
	}
	
	@Override
	public Article selectById(int idArticle) throws DALException {
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Article article = null;
		try {
			connection = JdbcTools.getConnection();
			stmt = connection.prepareStatement(sqlSelectById);
			stmt.setInt(1, idArticle);
			rs = stmt.executeQuery();
			if( rs.next() ) {
				String type = rs.getString("type").trim();
				if( type.equalsIgnoreCase(STYLO) ) {
					article = new Stylo( rs.getInt("idArticle"),rs.getString("marque"), rs.getString("reference"), rs.getString("designation"), rs.getFloat("prixUnitaire"), rs.getInt("qteStock"), rs.getString("couleur"));
				}
				if( type.equalsIgnoreCase(RAMETTE) ) {
					article = new Ramette( rs.getInt("idArticle"),rs.getString("marque"), rs.getString("reference"), rs.getString("designation"), rs.getFloat("prixUnitaire"), rs.getInt("qteStock"), rs.getInt("grammage"));
				}
			}
		} catch(SQLException e) {
			throw new DALException("selectById a échoué : ", e);
		} finally {
			if(stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					throw new DALException("Echec de la fermeture : ", e);
				}
			}
		}
		JdbcTools.closeConnection();
		return article;
	}

	@Override
	public int insert(Article a) throws DALException {
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int idArticle = -1;
		try {
			connection = JdbcTools.getConnection();
			stmt = connection.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
			
			stmt.setString(1, a.getReference());
			stmt.setString(2, a.getMarque());
			stmt.setString(3, a.getDesignation());
			stmt.setFloat(4, a.getPrixUnitaire());
			stmt.setInt(5, a.getQteStock());
			if( a instanceof Ramette) {
				stmt.setInt(6, ((Ramette) a).getGrammage());
				stmt.setNull(7, Types.NVARCHAR);
				stmt.setString(8, RAMETTE);
			}
			if( a instanceof Stylo) {
				stmt.setNull(6, Types.INTEGER);
				stmt.setString(7, ((Stylo) a).getCouleur() );
				stmt.setString(8, STYLO);
			}
			
			rs = stmt.executeQuery();
			if(rs.next()) {
				idArticle = rs.getInt("GENERATED_KEYS");
			}
			
		} catch(SQLException e) {
			throw new DALException("insert a échoué : ", e);
		} finally {
			if(stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					throw new DALException("Echec de la fermeture : ", e);
				}
			}
		}
		JdbcTools.closeConnection();
		a.setIdArticle(idArticle);
		return idArticle;
	}

	@Override
	public boolean update(Article a) throws DALException {
		Connection connection = null;
		PreparedStatement stmt = null;
		boolean success = false;
		try {
			connection = JdbcTools.getConnection();
			stmt = connection.prepareStatement(sqlUpdate);
			
			stmt.setString(1, a.getReference());
			stmt.setString(2, a.getMarque());
			stmt.setString(3, a.getDesignation());
			stmt.setFloat(4, a.getPrixUnitaire());
			stmt.setInt(5, a.getQteStock());
			if( a instanceof Ramette) {
				stmt.setInt(6, ((Ramette) a).getGrammage());
				stmt.setNull(7, Types.NVARCHAR);
			}
			if( a instanceof Stylo) {
				stmt.setNull(6, Types.INTEGER);
				stmt.setString(7, ((Stylo) a).getCouleur() );
			}
			stmt.setInt(8, a.getIdArticle());
			
			if( stmt.executeUpdate() > 0) {
				success = true;
			} else {
				success = false;
			}
			
		} catch(SQLException e) {
			throw new DALException("update a échoué : ", e);
		} finally {
			if(stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					throw new DALException("Echec de la fermeture : ", e);
				}
			}
		}
		JdbcTools.closeConnection();
		return success;
	}

	@Override
	public boolean delete(int idArticle) throws DALException {
		Connection connection = null;
		PreparedStatement stmt = null;
		boolean success = false;
		try {
			connection = JdbcTools.getConnection();
			stmt = connection.prepareStatement(sqlDelete);
			stmt.setInt(1, idArticle);
			
			if( stmt.executeUpdate() > 0) {
				success = true;
			} else {
				success = false;
			}
			
		} catch(SQLException e) {
			throw new DALException("delete a échoué : ", e);
		} finally {
			if(stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					throw new DALException("Echec de la fermeture : ", e);
				}
			}
		}
		JdbcTools.closeConnection();
		return success;
	}
	
	@Override
	public boolean deleteAll() throws DALException {
		Connection connection = null;
		Statement stmt = null;
		boolean success = false;
		
		try {
			connection = JdbcTools.getConnection();
			stmt = connection.createStatement();
			
			if( stmt.executeUpdate("DELETE FROM Articles") > 0) {
				success = true;
			} else {
				success = false;
			}
		} catch (SQLException e) {
			throw new DALException("delete a échoué : ", e);
		} finally {
			if(stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					throw new DALException("Echec de la fermeture : ", e);
				}
			}
		}
		JdbcTools.closeConnection();
		return success;
	}

	@Override
	public String[] selectCouleur() throws DALException {
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null; 
		List<String> couleurs = new ArrayList<>();
		try {
			connection = JdbcTools.getConnection();
			stmt = connection.createStatement();
			rs = stmt.executeQuery(sqlCouleur);
			while( rs.next() ) {
				couleurs.add(rs.getString("couleur"));
			}
		} catch(SQLException e) {
			throw new DALException("selectAll a échoué : ", e);
		} finally {
			if(stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					throw new DALException("Echec de la fermeture : ", e);
				}
			}
		}
		JdbcTools.closeConnection();
		return couleurs.toArray(new String[couleurs.size()]);
	}

}
