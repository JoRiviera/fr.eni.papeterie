package fr.eni.papeterie.ihm;

import javax.swing.SwingUtilities;

public class TestIHM {
	public static void main(String[] args){
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				EcranArticle ecran = new EcranArticle();
				ecran.setVisible(true);
			}
		});

	}
}
