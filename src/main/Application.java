package main;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.io.IOException;

import javax.swing.JFrame;

import elements.BarCouleur;
import elements.BarTaille;
import elements.Forme;
import elements.Gomme;
import elements.Pinceau;
import elements.Pot;
import elements.QuitMenu;
import elements.QuitMenu_remake;
import fr.lri.swingstates.canvas.CRectangle;
import fr.lri.swingstates.canvas.CShape;
import fr.lri.swingstates.canvas.CStateMachine;
import fr.lri.swingstates.canvas.Canvas;
import fr.lri.swingstates.canvas.transitions.PressOnTag;
import fr.lri.swingstates.sm.State;
import fr.lri.swingstates.sm.Transition;
import fr.lri.swingstates.sm.transitions.Drag;
import fr.lri.swingstates.sm.transitions.Release;
import widgets.WidgetCouleurTaille;
import widgets.WidgetOutils;

/**
 * <b>Fenêtre de l'application.</b>
 * <p>C'est une JFrame qui affiche en plein écran le canvas avec ses composants.
 * Elle a :
 * <ul>
 * <li>Un unique Canvas dans lequel on dessine.</li>
 * <li>Un CRectangle de la taille du canvas pour pouvoir cacher les outils dessous</li>
 * <li>Un WidgetOutils qui sont les outils de dessin.</li>
 * <li>Un WidgetCouleurTaille qui est l'outils pour choisir la couleur du pinceau</li>
 * <li>Deux Points pour donner la position des deux widgets</li>
 * </ul>
 * <p>
 * 
 * @see Canvas
 * @see WidgetOutils
 * @see WidgetCouleurTaille
 * @see Point
 * 
 * @author ANDRIANIRINA Tojo
 * @author GABRIEL Damien
 * @author NGUYEN Julie
 */
public class Application extends JFrame {
	/**
	 * Le Canvas de la fenêtre. On dessine dessus et les CElements sont placés dessus.
	 * @see Application#Application()
	 */
	private Canvas canvas;
	
	/**
	 * Le Canvas de crossing.
	 * @see Application#Application()
	 */
	private Canvas canvas_trace;
	
	/**
	 * Le widget contenant les outils de dessin : pinceau, gomme, pot et formes.
	 * @see Application#Application()
	 */
	private WidgetOutils widgetOutils;
	
	/**
	 * Le widget contenant l'outils du pinceau, spécifiquement : la couleur et la taille personnalisés. 
	 * @see Application#Application()
	 */
	private WidgetCouleurTaille widgetcouleurtaille;

	/**
	 * Le point de positionnement du Widget Pinceau.
	 * @see Application#Application()
	 */
	private Point positionWidgetP;
	
	/**
	 * Le point de positionnement du Widget CouleurTaille.
	 * @see Application#Application()
	 */
	private Point positionWidgetCT;		
		
	/**
	 * Constructeur de Application.
	 * <p>A la construction d'un objet Application, on met le titre de la fenêtre à
	 * "Application Dessin - A.G.N" et on affiche la fenêtre en plein écran. 
	 * On ajoute les widgets de couleur et pinceau.</p>
	 */
	public Application () throws IOException {

		super("Application Dessin - A.G.N");

		Dimension minsize = new Dimension(600,600);
		//this.setPreferredSize(minsize);
		this.setMinimumSize(minsize);
		//this.setExtendedState(this.MAXIMIZED_BOTH);

		positionWidgetP = new Point(400, 50);
		positionWidgetCT = new Point (100, 100);		

		canvas = new Canvas(minsize.width, minsize.height);	
		canvas.setAntialiased(true);
		getContentPane().add(canvas);
				
		// Ajout un marquage pour la trace
		CrossingTrace ct = new CrossingTrace(canvas) ;
		ct.attachTo(canvas);
		canvas.setVisible(true);
		canvas.setOpaque(true);				
		
		widgetOutils = new WidgetOutils(canvas, positionWidgetP);
		canvas.addShape(widgetOutils);
		
		// widgetOutils.addTag("NonDrawable");

//		BarCouleur bc = new BarCouleur ("images/couleurBar.png", positionWidgetCT, canvas, widgetOutils);
//		BarCouleur bc = new BarCouleur ("images/couleurBar.png", positionWidgetCT, canvas, widgetOutils, pinceau);
//		BarTaille bt = new BarTaille ("images/tailleBar.png", positionWidgetCT_taille, canvas, widgetOutils);
		
//		bc.addTag("BarColor");
//		bc.addTag("NonDrawable");
//		bt.addTag("BarTaille");		
//		bt.addTag("NonDrawable");		

		widgetcouleurtaille = new WidgetCouleurTaille (canvas, positionWidgetCT, widgetOutils);
//		widgetcouleurtaille.addTag("NonDrawable");
		
		// MENU RADIAL - ABANDONE
//		QuitMenu_remake qm = new QuitMenu_remake(canvas);
//		QuitMenu qm = new QuitMenu(canvas);
				
		this.setGlassPane(canvas);
		this.getGlassPane().setVisible(true);
		
		Utilitaires.addDragger(canvas);
		
		pack();
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		canvas.requestFocusInWindow();
		
	}
	
	

}
