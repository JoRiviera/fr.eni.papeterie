package fr.eni.papeterie.bll;

/**
 * Développement d'une applicaion JAVA en couche
 * TP Papeterie
 * @author ENI
 *	ENI - 2021
 *
 * BLLException.java
 */

/**
 * Exception niveau couche BLL
 */

public class BLLException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public BLLException() {
		super();
	}
	
	public BLLException(String message) {
		super(message);
	}
	
	public BLLException(String message, Throwable exception) {
		super(message, exception);
	}

	//Méthodes
	@Override
	public String getMessage() {
		StringBuffer sb = new StringBuffer("Couche DAL - ");
		sb.append(super.getMessage());
		
		return sb.toString() ;
	}

}
