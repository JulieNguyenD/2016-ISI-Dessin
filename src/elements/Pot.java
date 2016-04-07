package elements;

import java.awt.Color;
import java.awt.geom.Point2D;

import fr.lri.swingstates.canvas.CImage;
import fr.lri.swingstates.canvas.Canvas;

/**
 * <b>CImage pour le pot</b>
 * <p>Le pot a sa propre CStateMachine. 
 * Lorsque l'on crosse le pot, sa fonction est activée 
 * et une boîte apparaît pour choisir la couleur.</p>
 * 
 * @see CImage
 * 
 * @author ANDRIANIRINA Tojo
 * @author GABRIEL Damien
 * @author NGUYEN Julie
 */
public class Pot extends CImage {
	
	/**
	 * La couleur du pot. 
	 * @see Color
	 */
	private Color couleurPot;
		
	/**
	 * Booléen indiquant si le pot est actif.
	 */
	private boolean estActif;
	
	/**
	 * Le canvas sur lequel on ajoute la CStateMachine
	 * @see Canvas
	 */
	private Canvas canvas;

	/**
	 * Constructeur de Pot.
	 * <p>A la création d'un Pot, la couleur de base est blanc.
	 * Le pot n'est pas actif.<br/>
	 * On instancie le canvas et on attache le pot au Canvas.</p> 
	 * 
	 * @param path : Le chemin vers l'image.
	 * @param position : La position de départ de l'image (ici le coin supérieur gauche de l'image)
	 */
	public Pot(String path, Point2D position, Canvas canvas) {
		super(path, position);
		this.couleurPot = Color.WHITE;
		this.estActif = false;
		this.canvas = canvas;
		
		this.addTo(canvas);	
		this.addTag("pot");
	}
	
	/**
	 * Getter de couleurPot : retourne la couleur du pot. 
	 * @see Color
	 * @return la couleur du Pot
	 */
	public Color getCouleurPot() {
		return couleurPot;
	}

	/**
	 * Met à jour la couleur du pot avec la nouvelle couleur. 
	 * @param couleurPot : la nouvelle couleur du pot
	 */
	public void setCouleurPot(Color couleurPot) {
		this.couleurPot = couleurPot;
	}
	
	/**
	 * Retourne si le Pot est actif ou non.
	 * @return true si le Pot est actif, false sinon.
	 */
	public boolean isEstActif() {
		return estActif;
	}

	/**
	 * Met à jour l'état du Pot. S'il est actif, on met à true ; sinon false.
	 * @param estActif : le nouvel état du Pot, false ou true.
	 */
	public void setEstActif(boolean estActif) {
		this.estActif = estActif;
	}

}
