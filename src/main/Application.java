package main;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.io.IOException;

import javax.swing.JFrame;

import elements.BarCouleur;
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
	
//	/**
//	 * Le CRectangle blanc qui "remplace" le canvas. On cache les éléments en dessous.
//	 * @see Application#Application()
//	 */
//	private CRectangle hidden;
	
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

	public static CrossingTrace ct;
	
	public Application () throws IOException {

		super("Application Dessin - A.G.N");

		Dimension minsize = new Dimension(600,600);
		//this.setPreferredSize(minsize);
		this.setMinimumSize(minsize);
		this.setExtendedState(this.MAXIMIZED_BOTH);

		positionWidgetP = new Point(400, 100);
		positionWidgetCT = new Point (100, 100);

		canvas = new Canvas(minsize.width, minsize.height);	
		canvas.setAntialiased(true);
		getContentPane().add(canvas);
		
		
		
//		hidden = new CRectangle(0, 0, 500, 500);
//		hidden.setStroke(new BasicStroke(0));
//		hidden.setFillPaint(Color.WHITE);
//		canvas.addShape(hidden);
//		
//		hidden.belowAll();
		
				
		// Ajout un marquage pour la trace
		ct = new CrossingTrace(canvas) ;
		ct.attachTo(canvas);
		canvas.setVisible(true);
		canvas.setOpaque(true);		
				
		widgetOutils = new WidgetOutils(canvas, positionWidgetP);
		canvas.addShape(widgetOutils);
		
		BarCouleur bc = new BarCouleur ("images/couleurBar.png", positionWidgetCT, canvas, widgetOutils);
		bc.addTag("BarColor");
		bc.addTag("NonDrawable");

		addDragger(canvas);
		
		pack();
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		canvas.requestFocusInWindow();
		
	}
	
	/**
	 * Créé un CStateMachine pour les tag "draggable" et l'attache au canvas.
	 * <p>Les CElements avec un tag "draggable" peuvent être bougé sur le canvas.</p>
	 *
	 * @param canvas : canvas sur lequel on attache le CStateMachine
	 */
	public static void addDragger(Canvas canvas) {

		// Create the state machine and attach it to the canvas
		CStateMachine dragger = new CStateMachine(canvas) {

			Point2D pressLocation;
			Point2D shapeLocation;
			CShape shape;

			public State idling = new State() {
				// The mouse button "BUTTON3" is the right mouse button
				// ("BUTTON1" is the left mouse button)
				Transition down = new PressOnTag("draggable",
						CStateMachine.BUTTON3, ">> dragging") {
					public void action() {
						pressLocation = getPoint();
						shape = getShape();
						shapeLocation = new Point2D.Double(shape.getCenterX(),
								shape.getCenterY());
					};
				};
			};
			public State dragging = new State() {
				Transition move = new Drag() {
					public void action() {
						// Translate the shape (use shape.translateTo())
						pressLocation = getPoint();
						shape.translateTo(pressLocation.getX(), pressLocation.getY());
					};
				};
				Transition release = new Release(">> idling") {					
				};
			};
		};

	}

}
