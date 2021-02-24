package fr.eni.papeterie.dal.jdbc;

import fr.eni.papeterie.bo.Article;
import fr.eni.papeterie.bo.Stylo;
import fr.eni.papeterie.dal.DALException;
import fr.eni.papeterie.bo.Ramette;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.microsoft.sqlserver.jdbc.SQLServerDriver;

/**
 * Développement d'une applicaion JAVA en couche
 * TP Papeterie
 * @author Jonathan CAMARA
 *	ENI - 2021
 *
 * ArticleDaoJdbcImpl.java
 */

/**
 * Implémentation du driver JDBC - Couche DAL
 * 
 * Le driver est chargé en mémoire avec une méthode static anonyme.
 * On ouvre et ferme la connexion à chaque appel de méthodes DML.
 */

public class ArticleDaoJdbcImpl {
	
	// ----- STATICS
	
	// Chargement du driver
	static {
		try {
			DriverManager.registerDriver(new SQLServerDriver());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// REQUETES PREPAREES

	private final static String sqlSelectById = "SELECT * FROM Articles WHERE idArticle = ?";
	private final static String sqlSelectAll = "SELECT * FROM Articles";
	
	private final static String sqlUpdate = "UPDATE Articles SET reference=?,marque=?,designation=?,prixUnitaire=?,qteStock=?,grammage=?,couleur=? "
											+ "WHERE idArticle=?";
	private final static String sqlInsert = "INSERT INTO Articles(reference, marque, designation, prixUnitaire, qteStock, grammage, couleur, type) "
											+ "VALUES(?,?,?,?,?,?,?,?)";
	private final static String sqlDelete =  "DELETE FROM Articles WHERE idArticle = ?";
	
	// Types d'articles
	private final static String STYLO = "stylo";
	private final static String RAMETTE = "ramette";
	
	// ------ PUBLIC
	
	public ArticleDaoJdbcImpl() {
		super();
	}
	
	/**
	 * SELECT * FROM ARTICLES
	 * @return List<Article> liste des éléments de la table Articles
	 * @throws DALException
	 */
	public List<Article> selectAll() throws DALException {
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<Article> liste = new ArrayList<>();
		try {
			connection = this.getConnection();
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
					e.printStackTrace();
				}
			}
		}
		this.closeConnection();
		return liste;
	}
	
	/**
	 * SELECT * FROM Articles WHERE id=idArticle
	 * @param idArticle
	 * @return Article
	 * @throws DALException
	 */
	public Article selectById(int idArticle) throws DALException {
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Article article = null;
		try {
			connection = this.getConnection();
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
					e.printStackTrace();
				}
			}
		}
		this.closeConnection();
		return article;
	}

	/**
	 * INSERT INTO Articles, met l'idArticle àjour avec l'id généré par la BDD
	 * @param a Article
	 * @return int id généré par la BDD
	 * @throws DALException
	 */
	public int insert(Article a) throws DALException {
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int idArticle = -1;
		try {
			connection = this.getConnection();
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
					e.printStackTrace();
				}
			}
		}
		this.closeConnection();
		a.setIdArticle(idArticle);
		return idArticle;
	}

	/**
	 * UPDATE Articles SET ... 
	 * @param a Article
	 * @return boolean success
	 * @throws DALException
	 */
	public boolean update(Article a) throws DALException {
		Connection connection = null;
		PreparedStatement stmt = null;
		boolean success = false;
		try {
			connection = this.getConnection();
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
					e.printStackTrace();
				}
			}
		}
		this.closeConnection();
		return success;
	}

	/**
	 * DELETE FROM Articles WHERE idArticle=
	 * @param idArticle
	 * @return boolean success
	 * @throws DALException
	 */
	public boolean delete(int idArticle) throws DALException {
		Connection connection = null;
		PreparedStatement stmt = null;
		boolean success = false;
		try {
			connection = this.getConnection();
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
					e.printStackTrace();
				}
			}
		}
		this.closeConnection();
		return success;
	}
	
	public boolean deleteAll() throws DALException {
		Connection connection = null;
		Statement stmt = null;
		boolean success = false;
		
		try {
			connection = this.getConnection();
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
					e.printStackTrace();
				}
			}
		}
		this.closeConnection();
		return success;
	}
	
	// ----- PRIVATE
	private Connection con = null;
	private final String conUrl = "jdbc:sqlserver://localhost:1433;dataBaseName=PAPETERIE_DB";
	private final String user = "SA";
	private final String pw = "<P@ssw0rd";
	
	/**
	 * Ouvre la connection au serveur
	 * @return Connection object pour envoyer la requêe
	 */
	private Connection getConnection() {
		if(this.con == null) {
			try {
				this.con = DriverManager.getConnection(this.conUrl, this.user, this.pw);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return this.con;
	}
	
	/**
	 * Ferme la connection en cours à la BDD.
	 */
	private void closeConnection() {
		if(this.con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.con = null;
	}

}
