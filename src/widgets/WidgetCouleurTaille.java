package widgets;

import java.awt.Point;
import java.awt.geom.Point2D;

import elements.BarCouleur;
import elements.BarTaille;
import fr.lri.swingstates.canvas.CRectangle;
import fr.lri.swingstates.canvas.CShape;
import fr.lri.swingstates.canvas.Canvas;

public class WidgetCouleurTaille extends CShape{
	private Canvas canvas;
	private CRectangle drag, outils;
	private BarCouleur barCouleur;
	private BarTaille barTaille;
	private Point2D position_barCouleur, potision_barTaille;
	private int padding = 10;
	
	public WidgetCouleurTaille (Canvas c, Point position){
		this.canvas = c;
		drag = new CRectangle(position.getX()-padding, position.getY()-padding-15, 160+2*padding, 15);
		outils = new CRectangle(position.getX()-padding, position.getY()-padding, 160+2*padding, 4*160+2*padding);
		
		canvas.addShape(drag);
		canvas.addShape(outils);
		
		position_barCouleur = new Point2D.Double(position.getX(), position.getY());
		potision_barTaille = new Point2D.Double(position.getX() + 80, position.getY());
		
	}
	
}
