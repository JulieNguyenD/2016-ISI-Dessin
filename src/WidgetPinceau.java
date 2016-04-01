import java.awt.Point;

import javax.swing.JComponent;

import fr.lri.swingstates.canvas.CRectangle;
import fr.lri.swingstates.canvas.CShape;
import fr.lri.swingstates.canvas.Canvas;

/**
 * CShape qui contient les éléments de manipulation du canvas
 * 
 * @author ANDRIANIRINA Tojo
 * @author GABRIEL Damien
 * @author NGUYEN Julie
 */
public class WidgetPinceau extends CShape {
	
	private Canvas canvas;
	private CRectangle drag, outils;
	private int padding = 10;

	public WidgetPinceau(Canvas c, Point position) {
		this.canvas = c;
		drag = new CRectangle(position.getX()-padding, position.getY()-padding-15, 80+2*padding, 15);
		canvas.addShape(drag);
		outils = new CRectangle(position.getX()-padding, position.getY()-padding, 80+2*padding, 4*80+2*padding);
		canvas.addShape(outils);
		canvas.newImage(position.getX(), position.getY(), "images/pot.PNG");
		canvas.newImage(position.getX(), position.getY()+80, "images/forme.PNG");
		canvas.newImage(position.getX(), position.getY()+2*80, "images/gomme.PNG");
		canvas.newImage(position.getX(), position.getY()+3*80, "images/pinceau2.PNG");
		
		
	}

}
