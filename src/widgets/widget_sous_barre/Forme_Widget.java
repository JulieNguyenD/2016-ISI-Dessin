package widgets.widget_sous_barre;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import elements.Annexe_forme;
import fr.lri.swingstates.canvas.CRectangle;
import fr.lri.swingstates.canvas.Canvas;

public class Forme_Widget extends CRectangle {
	
	private Canvas canvas;
	private ArrayList <Annexe_forme> formeList;
	private int padding = 10;
	private Point2D position_init;

	public Forme_Widget (Canvas canvas, Point2D position){
		super(position, 70, 290);
		this.canvas = canvas;	
		
		position_init = new Point2D.Double(position.getX()+padding, position.getY()+padding);
		Point2D pos = new Point2D.Double(0, 0);
				
		formeList = new ArrayList<Annexe_forme>();		


		formeList.add(new Annexe_forme ("images/forme_carre.png", pos, canvas, "rectangle"));
		formeList.add(new Annexe_forme ("images/forme_ellipse.png", pos, canvas, "ellipse"));
		formeList.add(new Annexe_forme ("images/forme_triangle.png", pos, canvas, "line"));
				
		for (int i = 0; i < formeList.size(); i++){
			formeList.get(i).translateBy (position_init.getX(), position_init.getY()+(55 * i));
			formeList.get(i).setParent(this);
		}
	}
	
	public void montrer(boolean b) {
		for (Annexe_forme a : formeList) {
			a.montrer(b);
		}
	}

}
