package widgets;

import java.awt.Point;

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
	private int padding = 10;
	
	public WidgetCouleurTaille (Canvas c, Point position){
		this.canvas = c;
		drag = new CRectangle(position.getX()-padding, position.getY()-padding-15, 80+2*padding, 15);
		//outils = 
	}
	
}
