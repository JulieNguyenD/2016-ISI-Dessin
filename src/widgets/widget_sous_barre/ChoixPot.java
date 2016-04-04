package widgets.widget_sous_barre;

import java.awt.geom.Point2D;

import fr.lri.swingstates.canvas.CRectangle;
import fr.lri.swingstates.canvas.Canvas;

public class ChoixPot extends CRectangle {
	
	private Canvas canvas;
	private Couleur_Widget choixPinceauCouleur;
	private Point2D position_widget;
	
	public ChoixPot(Canvas canvas, Point2D position) {
		super(position.getX()-70-10*2, position.getY(), 70, 290);
		
		position_widget = new Point2D.Double(position.getX()-70-10*2, position.getY());
		
		this.canvas = canvas;
		this.addTo(canvas);
		
		choixPinceauCouleur = new Couleur_Widget(canvas, position_widget);

		choixPinceauCouleur.setParent(this);
		
		this.montrer(false);			
	}
	
	public void montrer(boolean b) {
		this.setDrawable(b);
		this.choixPinceauCouleur.montrer(b);
	}

}
