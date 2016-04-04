package widgets.widget_sous_barre;

import java.awt.BasicStroke;
import java.awt.geom.Point2D;

import fr.lri.swingstates.canvas.CRectangle;
import fr.lri.swingstates.canvas.Canvas;

public class ChoixGomme extends CRectangle {
	
	private String fonction;
	
	private Canvas canvas;
	
	private Gomme_Widget gommeWidget;
	
	private Point2D position_widget;
	
	public ChoixGomme(Canvas canvas, Point2D position) {
		super(position.getX()-70-10*2, position.getY(), 70, 125);
		
		position_widget = new Point2D.Double(position.getX()-70-10*2, position.getY());

		this.canvas = canvas;	
		this.addTo(canvas);		
		
		this.setStroke(new BasicStroke(1));
		
		gommeWidget = new Gomme_Widget(canvas, position_widget);
		
		gommeWidget.setParent(this);	
		
		this.montrer(false);
	}
	
	public void montrer(boolean b) {
		this.setDrawable(b);
		this.gommeWidget.montrer(b);
	}

}
