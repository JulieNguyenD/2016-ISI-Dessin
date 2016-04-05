package widgets;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.io.IOException;

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
	WidgetOutils widgetPinceau;
	
	public WidgetCouleurTaille (Canvas c, Point position, WidgetOutils widgetPinceau) throws IOException{
		this.canvas = c;
		
		drag = new CRectangle(position.getX()-padding, position.getY()-padding-15, 75+2*padding, 15);
		outils = new CRectangle(position.getX()-padding, position.getY()-padding, 75+2*padding, 110+2*padding);
		
		canvas.addShape(drag);
		canvas.addShape(outils);
		
		position_barCouleur = new Point2D.Double(position.getX(), position.getY());
		potision_barTaille = new Point2D.Double(position.getX() + 50, position.getY());
		
		this.barCouleur = new BarCouleur ("images/couleurBar.png", position_barCouleur, canvas, widgetPinceau);
		this.barTaille = new BarTaille ("images/tailleBar.png", potision_barTaille, canvas, widgetPinceau);
		
		outils.addChild(barCouleur).addChild(barTaille);
		drag.addChild(barCouleur).addChild(barTaille).addChild(outils);
		
		drag.addTag("draggable");
		drag.setOutlinePaint(Color.BLACK).setFillPaint(Color.RED).setTransparencyFill((float) 0.25);
		
		barCouleur.addTag("BarColor");
		barTaille.addTag("BarTaille");
		drag.addTag("NonDrawable");
		outils.addTag("NonDrawable");

		
	}	
}
