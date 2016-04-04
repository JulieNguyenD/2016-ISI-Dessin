package widgets.widget_sous_barre;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import elements.Annexe_forme;
import elements.Taille;
import fr.lri.swingstates.canvas.CRectangle;
import fr.lri.swingstates.canvas.Canvas;

public class Gomme_Widget extends CRectangle {
	
	private Canvas canvas;
	private ArrayList <Annexe_forme> gommeList;
	private int padding = 10;
	private Point2D position_init;
	
	public Gomme_Widget(Canvas canvas, Point2D position) {
		super(position, 70, 290);
		this.canvas = canvas;	
		
		position_init = new Point2D.Double(position.getX()+padding, position.getY()+padding);
		Point2D pos = new Point2D.Double(0, 0);
				
		gommeList = new ArrayList<Annexe_forme>();		
		gommeList.add(new Annexe_forme ("images/gomme_pot.png", pos, canvas, "pot"));
		gommeList.add(new Annexe_forme ("images/gomme_forme.png", pos, canvas, "forme"));
				
		for (int i = 0; i < gommeList.size(); i++){
			gommeList.get(i).translateBy (position_init.getX(), position_init.getY()+(55 * i));
			gommeList.get(i).setParent(this);
		}
	}
	
	public void montrer(boolean b) {
		for (Annexe_forme a : gommeList) {
			a.montrer(b);
		}
	}

}
