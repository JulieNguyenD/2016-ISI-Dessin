import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D;

import elements.Pinceau;
import fr.lri.swingstates.canvas.CImage;
import fr.lri.swingstates.canvas.CRectangle;
import fr.lri.swingstates.canvas.CShape;
import fr.lri.swingstates.canvas.Canvas;

/**
 * <b>CShape qui sert de wiget pour les outils</b>
 * <p>CShape qui contient les �l�ments de manipulation du canvas. Il poss�de :
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
public class WidgetPinceau extends CShape {
	
	/**
	 * Le canvas pour ajouter les composants dessus.
	 * @see WidgetPinceau#WidgetPinceau(Canvas, Point)
	 */
	private Canvas canvas;
	
	/**
	 * Les CRectangle. 
	 * <p><b>drag</b> : CRectangle au-dessus de outils. Permet de faire bouger l'ensemble.<br/>
	 * <b>outils</b> :  CRectangle contenant les CImages des outils, align�s verticalement.</p>
	 * @see WidgetPinceau#WidgetPinceau(Canvas, Point)
	 */
	private CRectangle drag, outils;
	
	/**
	 * Les CImages des outils.
	 * <p><b>pinceau</b> : CImage de l'outils pinceau<br/>
	 * <b>pot</b> : CImage de l'outils pot<br/>
	 * <b>gomme</b> : CImage de l'outils gomme<br/>
	 * <b>forme</b> : CImage de l'outils forme</p>
	 * @see WidgetPinceau#WidgetPinceau(Canvas, Point)
	 */
	private CImage pot, gomme, forme;
	
	private Pinceau pinceau;
	
	/**
	 * padding entre les CImages et le CRectangle outils qui l'entoure.
	 * @see WidgetPinceau#outils
	 */
	private int padding = 10;

	/**
	 * Constructeur de WidgetPinceau.
	 * <p>On instancie le canvas. On positionne les CRectangles et on ajoute les CImages</p>
	 * 
	 * @param c : canvas sur lequel on dessinne. 
	 * @param position : position � laquelle on place le coin sup�rieur gauche de la premi�re image.
	 */
	public WidgetPinceau(Canvas c, Point position) {
		this.canvas = c;
		drag = new CRectangle(position.getX()-padding, position.getY()-padding-15, 80+2*padding, 15);
		outils = new CRectangle(position.getX()-padding, position.getY()-padding, 80+2*padding, 4*80+2*padding);

		canvas.addShape(drag);
		canvas.addShape(outils);
		
		pinceau = new Pinceau("images/pinceau2.PNG", new Point2D.Double(position.getX(), position.getY()), canvas);
		pinceau.addPinceauStateMachine(pinceau);
		
		pot = new CImage("images/pot.PNG", new Point2D.Double(position.getX(), position.getY()+80));
		gomme = new CImage("images/gomme.PNG", new Point2D.Double(position.getX(), position.getY()+2*80));
		forme = new CImage("images/forme.PNG", new Point2D.Double(position.getX(), position.getY()+3*80));

		canvas.addShape(pot);
		canvas.addShape(gomme);
		canvas.addShape(forme);
		
		outils.addChild(pinceau).addChild(pot).addChild(gomme).addChild(forme);
		drag.addChild(outils);
		
		drag.addTag("draggable");
		drag.setOutlinePaint(Color.BLACK).setFillPaint(Color.RED).setTransparencyFill((float) 0.25);
		
	}

}
