import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JFrame;

import fr.lri.swingstates.canvas.Canvas;

/**
 * <b>Fenêtre de l'application.</b>
 * <p>C'est une JFrame qui affiche en plein écran le canvas avec ses composants.
 * Elle a :
 * <ul>
 * <li>Un unique Canvas dans lequel on dessine.</li>
 * <li>Un WidgetPinceau qui sont les outils de dessin.</li>
 * <li>Un WidgetCouleur qui est l'outils pour choisir la couleur et la taille du pinceau</li>
 * <li>Deux Points pour donner la position des deux widgets</li>
 * </ul>
 * <p>
 * 
 * @see Canvas
 * @see WidgetPinceau
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
	 * Le widget contenant les outils de dessin : pinceau, gomme, pot et formes.
	 * @see Application#Application()
	 */
	private WidgetPinceau widgetpinceau;
	
	/**
	 * Le point de positionnement du Widget Pinceau.
	 * @see Application#Application()
	 */
	private Point positionWidgetP;
	
	public Application () {
		super("Application Dessin - A.G.N");
		
		Dimension minsize = new Dimension(600,600);
		//this.setPreferredSize(minsize);
		this.setMinimumSize(minsize);
		this.setExtendedState(this.MAXIMIZED_BOTH);
		
		positionWidgetP = new Point(400, 100);
		
		canvas = new Canvas(minsize.width, minsize.height);		
		getContentPane().add(canvas);
		
		widgetpinceau = new WidgetPinceau(canvas, positionWidgetP);
		canvas.addShape(widgetpinceau);
		
		pack();
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		canvas.requestFocusInWindow();
		
	}

}
