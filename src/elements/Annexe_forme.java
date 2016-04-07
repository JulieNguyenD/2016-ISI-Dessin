package elements;

import java.awt.geom.Point2D;

import fr.lri.swingstates.canvas.CImage;
import fr.lri.swingstates.canvas.Canvas;

/**
 * <b>CImage pour les formes annexes</b>
 * <p>Cette forme sert à mettre une image autre que les outils principaux.<br/>
 * Elle possède une "forme" : c'est sa fonction. <br/>
 * Cela peut être : (Pour Forme) rectangle, ellipse, ligne, (Pour Gomme) forme ou pinceau.</p>
 * 
 * @see CImage
 * 
 * @author ANDRIANIRINA Tojo
 * @author GABRIEL Damien
 * @author NGUYEN Julie
 */
public class Annexe_forme extends CImage {
	
	/**
	 * La forme/fonction de l'annexe.
	 */
	private String forme;
	
	/**
	 * Le canvas sur lequel on ajoute la CStateMachine
	 * @see Canvas
	 */
	private Canvas canvas;
	
	/**
	 * Constructeur de Annexe_forme.
	 * <p>On initialise l'image avec "chemin" que l'on place à "position_depart". <br/>
	 * On la place sur le "canvas" et on donne sa fonction "forme"</p>
	 * @param chemin : le chemin vers l'image
	 * @param position_depart : la position de départ de l'image.
	 * @param canvas : le canvas sur lequel on ajoute l'annexe
	 * @param forme : la fonction de l'annexe.
	 */
	public Annexe_forme(String chemin, Point2D position_depart, Canvas canvas, String forme) {
		super(chemin, position_depart);
		this.canvas = canvas;
		this.forme = forme;

		this.addTo(this.canvas);
		
		this.addTag("Annexeforme");
	}

	/**
	 * Getter de forme
	 * @return forme : la forme/fonction de l'annexe
	 */
	public String getForme() {
		return forme;
	}

	/**
	 * Setter de l'annexe. On met à jour forme avec le paramètre.
	 * @param forme : la nouvelle forme/fonction de l'annexe.
	 */
	public void setForme(String forme) {
		this.forme = forme;
	}
	
	/**
	 * Fonction qui permet de montrer ou cacher l'annexe. On ne peut plus récupérer les états avec un CStateMachine.
	 * @param b : true si on veut montrer. False si on veut cacher.
	 */
	public void montrer(boolean b) {
		this.setDrawable(b);
		this.setPickable(b);
	}

}
