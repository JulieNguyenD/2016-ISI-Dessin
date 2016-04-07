package widgets.widget_sous_barre;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import elements.Taille;
import fr.lri.swingstates.canvas.CRectangle;
import fr.lri.swingstates.canvas.Canvas;

/**
 * <b>CRectangle pour mettre les différents choix de Taille </b>
 * <p>C'est un CRectangle qui possède les choix pour la taille.</p>
 * 
 * @see CRectangle
 * 
 * @author ANDRIANIRINA Tojo
 * @author GABRIEL Damien
 * @author NGUYEN Julie
 */
public class Taille_Widget extends CRectangle {
	
	/**
	 * Canvas sur lequel on dessine.
	 */
	private Canvas canvas;
	
	/**
	 * Liste des Tailles
	 * @see Taille
	 */
	private ArrayList <Taille> tailleList;
	
	/**
	 * Le padding.
	 */
	private int padding = 10;
	
	/**
	 * La position de depart.
	 */
	private Point2D position_init;
	
	/**
	 * Constructeur de Taille_Widget. Au départ, il n'est pas visible.
	 * @param canvas : canvas sur lequel on dessine, ajoute les éléments.
	 * @param position : position de départ du widget annexe.
	 */
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
	
	/**
	 * Fonction qui permet de montrer ou cacher le widget. On ne peut plus récupérer les états avec un CStateMachine.
	 * @param b : true si on veut montrer. False si on veut cacher.
	 */
	public void montrer(boolean b) {
		for (Taille t : tailleList) {
			t.montrer(b);
		}
	}
	
}