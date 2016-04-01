import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JFrame;

import fr.lri.swingstates.canvas.Canvas;

/**
 * Fenêtre de l'application.
 * C'est une JFrame qui contient le canvas
 * 
 * @author ANDRIANIRINA Tojo
 * @author GABRIEL Damien
 * @author NGUYEN Julie
 */
@SuppressWarnings("serial")
public class Application extends JFrame {
	
	private Canvas canvas;
	private Point positionWidgetP;
	private WidgetPinceau widgetpinceau;
	
	public Application () {
		super("Application Dessin - A.G.N");
		
		Dimension minsize = new Dimension(600,600);
		this.setPreferredSize(minsize);
		this.setMinimumSize(minsize);
		
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
