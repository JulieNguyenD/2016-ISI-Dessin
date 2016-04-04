package widgets.widget_sous_barre;

import java.awt.BasicStroke;
import java.awt.geom.Point2D;

import fr.lri.swingstates.canvas.CRectangle;
import fr.lri.swingstates.canvas.Canvas;

public class ChoixFormes extends CRectangle {
	
	private Canvas canvas;
	private CRectangle hidden;
	private Couleur_Widget choixCouleurContour;
	private Forme_Widget choixForme;
	private Point2D position_widget;
	
	public ChoixFormes(Canvas canvas, Point2D position, CRectangle hidden) {
		super(position.getX()-70*2-10*2, position.getY(), 70*2, 290);
		
		position_widget = new Point2D.Double(position.getX()-70*2-10*2, position.getY());

		this.canvas = canvas;	
		this.hidden = hidden;
		this.addTo(canvas);		
		
		this.setStroke(new BasicStroke(1));
		
		choixCouleurContour = new Couleur_Widget(canvas, position_widget);
		choixForme = new Forme_Widget(canvas, position_widget);
		choixForme.translateBy(70, 0);
		
		choixCouleurContour.setParent(this);
		choixForme.setParent(this);
		
		this.setParent(hidden);
		this.below(hidden);
		

	}

}
