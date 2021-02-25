package fr.eni.papeterie.dal.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * settings.properties pour configurer la connexion à la bdd.
 */
import fr.eni.papeterie.dal.Settings;

/**
 * Développement d'une applicaion JAVA en couche
 * TP Papeterie
 * @author Jonathan CAMARA
 *	ENI - 2021
 *
 * JdbcTools.java
 */

/**
 * Implémentation du driver JDBC - Couche DAL BO Article
 */

public class JdbcTools {
	
		// Chargement du driver
		static {
			try {
				Class.forName(Settings.getProperty("driverjdbc"));
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// ----- PRIVATE
	
		private static Connection con = null;
		private static final String urldb = Settings.getProperty("url");
		private static final String userdb = Settings.getProperty("user");
		private static final String passworddb = Settings.getProperty("password");
		
		// ----- PUBLIC
		
		/**
		 * Ouvre la connection au serveur
		 * @return Connection object pour envoyer la requêe
		 */
		public static Connection getConnection() throws SQLException {
			if(JdbcTools.con == null) {
				try {
					JdbcTools.con = DriverManager.getConnection(JdbcTools.urldb, JdbcTools.userdb, JdbcTools.passworddb);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return JdbcTools.con;
		}
		
		/**
		 * Ferme la connection en cours à la BDD.
		 */
		public static void closeConnection() {
			if(JdbcTools.con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			JdbcTools.con = null;
		}
}
