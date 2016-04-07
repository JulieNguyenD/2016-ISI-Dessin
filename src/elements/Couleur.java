package elements;

import java.awt.BasicStroke;
import java.awt.Color;

import fr.lri.swingstates.canvas.CRectangle;
import fr.lri.swingstates.canvas.Canvas;

/**
 * <b>CRectangle pour la Couleur </b>
 * <p>C'est un carré qui possède un CRectangle avec dedans une couleur.</p>
 * 
 * @see CRectangle
 * 
 * @author ANDRIANIRINA Tojo
 * @author GABRIEL Damien
 * @author NGUYEN Julie
 */
public class Couleur extends CRectangle {
	
	/**
	 * Couleur dans le CRectangle
	 */
	private Color couleur;
	
	/**
	 * Le CRectangle que l'on remplie avec la couleur.
	 */
	private CRectangle rectangle;

	/**
	 * Constructeur de Couleur.
	 * <p>A la création de Couleur, on initialise le rectangle avec la couleur.</p>
	 * @param color : la couleur du CRectangle rectangle
	 * @param canvas : le canvas dans lequel on place Couleur.
	 */
	public Couleur (final Color color, Canvas canvas){
		super (0, 0, 50, 50);
		rectangle = canvas.newRectangle(5, 25, 40, 5);
		rectangle.rotateBy(95.0);	
		this.couleur = color;
		
		rectangle.setStroke(new BasicStroke(0));
		rectangle.setFillPaint(color);
		rectangle.setPickable(false);
		this.setFillPaint(Color.WHITE);		
		
		rectangle.setParent(this);
		rectangle.setPickable(false);
		this.addTo(canvas);	
		
		this.below(rectangle);
		
		this.addTag("couleur");

	}
	
	/**
	 * Getter de couleur
	 * @return la couleur de Couleur
	 */
	public Color getColor() {
		return this.couleur;		
	}
	
	/**
	 * Fonction qui permet de montrer ou cacher l'annexe. On ne peut plus récupérer les états avec un CStateMachine.
	 * @param b : true si on veut montrer. False si on veut cacher.
	 */
	public void montrer(boolean b) {
		this.setDrawable(b);
		this.setPickable(b);
		this.rectangle.setDrawable(b);
	}

}
