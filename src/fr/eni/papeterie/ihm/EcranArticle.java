package fr.eni.papeterie.ihm;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.NumberFormat;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import fr.eni.papeterie.dal.ArticleDao;
import fr.eni.papeterie.dal.DALException;
import fr.eni.papeterie.dal.DaoFactory;

public class EcranArticle extends JFrame {

	// ----- STATICS
	
	private static final long serialVersionUID = 1L;
	
	static final int WIDTH = 300;
	static final int HEIGHT = 500;
	static final String TITRE ="Vue Article";
	
	static final int TXT_WIDTH = 20;
	static final int[] GRAMMAGES = {80, 100};
	
	static final boolean AUTORISATION_AJ_COULEUR = true;
	
	// ----- PUBLIC
	
	public EcranArticle() {
		super(TITRE);
		this.chargerMainPanel();
		this.setContentPane(mainPanel);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(new Dimension(EcranArticle.WIDTH, EcranArticle.HEIGHT));
	}
	
	/**
	 * Champ Référence article
	 * @return JTextField
	 */
	public JTextField getTxtReference() {
		if( this.txtReference == null) {
			this.txtReference = new JTextField();
		}
		return this.txtReference;
	}
	
	/**
	 * Champ Désignation article
	 * @return JTextField
	 */
	public JTextField getTxtDesignation() {
		if( this.txtDesignation == null) {
			this.txtDesignation = new JTextField();
		}
		return this.txtDesignation;
	}
	
	/**
	 * Champ Marque article
	 * @return JTextField
	 */
	public JTextField getTxtMarque() {
		if( this.txtMarque == null) {
			this.txtMarque = new JTextField();
		}
		return this.txtMarque;
	}
	
	/**
	 * Champ Stock article, format integer
	 * @return JFormmattedTextField
	 */
	public JFormattedTextField getTxtStock() {
		if( this.txtStock == null) {
			this.txtStock = new JFormattedTextField(NumberFormat.getIntegerInstance());
		}
		return this.txtStock;
	}
	
	/**
	 * Champ Prix Article, format nombre Max 2 Fraction Digits
	 * @return JFormattedTextField
	 */
	public JFormattedTextField getTxtPrix() {
		if(this.txtPrix == null) {
			NumberFormat formatPrix = NumberFormat.getNumberInstance();
			formatPrix.setMaximumFractionDigits(2);
			this.txtPrix = new JFormattedTextField(formatPrix);
		}
		return this.txtPrix;
	}
	
	// ----- PRIVATE
	

	private JPanel mainPanel;
	
	/**
	 * Chargement des éléments dans le Panel Principal
	 */
	private void chargerMainPanel() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
		mainPanel.setSize(WIDTH, HEIGHT);
		chargerAttributs();
		mainPanel.add(panelAttributs);
		chargerBarreAction();
		mainPanel.add(barreAction);
	}
	
	// Attributs article
	private JPanel panelAttributs;
	
	private JTextField txtReference;
	private JTextField txtMarque;
	private JTextField txtDesignation;
	private JLabel lbReference;
	private JLabel lbMarque;
	private JLabel lbDesignation;
	
	private JFormattedTextField txtStock;
	private JLabel lbStock;
	
	private JFormattedTextField txtPrix;
	private JLabel lbPrix;
	
	private JLabel lbType;
	private JPanel pnlType;
	private ButtonGroup btnGroupType;
	private JRadioButton typeStylo;
	private JRadioButton typeRamette;

	private JLabel lbGrammage;
	private JPanel pnlGrammage;
	private JCheckBox[] grammage_box;
	
	private JLabel lbCouleur;
	private JComboBox<String> cboxCouleur;

	
	/**
	 * Chargement des éléments dans le Panel des Attributs
	 */
	private void chargerAttributs() {
		
		panelAttributs = new JPanel();
		
		Map<JLabel, JComponent> attributs = new LinkedHashMap<>();

		this.lbReference = new JLabel("Reference");
		this.txtReference = this.getTxtReference();
		this.lbReference.setLabelFor(txtReference);
		attributs.put(this.lbReference, this.getTxtReference());
		
		this.lbDesignation = new JLabel("Designation");
		this.txtDesignation = this.getTxtDesignation();
		this.lbDesignation.setLabelFor(txtDesignation);
		attributs.put(this.lbDesignation, this.getTxtDesignation());
		
		this.lbMarque = new JLabel("Marque");
		this.txtMarque = this.getTxtMarque();
		this.lbMarque.setLabelFor(txtMarque);
		attributs.put(this.lbMarque, this.getTxtMarque());

		this.lbStock = new JLabel("Stock");
		this.txtStock = this.getTxtStock();
		this.lbStock.setLabelFor(txtStock);
		attributs.put(this.lbStock, this.getTxtStock());
		
		this.lbPrix = new JLabel("Prix");
		this.lbPrix.setLabelFor(this.txtPrix);
		attributs.put(this.lbPrix, this.getTxtPrix());
		
		this.lbType = new JLabel("Type");
		this.pnlType = new JPanel();
		this.pnlType.setLayout(new BoxLayout(this.pnlType, BoxLayout.PAGE_AXIS));
		this.btnGroupType = new ButtonGroup();
		this.typeStylo = new JRadioButton("Stylo");
		this.btnGroupType.add(this.typeStylo);
		this.pnlType.add(this.typeStylo);
		this.typeRamette = new JRadioButton("Ramette");
		this.btnGroupType.add(this.typeRamette);
		this.pnlType.add(this.typeRamette);
		this.lbType.setLabelFor(this.pnlType);
		attributs.put(this.lbType, this.pnlType);
		
		// Grammage
		this.lbGrammage = new JLabel("Grammage");
		this.pnlGrammage = new JPanel();
		this.pnlGrammage.setLayout(new BoxLayout(this.pnlGrammage, BoxLayout.PAGE_AXIS));
		this.grammage_box = new JCheckBox[GRAMMAGES.length];
		for(int i = 0; i < EcranArticle.GRAMMAGES.length; i++) {
			this.grammage_box[i] = new JCheckBox(EcranArticle.GRAMMAGES[i] + "g/m²");
			this.pnlGrammage.add(this.grammage_box[i]);
		};
		this.lbGrammage.setLabelFor(this.pnlGrammage);
		attributs.put(this.lbGrammage, this.pnlGrammage);
		
		// Couleur
		this.lbCouleur = new JLabel("Couleur");
		try {
			ArticleDao articleDao = DaoFactory.getArticleDao();
			String[] couleurs = articleDao.selectCouleur();
			this.cboxCouleur = new JComboBox<String>(couleurs);
		} catch (DALException e) {
			e.printStackTrace();
			this.cboxCouleur = new JComboBox<String>();
		} finally {
			this.cboxCouleur.setEditable(EcranArticle.AUTORISATION_AJ_COULEUR);
		}
		this.lbCouleur.setLabelFor(this.cboxCouleur);
		attributs.put(this.lbCouleur, this.cboxCouleur);
		
		// Mise en place des élements dans le GridBagLayout
		panelAttributs.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5,5,5,5);
		gbc.fill = GridBagConstraints.BOTH;
		int y = -1;
		for(Entry<JLabel, JComponent> attribut : attributs.entrySet()) {
			JLabel label = attribut.getKey();
			JComponent bloc = attribut.getValue();
			y = y + 1;
			gbc.gridy = y;
			gbc.gridx = 0;
			panelAttributs.add(label, gbc);
			gbc.gridx = 1;
			panelAttributs.add(bloc, gbc);
		}
	}

	// Boutons d'action
	
	private JPanel barreAction = new JPanel();
	
	/**
	 * Chargement des éléments dans la barre d'action
	 */
	private void chargerBarreAction() {
		
	}
}
