package widgets.widget_sous_barre;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import elements.Annexe_forme;
import fr.lri.swingstates.canvas.CRectangle;
import fr.lri.swingstates.canvas.Canvas;

/**
 * <b>CRectangle pour mettre les différents choix de la gomme </b>
 * <p>C'est un CRectangle qui possède les choix pour la gomme.</p>
 * 
 * @see CRectangle
 * 
 * @author ANDRIANIRINA Tojo
 * @author GABRIEL Damien
 * @author NGUYEN Julie
 */
public class Gomme_Widget extends CRectangle {
	
	/**
	 * Canvas sur lequel on dessine.
	 */
	private Canvas canvas;
	/**
	 * Liste des Formes
	 * @see Annexe_forme
	 */
	private ArrayList <Annexe_forme> gommeList;
	
	/**
	 * Le padding.
	 */
	private int padding = 10;
	
	/**
	 * La position de depart.
	 */
	private Point2D position_init;
	
	/**
	 * Constructeur de Gomme_Widget. Au départ, il n'est pas visible.
	 * @param canvas : canvas sur lequel on dessine, ajoute les éléments.
	 * @param position : position de départ du widget annexe.
	 */
	public Gomme_Widget(Canvas canvas, Point2D position) {
		super(position, 70, 290);
		this.canvas = canvas;	
		
		position_init = new Point2D.Double(position.getX()+padding, position.getY()+padding);
		Point2D pos = new Point2D.Double(0, 0);
				
		gommeList = new ArrayList<Annexe_forme>();
		gommeList.add(new Annexe_forme ("images/gomme_pinceau.png", pos, canvas, "pinceau"));
//		gommeList.add(new Annexe_forme ("images/gomme_pot.png", pos, canvas, "pot"));
		gommeList.add(new Annexe_forme ("images/gomme_forme.png", pos, canvas, "forme"));
				
		for (int i = 0; i < gommeList.size(); i++){
			gommeList.get(i).translateBy (position_init.getX(), position_init.getY()+(55 * i));
			gommeList.get(i).setParent(this);
		}
	}
	
	/**
	 * Fonction qui permet de montrer ou cacher le widget. On ne peut plus récupérer les états avec un CStateMachine.
	 * @param b : true si on veut montrer. False si on veut cacher.
	 */
	public void montrer(boolean b) {
		for (Annexe_forme a : gommeList) {
			a.montrer(b);
		}
	}

}
