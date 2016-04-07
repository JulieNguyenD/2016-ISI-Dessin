package widgets.widget_sous_barre;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import elements.Couleur;
import fr.lri.swingstates.canvas.CRectangle;
import fr.lri.swingstates.canvas.Canvas;

/**
 * <b>CRectangle pour mettre les différents choix de couleurs </b>
 * <p>C'est un CRectangle qui possède les choix pour la Couleur.</p>
 * 
 * @see CRectangle
 * 
 * @author ANDRIANIRINA Tojo
 * @author GABRIEL Damien
 * @author NGUYEN Julie
 */
public class Couleur_Widget extends CRectangle {
	
	/**
	 * Canvas sur lequel on dessine.
	 */
	private Canvas canvas;
	
	/**
	 * Liste des Couleurs
	 * @see Couleur
	 */
	private ArrayList <Couleur> couleurList;
	
	/**
	 * Le padding.
	 */
	private int padding = 10;
	
	/**
	 * La position de depart.
	 */
	private Point2D position_init;

	/**
	 * Constructeur de Couleur_Widget. Au départ, il n'est pas visible.
	 * @param canvas : canvas sur lequel on dessine, ajoute les éléments.
	 * @param position : position de départ du widget annexe.
	 */
	public Couleur_Widget (Canvas canvas, Point2D position){
		super(position, 70, 290);
		this.canvas = canvas;	
		
		position_init = new Point2D.Double(position.getX()+padding, position.getY()+padding);
				
		couleurList = new ArrayList<Couleur>();		
		couleurList.add(new Couleur (Color.red, canvas));
		couleurList.add(new Couleur (Color.green, canvas));
		couleurList.add(new Couleur (Color.blue, canvas));
		couleurList.add(new Couleur (Color.yellow, canvas));
		couleurList.add(new Couleur (Color.white, canvas));
				
		for (int i = 0; i < couleurList.size(); i++){
			couleurList.get(i).translateBy (position_init.getX(), position_init.getY()+(55 * i));
			couleurList.get(i).setParent(this);
		}
				
	}
	
	/**
	 * Fonction qui permet de montrer ou cacher le widget. On ne peut plus récupérer les états avec un CStateMachine.
	 * @param b : true si on veut montrer. False si on veut cacher.
	 */
	public void montrer(boolean b) {
		for(Couleur i : couleurList) {
			i.montrer(b);
		}
	}
		
}
