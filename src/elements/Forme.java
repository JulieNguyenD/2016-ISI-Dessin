package elements;

import java.awt.geom.Point2D;

import fr.lri.swingstates.canvas.CImage;
import fr.lri.swingstates.canvas.CShape;
import fr.lri.swingstates.canvas.CStateMachine;
import fr.lri.swingstates.canvas.Canvas;
import fr.lri.swingstates.canvas.transitions.EnterOnShape;
import fr.lri.swingstates.canvas.transitions.EnterOnTag;
import fr.lri.swingstates.canvas.transitions.LeaveOnShape;
import fr.lri.swingstates.canvas.transitions.LeaveOnTag;
import fr.lri.swingstates.canvas.transitions.PressOnShape;
import fr.lri.swingstates.sm.State;
import fr.lri.swingstates.sm.Transition;
import fr.lri.swingstates.sm.transitions.Drag;
import fr.lri.swingstates.sm.transitions.Move;
import fr.lri.swingstates.sm.transitions.Press;
import fr.lri.swingstates.sm.transitions.Release;
import main.Utilitaires;
import widgets.WidgetOutils;
import widgets.widget_sous_barre.ChoixFormes;

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
	 * Constructeur de Forme.
	 * <p>A la création d'une Forme.
	 * Le forme n'est pas actif.<br/>
	 * On instancie le canvas et on attache le forme au Canvas.</p> 
	 * 
	 * @param path : Le chemin vers l'image.
	 * @param position : La position de départ de l'image (ici le coin supérieur gauche de l'image)
	 */
	public Forme(String path, Point2D position, Canvas canvas) {
		super(path, position);
		this.estActif = false;
		this.canvas = canvas;
		
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

}
