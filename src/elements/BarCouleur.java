package elements;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import fr.lri.swingstates.canvas.CImage;
import fr.lri.swingstates.canvas.CNamedTag;
import fr.lri.swingstates.canvas.CRectangle;
import fr.lri.swingstates.canvas.CStateMachine;
import fr.lri.swingstates.canvas.Canvas;
import fr.lri.swingstates.canvas.transitions.EnterOnShape;
import fr.lri.swingstates.canvas.transitions.LeaveOnShape;
import fr.lri.swingstates.canvas.transitions.LeaveOnTag;
import fr.lri.swingstates.debug.StateMachineVisualization;
import fr.lri.swingstates.canvas.transitions.EnterOnTag;
import fr.lri.swingstates.sm.State;
import fr.lri.swingstates.sm.Transition;
import fr.lri.swingstates.sm.transitions.Press;
import fr.lri.swingstates.sm.transitions.Release;

public class BarCouleur extends CImage{
	
	private CStateMachine sm;	
	private Color color;
	private CRectangle rectangleTest;
	// State Machine qui gere le dessin de pinceau 
	// A mettre dans WidgetCouleurTaille apres
	private CStateMachine smd;
	BufferedImage biScaled;
	BufferedImage bi;
	int newX;
	int newY;

	public BarCouleur(String path, Point2D position, Canvas canvas) throws IOException {
		super(path, position);
		this.addTag("images");
		
		this.scaleBy(0.2);
		
		bi = ImageIO.read(new File(path));
		int heightOriginal = bi.getHeight();
		int widthOriginal = bi.getWidth();	
		
		biScaled = scale (bi, 0.2);
		int heightScaled = biScaled.getHeight();
        int widthScaled = biScaled.getWidth();        
        
        this.translateBy(- (widthOriginal / 2 - widthScaled /2), - (heightOriginal / 2 - heightScaled /2) );
                
        this.addTo(canvas);
        rectangleTest = new CRectangle (100, 400, 50,50);
        rectangleTest.addTo(canvas);
		
							
        sm = new CStateMachine (){
			public State out = new State() {
				public void enter() {					
					setFillPaint(Color.white);
				}
				
				Transition toOver = new EnterOnTag("images", ">> over") {
					public void action (){
						System.out.println("entrée reussi dans le tag============================");
					}
				};
				
				
				Transition pressOut = new Press (">> disarmed") {};
			};

			public State over = new State() {
				public void enter() {					
					setFillPaint(Color.gray);
				}				
				
				Transition leave = new LeaveOnTag("images",">> out") {};
				Transition arm = new Press(BUTTON1,">> armed") {
					public void action (){						
					}
				};

			};

			public State armed = new State() {
				public void enter() {
					setFillPaint(Color.blue);
				}

				Transition disarm = new LeaveOnTag("images", ">> disarmed") {
					public void action (){
						int x = (int) (getPoint().getX() - position.getX());
						int y = (int) (getPoint().getY() - position.getY());
																	
						if (y >= heightScaled){
				        	y = heightScaled - 1 ;
				        } else if (y <= 0){
				        	y = 1;
				        }
				        
				        if (x >= widthScaled){
				        	x = widthScaled - 1;
				        } else if (x <=0){
				        	x = 1;
				        }
						
						color = new Color(biScaled.getRGB(x, y));											
						rectangleTest.setFillPaint(color);
						
						int red = color.getRed();
						int green = color.getGreen();
						int blue = color.getBlue();
					}
				};
				Transition act = new Release(BUTTON1, ">> over") {};
				
			};

			public State disarmed = new State() {
				Transition rearm = new EnterOnTag("images", ">> armed") {};
				Transition cancel = new Release(BUTTON1, ">> out") {};

			};
		};
		
		sm.attachTo(this);
		showStateMachine(sm);
		
	}
		
	
	public Color getColor (){
		return this.color;
	}	
	
	// Fonction qui fait un scale de BufferImage
	public static BufferedImage scale(BufferedImage bi, double scaleValue) {
        AffineTransform tx = new AffineTransform();
        tx.scale(scaleValue, scaleValue);
        AffineTransformOp op = new AffineTransformOp(tx,
                AffineTransformOp.TYPE_BILINEAR);
        BufferedImage biNew = new BufferedImage( (int) (bi.getWidth() * scaleValue),
                (int) (bi.getHeight() * scaleValue),
                bi.getType());
        return op.filter(bi, biNew);
	}
	
	public static void showStateMachine(CStateMachine sm) {
		JFrame viz = new JFrame();
		viz.getContentPane().add(new StateMachineVisualization(sm));
		viz.pack();
		viz.setVisible(true);
	}

}
