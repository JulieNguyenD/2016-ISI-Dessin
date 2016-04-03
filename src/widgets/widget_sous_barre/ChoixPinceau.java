package widgets.widget_sous_barre;

import java.awt.BasicStroke;
import java.awt.geom.Point2D;

import fr.lri.swingstates.canvas.CRectangle;
import fr.lri.swingstates.canvas.Canvas;

public class ChoixPinceau extends CRectangle {
	
	private Canvas canvas;
	private Couleur_Widget choixPinceauCouleur;
	private Taille_Widget choixPinceauTaille;
	private Point2D position_widget;
	
	public ChoixPinceau(Canvas canvas, Point2D position) {
		super(position.getX()-70*2-10*2, position.getY(), 70*2, 290);
		
		position_widget = new Point2D.Double(position.getX()-70*2-10*2, position.getY());
		
		this.canvas = canvas;		
		this.addTo(canvas);
		
		
		this.setStroke(new BasicStroke(1));
		
		choixPinceauCouleur = new Couleur_Widget(canvas, position_widget);
		choixPinceauTaille = new Taille_Widget(canvas, position_widget);
		choixPinceauTaille.translateBy(70, 0);
		
		choixPinceauCouleur.setParent(this);
		// choixPinceauTaille.setParent(this);
		
		// this.belowAll();
		
	}
	
	public CRectangle getChoixPinceauTaille() {
		return choixPinceauTaille;
	}

}
