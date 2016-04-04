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
		//this.getTags();
		
		
		//System.out.println("Tous les tags ====" + this.getTags());
		
		this.scaleBy(0.2);
		
		bi = ImageIO.read(new File(path));
		int heightOriginal = bi.getHeight();
		int widthOriginal = bi.getWidth();	
		
		//System.out.println("heightOriginal = " + heightOriginal);
		//System.out.println("widthOriginal = " + widthOriginal);
		
		biScaled = scale (bi, 0.2);
		int heightScaled = biScaled.getHeight();
        int widthScaled = biScaled.getWidth();
        
        //System.out.println("heightScaled = "+ heightScaled);
		//System.out.println("widthScaled = " + widthScaled);
        
        newY  = (int) (position.getY() + (heightOriginal /2 - heightScaled /2) );
        newX  = (int) (position.getX() + (widthOriginal /2 - widthScaled /2) );
        
                
        
        
        this.addTo(canvas);
        rectangleTest = new CRectangle (100, 100, 50,50);
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
						//int x = (int) (getPoint().getX() - position.getX());
						//int y = (int) (getPoint().getY() - position.getY());
						
						int x = (int) (getPoint().getX() - newX);
						int y = (int) (getPoint().getY() - newY);
						
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
						
						//System.out.println("Name class = "+this.getClass().getName()+ "=============================");
						
						
						System.out.println("widthOriginal = " + widthOriginal);
						System.out.println("heightOriginal = " + heightOriginal);
						System.out.println("widthScaled = " + widthScaled);				        
				        System.out.println("heightScaled = "+ heightScaled);						
						System.out.println("******************************************");
						
						System.out.println("POINT COURANT X = " + getPoint().getX());
						System.out.println("POINT COURANT Y = " + getPoint().getY());
						System.out.println("POSITION IMAGE X = " + newX);
						System.out.println("POSITION IMAGE Y = " + newY);
						System.out.println("VALEUR FINAL X = " + x);
						System.out.println("VALEUR FINAL Y = " + y);
						System.out.println("******************************************");
						
						color = new Color(biScaled.getRGB(x, y));											
						rectangleTest.setFillPaint(color);
						
						//smd = Pinceau.addPinceauStateMachineDrawing (canvas, color, 1);
						
						int red = color.getRed();
						int green = color.getGreen();
						int blue = color.getBlue();
						// System.out.println("red = " + red + "green = " + green + "blue = "+ blue);
						
						// System.out.println("test reussi =========================");
					}
				};
				Transition act = new Release(BUTTON1, ">> over") {};
				
			};

			public State disarmed = new State() {
				/*public void enter() {
					setFillPaint(Color.white);
					// Faire ici les grandes actions
					// il y a un choix de couleur quelque part qui se fait
					
				}*/

				Transition rearm = new EnterOnTag("images", ">> armed") {};
				Transition cancel = new Release(BUTTON1, ">> out") {};

			};
		};
		
		sm.attachTo(this);
		//showStateMachine(sm);
		
		//sm.attachTo(this);
	}
	
	/*public void addBarCouleurStateMachine (){
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
						//int x = (int) (getPoint().getX() - position.getX());
						//int y = (int) (getPoint().getY() - position.getY());
						
						int x = (int) (getPoint().getX() - newX);
						int y = (int) (getPoint().getY() - newY);
						
						System.out.println("Name class = "+this.getClass().getName()+ "=============================");
						
						
						System.out.println("widthOriginal = " + widthOriginal);
						System.out.println("heightOriginal = " + heightOriginal);
						System.out.println("widthScaled = " + widthScaled);				        
				        System.out.println("heightScaled = "+ heightScaled);						
						System.out.println("******************************************");
						
						System.out.println("POINT COURANT X = " + getPoint().getX());
						System.out.println("POINT COURANT Y = " + getPoint().getY());
						System.out.println("POSITION IMAGE X = " + newX);
						System.out.println("POSITION IMAGE Y = " + newY);
						System.out.println("VALEUR FINAL X = " + x);
						System.out.println("VALEUR FINAL Y = " + y);
						System.out.println("******************************************");
						
						color = new Color(biScaled.getRGB(x, y));											
						rectangleTest.setFillPaint(color);
						
						//smd = Pinceau.addPinceauStateMachineDrawing (canvas, color, 1);
						
						int red = color.getRed();
						int green = color.getGreen();
						int blue = color.getBlue();
						// System.out.println("red = " + red + "green = " + green + "blue = "+ blue);
						
						// System.out.println("test reussi =========================");
					}
				};
				Transition act = new Release(BUTTON1, ">> over") {};
				
			};

			public State disarmed = new State() {
				public void enter() {
					setFillPaint(Color.white);
					// Faire ici les grandes actions
					// il y a un choix de couleur quelque part qui se fait
					
				}

				Transition rearm = new EnterOnTag("images", ">> armed") {};
				Transition cancel = new Release(BUTTON1, ">> out") {};

			};
		};
		
		sm.attachTo(this);
		showStateMachine(sm);
	}*/
	
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
