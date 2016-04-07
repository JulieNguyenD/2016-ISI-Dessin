package elements;

import java.awt.geom.Point2D;

import fr.lri.swingstates.canvas.CImage;
import fr.lri.swingstates.canvas.Canvas;

/**
 * <b>CImage pour la gomme</b>
 * <p>La gomme a sa propre CStateMachine. 
 * Lorsque l'on crosse la gomme, sa fonction est activée 
 * et une boîte apparaît pour choisir sa fonction.</p>
 * 
 * @see CImage
 * 
 * @author ANDRIANIRINA Tojo
 * @author GABRIEL Damien
 * @author NGUYEN Julie
 */
public class Gomme extends CImage {
	
	/**
	 * Booléen indiquant si la gomme est actif.
	 */
	private boolean estActif;
	
	/**
	 * Le canvas sur lequel on ajoute la CStateMachine
	 * @see Canvas
	 */
	private Canvas canvas;

	/**
	 * Constructeur de Gomme.
	 * <p>A la création d'une Gomme.
	 * Le gomme n'est pas actif.<br/>
	 * On instancie le canvas et on attache le gomme au Canvas.</p> 
	 * 
	 * @param path : Le chemin vers l'image.
	 * @param position : La position de départ de l'image (ici le coin supérieur gauche de l'image)
	 */
	public Gomme(String path, Point2D position, Canvas canvas) {
		super(path, position);
		this.estActif = false;
		this.canvas = canvas;
		
		this.addTo(canvas);	
		this.addTag("gomme");
	}
	
	/**
	 * Retourne si la Gomme est actif ou non.
	 * @return true si la Gomme est actif, false sinon.
	 */
	public boolean isEstActif() {
		return estActif;
	}

	/**
	 * Met à jour l'état du Gomme. S'il est actif, on met � true ; sinon false.
	 * @param estActif : le nouvel état de la Gomme, false ou true.
	 */
	public void setEstActif(boolean estActif) {
		this.estActif = estActif;
	}
}