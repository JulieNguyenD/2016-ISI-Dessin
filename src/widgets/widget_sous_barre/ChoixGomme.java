package widgets.widget_sous_barre;

import java.awt.BasicStroke;
import java.awt.geom.Point2D;

import fr.lri.swingstates.canvas.CRectangle;
import fr.lri.swingstates.canvas.Canvas;

/**
 * <b>CRectangle pour le Choix de la Gomme.</b>
 * <p>C'est un CRectangle qui possède les choix pour la Gomme..</p>
 * 
 * @see CRectangle
 * 
 * @author ANDRIANIRINA Tojo
 * @author GABRIEL Damien
 * @author NGUYEN Julie
 */
public class ChoixGomme extends CRectangle {
	
	/**
	 * La fonction de la Gomme
	 * Aurait du être dans la classe Gomme.
	 */
	private String fonction;
	
	/**
	 * Canvas sur lequel on dessine.
	 */
	private Canvas canvas;
	
	/**
	 * Les Choix de la gomme.
	 */
	private Gomme_Widget gommeWidget;
	
	/**
	 * La position de départ du widget annexe
	 */
	private Point2D position_widget;
	
	/**
	 * Constructeur de ChoixGomme. Au départ, il n'est pas visible.
	 * @param canvas : canvas sur lequel on dessine, ajoute les éléments.
	 * @param position : position de départ du widget annexe.
	 */
	public ChoixGomme(Canvas canvas, Point2D position) {
		super(position.getX()-70-10*2, position.getY(), 70, 125);
		
		position_widget = new Point2D.Double(position.getX()-70-10*2, position.getY());

		this.canvas = canvas;	
		this.addTo(canvas);		
		
		this.fonction = "pinceau";
		
		this.setStroke(new BasicStroke(1));
		
		this.addTag("choixGomme");
		
		gommeWidget = new Gomme_Widget(canvas, position_widget);
		
		gommeWidget.setParent(this);	
		
		this.montrer(false);
	}
	
	/**
	 * Getter de Fonction
	 * @return la fonction de la Gomme.
	 */
	public String getFonction() {
		return fonction;
	}

	/**
	 * Setter de fonction
	 * @param fonction : la nouvelle fonction de la Gomme.
	 */
	public void setFonction(String fonction) {
		this.fonction = fonction;
	}

	/**
	 * Fonction qui permet de montrer ou cacher le widget. On ne peut plus récupérer les états avec un CStateMachine.
	 * @param b : true si on veut montrer. False si on veut cacher.
	 */
	public void montrer(boolean b) {
		this.setDrawable(b);
		this.setPickable(b);
		this.gommeWidget.montrer(b);
	}

}
