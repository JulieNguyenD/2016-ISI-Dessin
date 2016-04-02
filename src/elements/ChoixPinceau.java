package elements;

import java.awt.BasicStroke;
import java.awt.geom.Point2D;

import fr.lri.swingstates.canvas.CRectangle;
import fr.lri.swingstates.canvas.Canvas;
import widget_Sous_Barre.Couleur_Widget;
import widget_Sous_Barre.Taille_Widget;

public class ChoixPinceau extends CRectangle {
	
	private Canvas canvas;
	private Couleur_Widget choixPinceauCouleur;
	private Taille_Widget choixPinceauTaille;
	private int padding;
	private Point2D position_init;
	
	public ChoixPinceau(Canvas canvas, Point2D position) {
		super(position.getX()-300, position.getY(), 70*2, 300);

		position_init = new Point2D.Double(position.getX()-300, position.getY());
		
		padding = 10;
		
		this.canvas = canvas;		
		this.addTo(canvas);
		
		this.setStroke(new BasicStroke(1));
		
		choixPinceauCouleur = new Couleur_Widget(canvas, position_init);
		choixPinceauTaille = new Taille_Widget(canvas, position_init);
		choixPinceauTaille.translateBy(70+padding, 0);
		
		choixPinceauCouleur.setParent(this);
		choixPinceauTaille.setParent(this);
		
		this.belowAll();
		
	}
	
	public CRectangle getChoixPinceauTaille() {
		return choixPinceauTaille;
	}

}
