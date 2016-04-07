package widgets.widget_sous_barre;

import java.awt.BasicStroke;
import java.awt.geom.Point2D;

import fr.lri.swingstates.canvas.CRectangle;
import fr.lri.swingstates.canvas.Canvas;

/**
 * <b>CRectangle pour le Choix du Pinceau </b>
 * <p>C'est un CRectangle qui possède les choix pour le pinceau.</p>
 * 
 * @see CRectangle
 * 
 * @author ANDRIANIRINA Tojo
 * @author GABRIEL Damien
 * @author NGUYEN Julie
 */
public class ChoixPinceau extends CRectangle {
	
	/**
	 * Canvas sur lequel on dessine.
	 */
	private Canvas canvas;
	
	/**
	 * Les choix pour la couleur du pinceau
	 * @see Couleur_Widget
	 */
	private Couleur_Widget choixPinceauCouleur;
	
	/**
	 * Les choix pour la taille du pinceau
	 * @see Taille_Widget
	 */
	private Taille_Widget choixPinceauTaille;
	
	/**
	 * Le point de départ du widget annexe.
	 */
	private Point2D position_widget;
	
	/**
	 * Constructeur de ChoixPinceau. Au départ, il n'est pas visible.
	 * @param canvas : canvas sur lequel on dessine, ajoute les éléments.
	 * @param position : position de départ du widget annexe.
	 */
	public ChoixPinceau(Canvas canvas, Point2D position) {
		super(position.getX()-70*2-10*2, position.getY(), 70*2, 290);
		this.canvas = canvas;
		
		position_widget = new Point2D.Double(position.getX()-70*2-10*2, position.getY());
		
		this.canvas = canvas;		
		this.addTo(canvas);		
		
		this.setStroke(new BasicStroke(1));
		
		this.addTag("choixPinceau");
		
		choixPinceauCouleur = new Couleur_Widget(canvas, position_widget);
		choixPinceauTaille = new Taille_Widget(canvas, position_widget);
		choixPinceauTaille.translateBy(70, 0);
		
		choixPinceauCouleur.setParent(this);
		choixPinceauTaille.setParent(this);
		
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
		this.choixPinceauTaille.montrer(b);
	}
}
