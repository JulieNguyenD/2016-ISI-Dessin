package widgets.widget_sous_barre;

import java.awt.geom.Point2D;

import fr.lri.swingstates.canvas.CRectangle;
import fr.lri.swingstates.canvas.Canvas;

/**
 * <b>CRectangle pour le Choix du Pot </b>
 * <p>C'est un CRectangle qui possède les choix pour le pot.</p>
 * 
 * @see CRectangle
 * 
 * @author ANDRIANIRINA Tojo
 * @author GABRIEL Damien
 * @author NGUYEN Julie
 */
public class ChoixPot extends CRectangle {
	
	/**
	 * Canvas sur lequel on dessine.
	 */
	private Canvas canvas;
	
	/**
	 * Les choix de couleur du pot
	 */
	private Couleur_Widget choixPinceauCouleur;
	
	/**
	 * Le point de position du widget
	 */
	private Point2D position_widget;
	
	/**
	 * Constructeur de ChoixPot. Au départ, il n'est pas visible.
	 * @param canvas : canvas sur lequel on dessine, ajoute les éléments.
	 * @param position : position de départ du widget annexe.
	 */
	public ChoixPot(Canvas canvas, Point2D position) {
		super(position.getX()-70-10*2, position.getY(), 70, 290);
		
		position_widget = new Point2D.Double(position.getX()-70-10*2, position.getY());
		
		this.canvas = canvas;
		this.addTo(canvas);
		
		this.addTag("choixPot");
		
		choixPinceauCouleur = new Couleur_Widget(canvas, position_widget);

		choixPinceauCouleur.setParent(this);
		
		this.montrer(false);			
	}
	
	/**
	 * Fonction qui permet de montrer ou cacher le widget. On ne peut plus récupérer les états avec un CStateMachine.
	 * @param b : true si on veut montrer. False si on veut cacher.
	 */
	public void montrer(boolean b) {
		this.setDrawable(b);
		this.setPickable(b);
		this.choixPinceauCouleur.montrer(b);
	}

}
