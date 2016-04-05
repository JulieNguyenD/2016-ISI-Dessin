package widgets;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D;

import javax.swing.JFrame;

import elements.*;
import fr.lri.swingstates.canvas.CImage;
import fr.lri.swingstates.canvas.CRectangle;
import fr.lri.swingstates.canvas.CShape;
import fr.lri.swingstates.canvas.CStateMachine;
import fr.lri.swingstates.canvas.Canvas;
import fr.lri.swingstates.debug.StateMachineVisualization;
import fr.lri.swingstates.sm.JStateMachine;
import main.Lancement;
import widgets.widget_sous_barre.ChoixFormes;
import widgets.widget_sous_barre.ChoixGomme;
import widgets.widget_sous_barre.ChoixPinceau;
import widgets.widget_sous_barre.ChoixPot;

/**
 * <b>CShape qui sert de widget pour les outils</b>
 * <p>CShape qui contient les éléments de manipulation du canvas. Il possède :
 * <ul>
 * <li>Un Canvas pour ajouter les CElements dessus.</li>
 * <li>Deux CRectangle : l'un pour faire le drag, l'autre pour mettre les outils dedans.</li>
 * <li>Quatre CImage : un pour chaque outils.</li>
 * <li>Quatre CRectangle : un pour chaque outils, ce sont les widgets annexes.</li>
 * </ul>
 * </p>
 * 
 * @see Canvas
 * @see Application
 * @see CRectangle
 * @see CImage
 * @see Pinceau
 * @see Pot
 * @see Gomme
 * @see Forme
 * @see ChoixPinceau
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
	 * <b>outils</b> :  CRectangle contenant les CImages des outils, align�s verticalement.</p>
	 * @see WidgetOutils#WidgetPinceau(Canvas, Point)
	 */
	private CRectangle drag, outils;
	
	private Point2D position_image_pinceau, position_image_pot, position_image_gomme, position_image_forme;
	
	private ChoixPinceau choixPinceau;
	
	public CStateMachine smPinceau;
	
	private ChoixPot choixPot;
	
	private ChoixFormes choixFormes;
	
	private ChoixGomme choixGomme;
	
	private Pinceau p;
	
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
	public WidgetOutils(Canvas c, Point position, Pinceau pinceau, Pot pot, Gomme gomme, Forme forme) {
		this.canvas = c;
		
		drag = new CRectangle(position.getX()-padding, position.getY()-padding-15, 80+2*padding, 15);
		outils = new CRectangle(position.getX()-padding, position.getY()-padding, 80+2*padding, 4*80+2*padding);

		canvas.addShape(drag);
		canvas.addShape(outils);
		
		position_image_pinceau = new Point2D.Double(position.getX(), position.getY());
		position_image_pot = new Point2D.Double(position.getX(), position.getY()+80);
		position_image_gomme = new Point2D.Double(position.getX(), position.getY()+2*80);
		position_image_forme = new Point2D.Double(position.getX(), position.getY()+3*80);
		

		pinceau = new Pinceau("images/pinceau2.png", position_image_pinceau, canvas);		
		pot = new Pot("images/pot.png", position_image_pot, canvas);		
		gomme = new Gomme("images/gomme.png", position_image_gomme, canvas);		
		forme = new Forme("images/forme.png", position_image_forme, canvas);

		p = pinceau;
		
		smPinceau = p.createPinceauStateMachineDrawing(pinceau, canvas);
		smPinceau.attachTo(canvas);
		
		outils.addChild(pinceau).addChild(pot).addChild(gomme).addChild(forme);
		drag.addChild(outils);		
		
		choixPinceau = new ChoixPinceau(canvas, position_image_pinceau);		
		choixPot = new ChoixPot(canvas, position_image_pot);		
		choixGomme = new ChoixGomme(canvas, position_image_gomme);		
		choixFormes = new ChoixFormes(canvas, position_image_forme);
		
		drag.addChild(choixPinceau).addChild(choixPot).addChild(choixGomme).addChild(choixFormes);
		
		// Ajout des statesMachines sur les formes.
		pinceau.addPinceauStateMachine(pinceau, choixPinceau);
		pot.addPotStateMachine(pot, choixPot);
		gomme.addGommeStateMachine(gomme, choixGomme);
		forme.addFormeStateMachine(forme, choixFormes);
		
		pinceau.createPinceauStateMachineDrawing(pinceau, canvas);
		
		drag.addTag("draggable");
		drag.setOutlinePaint(Color.BLACK).setFillPaint(Color.RED).setTransparencyFill((float) 0.25);
				
	}
	
	public CStateMachine getSMPinceau(){
		return smPinceau;
	}
	
	public void setSMPinceau (CStateMachine smPinceau){
		this.smPinceau = smPinceau;
	}
	
	public Pinceau getP() {
		return p;
	}
}
