package elements;

import java.awt.BasicStroke;
import java.awt.Color;

import fr.lri.swingstates.canvas.CRectangle;
import fr.lri.swingstates.canvas.Canvas;

/**
 * <b>CRectangle pour la Taille </b>
 * <p>C'est un carré qui possède un CRectangle avec une certaine taille.</p>
 * 
 * @see CRectangle
 * 
 * @author ANDRIANIRINA Tojo
 * @author GABRIEL Damien
 * @author NGUYEN Julie
 */
public class Taille extends CRectangle {
	
	/**
	 * Le CRectangle avec la taille différente.
	 */
	private CRectangle rectangle;
	
	/**
	 * La taille du CRectangle rectangle.
	 */
	private double tailleThis;

	/**
	 * Constructeur de Taille.
	 * <p>A la création de Taille, on initialise le rectangle avec la taille.</p>
	 * @param taille : la taille du rectangle.
	 * @param canvas : le canvas sur lequel on le place.
	 */
	public Taille (final double taille, Canvas canvas){
		super (0, 0, 50, 50);
		rectangle = canvas.newRectangle(5, 25, 40, taille);
		rectangle.rotateBy(95.0);
		tailleThis = taille;
		
		rectangle.setStroke(new BasicStroke(0));
		rectangle.setFillPaint(Color.BLACK);
		rectangle.setPickable(false);
		this.setFillPaint(Color.WHITE);
				
		rectangle.setParent(this);
		rectangle.setPickable(false);
		this.addTo(canvas);		
		
		this.below(rectangle);			
		
		this.addTag("taille");
	}
	
	/**
	 * Getter de Taille
	 * @return tailleThis : la taille du rectangle.
	 */
	public double getTaille() {
		return this.tailleThis;
		
	}
	
	/**
	 * Fonction qui permet de montrer ou cacher la taille. On ne peut plus récupérer les états avec un CStateMachine.
	 * @param b : true si on veut montrer. False si on veut cacher.
	 */
	public void montrer(boolean b) {
		this.setDrawable(b);
		this.setPickable(b);
		this.rectangle.setDrawable(b);
	}
}
