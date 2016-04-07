package widgets;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.io.IOException;

import elements.BarCouleur;
import elements.BarTaille;
import elements.Forme;
import elements.Gomme;
import elements.Pinceau;
import elements.Pot;
import fr.lri.swingstates.canvas.CImage;
import fr.lri.swingstates.canvas.CRectangle;
import fr.lri.swingstates.canvas.CShape;
import fr.lri.swingstates.canvas.Canvas;
import widgets.widget_sous_barre.ChoixFormes;
import widgets.widget_sous_barre.ChoixGomme;
import widgets.widget_sous_barre.ChoixPinceau;
import widgets.widget_sous_barre.ChoixPot;

/**
 * <b>CShape qui sert de widget pour l'outils annexe</b>
 * <p>CShape qui contient les éléments de manipulation du canvas. Il possède :
 * <ul>
 * <li>Un Canvas pour ajouter les CElements dessus.</li>
 * <li>Deux CRectangle : l'un pour faire le drag, l'autre pour mettre les outils dedans.</li>
 * <li>Un BarCouleur : pour ajuster la couleur.</li>
 * <li>Un BarTaille : oour ajuster la taille.</li>
 * <li>Deux Point2D : un pour chaque outils, pour les positionner.</li>
 * <li>Un padding</li>
 * <li>Un WidgetOutils : pour relier avec l'outils principal.</li>
 * </ul>
 * </p>
 * 
 * @see Canvas
 * @see CRectangle
 * @see BarCouleur
 * @see BarTaille
 * @see Point2D
 * @see WidgetOutils
 *  
 * @author ANDRIANIRINA Tojo
 * @author GABRIEL Damien
 * @author NGUYEN Julie
 */
public class WidgetCouleurTaille extends CShape {
	/**
	 * Le canvas pour ajouter les composants dessus.
	 * @see WidgetCouleurTaille#WidgetCouleurTaille(Canvas, Point, WidgetOutils)
	 */
	private Canvas canvas;
	
	/**
	 * Les CRectangle. 
	 * <p><b>drag</b> : CRectangle au-dessus de outils. Permet de faire bouger l'ensemble.<br/>
	 * <b>outils</b> :  CRectangle contenant les CImages des outils, alignés verticalement.</p>
	 */
	private CRectangle drag, outils;
	
	/**
	 * La BarCouleur pour ajuster la couleur
	 * @see BarCouleur
	 */
	private BarCouleur barCouleur;
	
	/**
	 * La BarTaille pour ajuster la Taille.
	 * @see BarTaille
	 */
	private BarTaille barTaille;
	
	/**
	 * Les Point2D pour les Barres.
	 * <p><b>position_barCouleur</b> : Point2D servant pour avoir la position de départ de la BarCouleur.<br/>
	 * <b>potision_barTaille</b> : Point2D servant pour avoir la position de départ de la BarTaille.<br/>
	 */
	private Point2D position_barCouleur, potision_barTaille;
	private int padding = 10;
	WidgetOutils widgetPinceau;
	
	/**
	 * Constructeur de WidgetCouleurTaille.
	 * <p>On instancie le canvas. On positionne les CRectangles et on ajoute les Barres</p>
	 * @param c : canvas sur lequel on dessinne. 
	 * @param position : position à laquelle on place le coin supérieur gauche de la BarCouleur.
	 * @param widgetPinceau : le WidgetPrincipal pour se référencer et transmettre les informations.
	 * 
	 * @throws IOException
	 */
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
