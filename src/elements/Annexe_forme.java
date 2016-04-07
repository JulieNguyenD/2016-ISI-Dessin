package elements;

import java.awt.geom.Point2D;

import fr.lri.swingstates.canvas.CImage;
import fr.lri.swingstates.canvas.Canvas;

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

		this.addTo(this.canvas);
		
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
