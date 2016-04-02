import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D;

import elements.*;
import fr.lri.swingstates.canvas.CImage;
import fr.lri.swingstates.canvas.CRectangle;
import fr.lri.swingstates.canvas.CShape;
import fr.lri.swingstates.canvas.Canvas;

/**
 * <b>CShape qui sert de wiget pour les outils</b>
 * <p>CShape qui contient les éléments de manipulation du canvas. Il possède :
 * <ul>
 * <li>Un Canvas pour ajouter les CElements dessus.</li>
 * <li>Deux CRectangle : l'un pour faire le drag, l'autre pour mettre les outils dedans.</li>
 * <li>Quatre CImage : un pour chaque outils.</li>
 * </ul>
 * </p>
 * 
 * @see Canvas
 * @see Application
 * @see CRectangle
 * @see CImage
 *  
 * @author ANDRIANIRINA Tojo
 * @author GABRIEL Damien
 * @author NGUYEN Julie
 */
public class WidgetOutils extends CShape {
	
	/**
	 * Le canvas pour ajouter les composants dessus.
	 * @see WidgetOutils#WidgetPinceau(Canvas, Point)
	 */
	private Canvas canvas;
	
	/**
	 * Les CRectangle. 
	 * <p><b>drag</b> : CRectangle au-dessus de outils. Permet de faire bouger l'ensemble.<br/>
	 * <b>outils</b> :  CRectangle contenant les CImages des outils, alignés verticalement.</p>
	 * @see WidgetOutils#WidgetPinceau(Canvas, Point)
	 */
	private CRectangle drag, outils;
	
	/**
	 * Les CImages des outils.
	 * <p><b>pinceau</b> : CImage de l'outils pinceau<br/>
	 * <b>pot</b> : CImage de l'outils pot<br/>
	 * <b>gomme</b> : CImage de l'outils gomme<br/>
	 * <b>forme</b> : CImage de l'outils forme</p>
	 * @see WidgetOutils#WidgetPinceau(Canvas, Point)
	 */
	
	private Pinceau pinceau;
	
	private Pot pot;
	
	private Gomme gomme;
	
	private Forme forme;
	
	private ChoixPinceau choix;
	
	/**
	 * padding entre les CImages et le CRectangle outils qui l'entoure.
	 * @see WidgetOutils#outils
	 */
	private int padding = 10;

	/**
	 * Constructeur de WidgetPinceau.
	 * <p>On instancie le canvas. On positionne les CRectangles et on ajoute les CImages</p>
	 * 
	 * @param c : canvas sur lequel on dessinne. 
	 * @param position : position à laquelle on place le coin supérieur gauche de la première image.
	 */
	public WidgetOutils(Canvas c, Point position) {
		this.canvas = c;
		drag = new CRectangle(position.getX()-padding, position.getY()-padding-15, 80+2*padding, 15);
		outils = new CRectangle(position.getX()-padding, position.getY()-padding, 80+2*padding, 4*80+2*padding);

		canvas.addShape(drag);
		canvas.addShape(outils);
		
		pinceau = new Pinceau("images/pinceau2.PNG", new Point2D.Double(position.getX(), position.getY()), canvas);
		pinceau.addPinceauStateMachine(pinceau);
		
		pot = new Pot("images/pot.PNG", new Point2D.Double(position.getX(), position.getY()+80), canvas);
		pot.addPotStateMachine(pot);
		
		gomme = new Gomme("images/gomme.PNG", new Point2D.Double(position.getX(), position.getY()+2*80), canvas);
		gomme.addGommeStateMachine(gomme);
		
		forme = new Forme("images/forme.PNG", new Point2D.Double(position.getX(), position.getY()+3*80), canvas);
		forme.addFormeStateMachine(forme);
		
		outils.addChild(pinceau).addChild(pot).addChild(gomme).addChild(forme);
		drag.addChild(outils);		
		
		choix = new ChoixPinceau(canvas, new Point2D.Double(position.getX(), position.getY()));
		choix.setParent(drag);
		
		drag.addTag("draggable");
		drag.setOutlinePaint(Color.BLACK).setFillPaint(Color.RED).setTransparencyFill((float) 0.25);
		
	}

}
