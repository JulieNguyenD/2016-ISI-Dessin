package widgets;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D;

import elements.*;
import fr.lri.swingstates.canvas.CImage;
import fr.lri.swingstates.canvas.CRectangle;
import fr.lri.swingstates.canvas.CShape;
import fr.lri.swingstates.canvas.CStateMachine;
import fr.lri.swingstates.canvas.Canvas;
import fr.lri.swingstates.canvas.transitions.EnterOnShape;
import fr.lri.swingstates.canvas.transitions.LeaveOnShape;
import fr.lri.swingstates.canvas.transitions.PressOnShape;
import fr.lri.swingstates.canvas.transitions.PressOnTag;
import fr.lri.swingstates.canvas.transitions.EnterOnTag;
import fr.lri.swingstates.sm.State;
import fr.lri.swingstates.sm.Transition;
import fr.lri.swingstates.sm.transitions.Move;
import fr.lri.swingstates.sm.transitions.Press;
import fr.lri.swingstates.sm.transitions.Release;
import main.Utilitaires;
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
 * <li>Quatre CImage : un pour chaque outils. (Ils ont des classes différentes)</li>
 * <li>Quatre CRectangle : un pour chaque outils, ce sont les widgets annexes. (Ils ont aussi des classes différentes)</li>
 * </ul>
 * </p>
 * 
 * @see Canvas
 * @see CRectangle
 * @see CImage
 * @see Pinceau
 * @see Pot
 * @see Gomme
 * @see Forme
 * @see ChoixPinceau
 * @see ChoixPot
 * @see ChoixGomme
 * @see ChoixFormes
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
	 * @see WidgetOutils#WidgetOutils(Canvas, Point)
	 */
	private CRectangle drag, outils;
	
	/**
	 * Les Point2D pour les CImages.
	 * <p><b>position_image_pinceau</b> : Point2D servant pour avoir la position de départ du pinceau.<br/>
	 * <b>position_image_pot</b> : Point2D servant pour avoir la position de départ du pot.<br/>
	 * <b>position_image_gomme</b> : Point2D servant pour avoir la position de départ de la gomme.<br/>
	 * <b>position_image_forme</b> : Point2D servant pour avoir la position de départ de l'outils forme.<br/></p>
	 * @see WidgetOutils#WidgetOutils(Canvas, Point)
	 */
	private Point2D position_image_pinceau, position_image_pot, position_image_gomme, position_image_forme;
	
	/**
	 * Le Pinceau (c'est un CImage).
	 * L'outils qui permet de dessiner sur le canvas
	 * @see Pinceau
	 */
	private Pinceau pinceau;

	/**
	 * Le Pot (c'est un CImage).
	 * L'outils qui permet de remplir les formes sur le canvas
	 * @see Pot
	 */
	private Pot pot;
	
	/**
	 * La Gomme (c'est un CImage).
	 * L'outils qui permet de gommer/supprimer sur le canvas
	 * @see Gomme
	 */
	private Gomme gomme;
	
	/**
	 * L'outils Forme (c'est un CImage).
	 * L'outils qui permet de faire des formes sur le canvas
	 * @see Forme
	 */
	private Forme forme;
	
	/**
	 * Le widget annexe pour le Pinceau (c'est un CRectangle). 
	 * Il permet d'afficher les différentes options du Pinceau.
	 * @see ChoixPinceau
	 * @see Pinceau
	 */
	private ChoixPinceau choixPinceau;	

	/**
	 * Le widget annexe pour le Pot (c'est un CRectangle). 
	 * Il permet d'afficher les différentes options du Pot.
	 * @see ChoixPot
	 * @see Pot
	 */
	private ChoixPot choixPot;
	
	/**
	 * Le widget annexe pour de l'outis Forme (c'est un CRectangle). 
	 * Il permet d'afficher les différentes options de la Forme.
	 * @see ChoixFormes
	 * @see Forme
	 */
	private ChoixFormes choixFormes;
	
	/**
	 * Le widget annexe pour de la Gomme (c'est un CRectangle). 
	 * Il permet d'afficher les différentes options de la Gomme.
	 * @see ChoixGomme
	 * @see Gomme
	 */
	private ChoixGomme choixGomme;
			
	/**
	 * padding entre les CImages et le CRectangle outils qui l'entoure.
	 * @see WidgetOutils#outils
	 */
	private int padding = 10;

	/**
	 * Constructeur de WidgetPinceau.
	 * <p>On instancie le canvas. On positionne les CRectangles et on ajoute les CImages<br/>
	 * et on ajoute les widgets annexes en les plaçant à gauche des outils</p>
	 * 
	 * @see Point2D
	 * @see Pinceau#Pinceau(String, Point2D, Canvas)
	 * @see Pot#Pot(String, Point2D, Canvas)
	 * @see Gomme#Gomme(String, Point2D, Canvas)
	 * @see Forme#Forme(String, Point2D, Canvas)
	 * @see ChoixPinceau#ChoixPinceau(Canvas, Point2D)
	 * @see Pinceau#addPinceauStateMachine(Pinceau, ChoixPinceau)
	 * @see ChoixPot#ChoixPot(Canvas, Point2D)
	 * @see Pot#addPotStateMachine(Pot, ChoixPot)
	 * @see ChoixGomme#ChoixGomme(Canvas, Point2D)
	 * @see Gomme#addGommeStateMachine(Gomme, ChoixGomme)
	 * @see ChoixFormes#ChoixFormes(Canvas, Point2D)
	 * @see Forme#addFormeStateMachine(CShape, ChoixFormes)
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
		
		position_image_pinceau = new Point2D.Double(position.getX(), position.getY());
		position_image_pot = new Point2D.Double(position.getX(), position.getY()+80);
		position_image_gomme = new Point2D.Double(position.getX(), position.getY()+2*80);
		position_image_forme = new Point2D.Double(position.getX(), position.getY()+3*80);
		
		this.pinceau = new Pinceau("images/pinceau2.png", position_image_pinceau, canvas);		
		this.pot = new Pot("images/pot.png", position_image_pot, canvas);		
		this.gomme = new Gomme("images/gomme.png", position_image_gomme, canvas);		
		this.forme = new Forme("images/forme.png", position_image_forme, canvas);
		
		smGomme = this.gomme.createGommeStateMachineDrawing(gomme, canvas);
		smGomme.attachTo(canvas);
		
		smPot = this.pot.createPotStateMachineDrawing(pot, canvas);
		smPot.attachTo(canvas);
		
		outils.addChild(pinceau).addChild(pot).addChild(gomme).addChild(forme);
		drag.addChild(outils);			

		drag.addTag("NonDrawable");
		outils.addTag("NonDrawable");
		
		choixPinceau = new ChoixPinceau(canvas, position_image_pinceau);		
		choixPot = new ChoixPot(canvas, position_image_pot);		
		choixGomme = new ChoixGomme(canvas, position_image_gomme);		
		choixFormes = new ChoixFormes(canvas, position_image_forme);
		
		// drag est le parent de tous les widgets annexes
		drag.addChild(choixPinceau).addChild(choixPot).addChild(choixGomme).addChild(choixFormes);
		
		// Ajout des statesMachines sur les formes.
		// Si on passe sur un des outils principaux, cela affiche le widget annexe
		pot.addPotStateMachine(pot, this);
		gomme.addGommeStateMachine(gomme, this);
		forme.addFormeStateMachine(forme, this);	
				
		// drag est draggable. Permet à la stateMachine du Canvas de faire bouger les éléments draggable.
		drag.addTag("draggable");
		drag.setOutlinePaint(Color.BLACK).setFillPaint(Color.RED).setTransparencyFill((float) 0.25);		
	}
	
	/**
	 * Retourne la CStateMachine smPinceau en attribut.
	 * @return la CStateMachine smPinceau
	 */
	public CStateMachine getSMPinceau(){
		return smPinceau;
	}
	
	/**
	 * Mise à jour de la CStateMachine smPinceau avec le paramètre.
	 * @param smPinceau : la nouvelle CStateMachine pour le pinceau.
	 */
	public void setSMPinceau (CStateMachine smPinceau){
		this.smPinceau = smPinceau;
	}
	
	/**
	 * Getter de ChoixPinceau qui est le widget annexe du Pinceau
	 * @return choixPinceau : l'attribut choixPinceau 
	 */
	public ChoixPinceau getChoixPinceau() {
		return choixPinceau;
	}

	/**
	 * Getter de ChoixPot qui est le widget annexe du Pot
	 * @return choixPot : l'attribut choixPot 
	 */
	public ChoixPot getChoixPot() {
		return choixPot;
	}

	/**
	 * Getter de ChoixFormes qui est le widget annexe de la Forme
	 * @return choixFormes : l'attribut choixFormes 
	 */
	public ChoixFormes getChoixFormes() {
		return choixFormes;
	}

	/**
	 * Getter de ChoixGomme qui est le widget annexe de la Gomme
	 * @return choixGomme : l'attribut choixGomme 
	 */
	public ChoixGomme getChoixGomme() {
		return choixGomme;
	}
	
	/**
	 * Getter de Pinceau qui est l'outils pinceau
	 * @return pinceau : l'attribut pinceau 
	 */
	public Pinceau getPinceau() {
		return pinceau;
	}

	/**
	 * Getter de Pot qui est l'outils Pot
	 * @return pot : l'attribut pot 
	 */
	public Pot getPot() {
		return pot;
	}

	/**
	 * Getter de Gomme qui est l'outils Gomme
	 * @return gomme : l'attribut gomme 
	 */
	public Gomme getGomme() {
		return gomme;
	}

	/**
	 * Getter de Forme qui est l'outils Forme
	 * @return forme : l'attribut forme 
	 */
	public Forme getForme() {
		return forme;
	}

}
