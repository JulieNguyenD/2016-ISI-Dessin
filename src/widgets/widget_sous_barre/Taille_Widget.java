package widgets.widget_sous_barre;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import elements.Taille;
import fr.lri.swingstates.canvas.CRectangle;
import fr.lri.swingstates.canvas.Canvas;

public class Taille_Widget extends CRectangle {
	private Canvas canvas;
	private ArrayList <Taille> tailleList;
	private int padding = 10;
	private Point2D position_init;
	
	public Taille_Widget (Canvas canvas, Point2D position){
		super(position, 70, 300);
		this.canvas = canvas;	
		
		position_init = new Point2D.Double(position.getX()+padding, position.getY()+padding);
				
		tailleList = new ArrayList<Taille>();		
		tailleList.add(new Taille (1.0, canvas));
		tailleList.add(new Taille (2.0, canvas));
		tailleList.add(new Taille (3.0, canvas));
		tailleList.add(new Taille (4.0, canvas));
		tailleList.add(new Taille (5.0, canvas));
				
		for (int i = 0; i < tailleList.size(); i++){
			tailleList.get(i).translateBy (position_init.getX(), position_init.getY()+(55 * i));
			tailleList.get(i).setParent(this);
		}	
		
	}
	
}