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
import main.Application;
import widgets.WidgetOutils;

public class BarCouleur extends CImage{
	
	private CStateMachine sm;	
	private Color color;
	private CRectangle rectangleTest;
	// State Machine qui gere le dessin de pinceau 
	// A mettre dans WidgetCouleurTaille apres
	private CStateMachine smd;
	BufferedImage biScaled;
	BufferedImage bi;
	private WidgetOutils widgetPinceau;

	public BarCouleur(String path, Point2D position, Canvas canvas, WidgetOutils widgetPinceau) throws IOException {
		super(path, position);
		//this.addTag("BarColor");
		//this.addTag("NonDrawable");
		this.widgetPinceau = widgetPinceau;
		
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
				
				Transition toOver = new EnterOnTag("BarColor", ">> over") {
					public void action (){
						System.out.println("entrée reussi dans le tag============================");
					}
				};
				
				
				//Transition pressOut = new Press (">> disarmed") {};
			};

			public State over = new State() {								
				Transition leave = new LeaveOnTag("BarColor",">> out") {};
				Transition arm = new Press(BUTTON1,">> armed") {
					public void action (){						
					}
				};

			};

			public State armed = new State() {
				public void enter() {
					setFillPaint(Color.blue);
				}

				Transition disarm = new LeaveOnTag("BarColor", ">> out") {
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
						
						sm.setActive(false);
						sm.detach(canvas);
						
						widgetPinceau.smPinceau.detach(canvas);
						Pinceau pinceau = widgetPinceau.getPinceau();
						CStateMachine csm = pinceau.createPinceauStateMachineDrawing(canvas, color, 1);
						Application.ct.setActive (false);
						//Application.ct.detach(canvas);
						csm.setActive(true);
						csm.attachTo(canvas);
						//widgetPinceau.smPinceau = sm;//pinceau.createPinceauStateMachineDrawing(canvas, color, 1);
						//widgetPinceau.smPinceau.setActive(true);
						
						//widgetPinceau.smPinceau.attachTo(canvas);
						
						System.out.println("Activation SM APRES = "+ widgetPinceau.smPinceau.isActive());
						
						//sm.setActive(false);																	
						//smd = Pinceau.addPinceauStateMachineDrawing(canvas, color, 1);
						//showStateMachine(smd);
					}
				};
				Transition act = new Release(BUTTON1, ">> over") {};
				
			};

			/*public State disarmed = new State() {
				Transition rearm = new EnterOnTag("BarColor", ">> armed") {
					public void action (){
						//sm.setActive(true);
					}
				};
				Transition cancel = new Release(BUTTON1, ">> out") {
					public void action (){
						System.out.println("test de relachement ++++++++++++++++++++");
						Pinceau pinceau = widgetPinceau.getPinceau();
						CStateMachine smPinceau = pinceau.createPinceauStateMachineDrawing(canvas, color, 1);
						smPinceau.setActive(true);
						//smPinceau.attachTo(canvas);
						widgetPinceau.setSMPinceau(smPinceau);												
						(widgetPinceau.getSMPinceau()).setActive(true);
						showStateMachine(widgetPinceau.getSMPinceau());
						sm.setActive(false);
						//widgetPinceau.getSMPinceau().attachTo(canvas);
						System.out.println("Activation SM pinceau = "+ widgetPinceau.getSMPinceau().isActive());
						
						
						
						System.out.println("Activation SM AVANT = "+ widgetPinceau.smPinceau.isActive());
						
						widgetPinceau.smPinceau.setActive(true);
						
						System.out.println("Activation SM APRES = "+ widgetPinceau.smPinceau.isActive());
						
						System.out.println("test FIN relachement ++++++++++++++++++++");
						
					}
				};

			};*/
		};
		
		sm.attachTo(this);
		//sm.setActive(false);
		//showStateMachine(sm);
		
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
