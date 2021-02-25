package fr.eni.papeterie.dal;


import java.io.IOException;

/**
 * Développement d'une applicaion JAVA en couche
 * TP Papeterie
 * @author Jonathan CAMARA
 *	ENI - 2021
 *
 * Settings.java
 */

/**
 * Externalisation de l'accès à la BDD
 */

import java.util.Properties;

public class Settings {
	
	//----- PUBLIC
	
	public static String getProperty(String key) {
		return proprietes.getProperty(key);
	}
	
	//----- PRIVATE
	
	private static Properties proprietes = null;
	
	//----- STATIC
	static {
		proprietes = new Properties();
		try {
			proprietes.load(Settings.class.getResourceAsStream("settings.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
