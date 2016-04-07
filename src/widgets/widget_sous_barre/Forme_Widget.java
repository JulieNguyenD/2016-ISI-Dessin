package widgets.widget_sous_barre;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import elements.Annexe_forme;
import fr.lri.swingstates.canvas.CRectangle;
import fr.lri.swingstates.canvas.Canvas;

/**
 * <b>CRectangle pour mettre les différents choix de formes </b>
 * <p>C'est un CRectangle qui possède les choix pour la forme.</p>
 * 
 * @see CRectangle
 * 
 * @author ANDRIANIRINA Tojo
 * @author GABRIEL Damien
 * @author NGUYEN Julie
 */
public class Forme_Widget extends CRectangle {
	
	/**
	 * Canvas sur lequel on dessine.
	 */
	private Canvas canvas;
	
	/**
	 * Liste des Formes
	 * @see Annexe_forme
	 */
	private ArrayList <Annexe_forme> formeList;
	
	/**
	 * Le padding.
	 */
	private int padding = 10;
	
	/**
	 * La position de depart.
	 */
	private Point2D position_init;

	/**
	 * Constructeur de Forme_Widget. Au départ, il n'est pas visible.
	 * @param canvas : canvas sur lequel on dessine, ajoute les éléments.
	 * @param position : position de départ du widget annexe.
	 */
	public Forme_Widget (Canvas canvas, Point2D position){
		super(position, 70, 290);
		this.canvas = canvas;	
		
		position_init = new Point2D.Double(position.getX()+padding, position.getY()+padding);
		Point2D pos = new Point2D.Double(0, 0);
				
		formeList = new ArrayList<Annexe_forme>();		


		formeList.add(new Annexe_forme ("images/forme_carre.png", pos, canvas, "rectangle"));
		formeList.add(new Annexe_forme ("images/forme_ellipse.png", pos, canvas, "ellipse"));
		formeList.add(new Annexe_forme ("images/forme_lignes.png", pos, canvas, "ligne"));
				
		for (int i = 0; i < formeList.size(); i++){
			formeList.get(i).translateBy (position_init.getX(), position_init.getY()+(55 * i));
			formeList.get(i).setParent(this);
		}
	}
	
	/**
	 * Fonction qui permet de montrer ou cacher le widget. On ne peut plus récupérer les états avec un CStateMachine.
	 * @param b : true si on veut montrer. False si on veut cacher.
	 */
	public void montrer(boolean b) {
		for (Annexe_forme a : formeList) {
			a.montrer(b);
		}
	}

}
