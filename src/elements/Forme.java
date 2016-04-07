package elements;

import java.awt.Color;
import java.awt.geom.Point2D;

import fr.lri.swingstates.canvas.CImage;
import fr.lri.swingstates.canvas.Canvas;

/**
 * <b>CImage pour la Forme</b>
 * <p>Cette forme est un outils du widget principal.<br/>
 * Lorsque l'on crosse la Forme, sa fonction est activée 
 * et une boîte apparaît pour choisir la taille et la couleur.</p>
 * 
 * @see CImage
 * 
 * @author ANDRIANIRINA Tojo
 * @author GABRIEL Damien
 * @author NGUYEN Julie
 */
public class Forme extends CImage {
	
	/**
	 * Booléen indiquant si la forme est actif.
	 */
	private boolean estActif;
	
	/**
	 * Le canvas sur lequel on ajoute la CStateMachine
	 * @see Canvas
	 */
	private Canvas canvas;
	
	/**
	 * La fonction de la Forme.
	 */
	private String fonction;
	
	/**
	 * La taille du contour de la forme.
	 */
	private int taille;
	
	/**
	 * La couleur du contour de la forme.
	 */
	private Color couleur;
	
	/**
	 * Constructeur de Forme.
	 * <p>A la création d'une Forme.
	 * Le forme n'est pas actif et est par défaut un rectangle.<br/>
	 * On instancie le canvas et on attache le forme au Canvas.</p> 
	 * 
	 * @param path : Le chemin vers l'image.
	 * @param position : La position de départ de l'image (ici le coin supérieur gauche de l'image)
	 */
	public Forme(String path, Point2D position, Canvas canvas) {
		super(path, position);
		this.canvas = canvas;		
		this.couleur = Color.black;
		this.fonction = "rectangle";
		this.taille = 1;
		this.estActif = false;
		
		this.addTo(canvas);

		this.addTag("forme");
	}
	
	/**
	 * Retourne si le Forme est actif ou non.
	 * @return true si le Forme est actif, false sinon.
	 */
	public boolean isEstActif() {
		return estActif;
	}

	/**
	 * Met à jour l'état du Forme. S'il est actif, on met à true ; sinon false.
	 * @param estActif : le nouveau état du Forme, false ou true.
	 */
	public void setEstActif(boolean estActif) {
		this.estActif = estActif;
	}
	
	/**
	 * Retourne la fonction de la forme.
	 * @return fonction : peut être rectangle, ellipse ou ligne.
	 */
	public String getFonction() {
		return fonction;
	}
	
	/**
	 * Met à jour la fonction de la Forme. 
	 * @param fonction : la nouvelle fonction de la forme.
	 */
	public void setFonction(String fonction) {
		this.fonction = fonction;
	}
	
	/**
	 * Retourne la taille de Forme.
	 * @return la taille du contour de la forme.
	 */
	public int getTaille(){
		return this.taille;
	}
	
	/**
	 * Met à jour la taille du Pinceau avec la nouvelle taille.
	 * @param taille : la nouvelle taille du Pinceau
	 */
	public void setTaille(int taille) {
		this.taille = taille;
	}
	
	/**
	 * Getter de couleur
	 * @return couleur : la couleur du contour de la forme.
	 */
	public Color getCouleur (){
		return this.couleur;
	}
	
	/**
	 * Met à jour la couleur du contour de la forme.
	 * @param couleur : la nouvelle couleur du contour.
	 */
	public void setCouleur (Color couleur){
		this.couleur = couleur;
	}	

}
