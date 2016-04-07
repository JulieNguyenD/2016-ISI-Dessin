package elements;

import java.awt.Color;
import java.awt.geom.Point2D;

import fr.lri.swingstates.canvas.CImage;
import fr.lri.swingstates.canvas.CStateMachine;
import fr.lri.swingstates.canvas.Canvas;
import fr.lri.swingstates.canvas.transitions.EnterOnShape;
import fr.lri.swingstates.canvas.transitions.LeaveOnShape;
import fr.lri.swingstates.sm.State;
import fr.lri.swingstates.sm.Transition;
import fr.lri.swingstates.sm.transitions.Press;
import fr.lri.swingstates.sm.transitions.Release;

public class Annexe_forme extends CImage {
	
	private String forme;
	
	/**
	 * Le canvas sur lequel on ajoute la CStateMachine
	 * @see Canvas
	 */
	private Canvas canvas;
	
	public Annexe_forme(String chemin, Point2D position_depart, Canvas canvas, String forme) {
		super(chemin, position_depart);
		this.canvas = canvas;
		this.forme = forme;

		this.addTo(canvas);
		this.addTag("choix");
		
		this.addTag("Annexeforme");
	}

	public String getForme() {
		return forme;
	}

	public void setForme(String forme) {
		this.forme = forme;
	}
	
	public void montrer(boolean b) {
		this.setDrawable(b);
		this.setPickable(b);
	}

}
