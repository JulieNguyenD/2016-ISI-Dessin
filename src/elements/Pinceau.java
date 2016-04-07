package elements;

import java.awt.Color;
import java.awt.geom.Point2D;

import fr.lri.swingstates.canvas.CImage;
import fr.lri.swingstates.canvas.Canvas;

/**
 * <b>CImage pour le pinceau</b>
 * <p>Le pinceau a sa propre CStateMachine. 
 * Lorsque l'on crosse le pinceau, sa fonction est activée 
 * et une boîte apparaît pour choisir la taille et la couleur.</p>
 * 
 * @see CImage
 * 
 * @author ANDRIANIRINA Tojo
 * @author GABRIEL Damien
 * @author NGUYEN Julie
 */
public class Pinceau extends CImage {
	
	/**
	 * La couleur du pinceau. 
	 * @see Color
	 */
	private Color couleurPinceau;
	
	/**
	 * La taille du pinceau.
	 */
	private int taille;
	
	/**
	 * Booléen indiquant si le pinceau est actif.
	 */
	private boolean estActif;
	
	/**
	 * Le canvas sur lequel on ajoute la CStateMachine
	 * @see Canvas
	 */
	private Canvas canvas;

	/**
	 * Constructeur de Pinceau.
	 * <p>A la création d'un Pinceau, la couleur de base est noir.
	 * La taille du trait est de 1 et le pinceau n'est pas actif.<br/>
	 * On instancie le canvas et on attache le pinceau au Canvas.</p> 
	 * 
	 * @param path : Le chemin vers l'image.
	 * @param position : La position de départ de l'image (ici le coin supérieur gauche de l'image)
	 */
	public Pinceau(String path, Point2D position, Canvas canvas) {
		super(path, position);
		this.couleurPinceau = Color.BLACK;
		this.taille = 1;
		this.estActif = false;
		this.canvas = canvas;
		
		this.addTo(canvas);
		this.addTag("pinceau");
	}
	
	/**
	 * Getter de couleurPinceau : retourne la couleur du pinceau. 
	 * @see Color
	 * @return la couleur du Pinceau
	 */
	public Color getCouleurPinceau() {
		return couleurPinceau;
	}

	/**
	 * Met à jour la couleur du Pinceau avec la nouvelle couleur. 
	 * @param couleurPinceau : la nouvelle couleur du Pinceau
	 */
	public void setCouleurPinceau(Color couleurPinceau) {
		this.couleurPinceau = couleurPinceau;
	}

	/**
	 * Retourne la taille du Pinceau
	 * @return la taille du Pinceau
	 */
	public int getTaille() {
		return taille;
	}

	/**
	 * Met à jour la taille du Pinceau avec la nouvelle taille.
	 * @param taille : la nouvelle taille du Pinceau
	 */
	public void setTaille(int taille) {
		this.taille = taille;
	}

	/**
	 * Retourne si le pinceau est actif ou non.
	 * @return true si le pinceau est actif, false sinon.
	 */
	public boolean isEstActif() {
		return estActif;
	}

	/**
	 * Met à jour l'état du Pinceau. S'il est actif, on met à true ; sinon false.
	 * @param estActif : le nouvel état du pinceau, false ou true.
	 */
	public void setEstActif(boolean estActif) {
		this.estActif = estActif;
	}	
}
