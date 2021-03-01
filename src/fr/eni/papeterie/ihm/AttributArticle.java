package fr.eni.papeterie.ihm;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AttributArticle extends JPanel {
	
	public AttributArticle(JLabel label, JComponent composant) {
		super();
		label.setLabelFor(composant);
		this.add(label);
		this.add(composant);
		this.setLayout(getLayout());
	}
}
