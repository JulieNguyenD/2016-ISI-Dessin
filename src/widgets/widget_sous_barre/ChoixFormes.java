package widgets.widget_sous_barre;

import java.awt.BasicStroke;
import java.awt.geom.Point2D;

import fr.lri.swingstates.canvas.CRectangle;
import fr.lri.swingstates.canvas.Canvas;

/**
 * <b>CRectangle pour le Choix de la Forme.</b>
 * <p>C'est un CRectangle qui possède les choix pour la forme..</p>
 * 
 * @see CRectangle
 * 
 * @author ANDRIANIRINA Tojo
 * @author GABRIEL Damien
 * @author NGUYEN Julie
 */
public class ChoixFormes extends CRectangle {
	
	/**
	 * Canvas sur lequel on dessine.
	 */
	private Canvas canvas;
	
	/**
	 * Les choix de couleur du contour.
	 * @see Couleur_Widget
	 */
	private Couleur_Widget choixCouleurContour;
	
	/**
	 * Les choix de forme.
	 * @see Forme_Widget
	 */
	private Forme_Widget choixForme;
	
	/**
	 * La position du widget.
	 */
	private Point2D position_widget;

	/**
	 * Constructeur de ChoixFormes. Au départ, il n'est pas visible.
	 * @param canvas : canvas sur lequel on dessine, ajoute les éléments.
	 * @param position : position de départ du widget annexe.
	 */
	public ChoixFormes(Canvas canvas, Point2D position) {
		super(position.getX()-70*2-10*2, position.getY(), 70*2, 290);
		
		position_widget = new Point2D.Double(position.getX()-70*2-10*2, position.getY());

		this.canvas = canvas;	
		this.addTo(canvas);		
		
		this.setStroke(new BasicStroke(1));
		
		this.addTag("choixFormes");
		
		choixCouleurContour = new Couleur_Widget(canvas, position_widget);
		choixForme = new Forme_Widget(canvas, position_widget);
		choixForme.translateBy(70, 0);
		
		choixCouleurContour.setParent(this);
		choixForme.setParent(this);
		
		this.montrer(false);			
	}
	
	/**
	 * Fonction qui permet de montrer ou cacher le widget. On ne peut plus récupérer les états avec un CStateMachine.
	 * @param b : true si on veut montrer. False si on veut cacher.
	 */
	public void montrer(boolean b) {
		this.setDrawable(b);
		this.setPickable(b);
		this.choixCouleurContour.montrer(b);
		this.choixForme.montrer(b);
	}

}
