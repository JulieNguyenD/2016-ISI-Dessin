package elements;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import fr.lri.swingstates.canvas.CImage;
import fr.lri.swingstates.canvas.CRectangle;
import fr.lri.swingstates.canvas.CStateMachine;
import fr.lri.swingstates.canvas.Canvas;
import fr.lri.swingstates.canvas.transitions.EnterOnShape;
import fr.lri.swingstates.canvas.transitions.LeaveOnShape;
import fr.lri.swingstates.sm.State;
import fr.lri.swingstates.sm.Transition;
import fr.lri.swingstates.sm.transitions.Press;
import fr.lri.swingstates.sm.transitions.Release;

public class BarCouleur extends CImage{
	
	private CStateMachine sm;	
	private Color color;
	private CRectangle rectangleTest;

	public BarCouleur(String path, Point2D position, Canvas canvas) throws IOException {
		super(path, position);
		BufferedImage bi = ImageIO.read(new File(path));		
		//int hauteur = bi.getHeight();
        //int largeur = bi.getWidth();
        
        this.addTo(canvas);
        rectangleTest = new CRectangle (600, 100, 50,50);
        rectangleTest.addTo(canvas);
		
							
		sm = new CStateMachine (){
			public State out = new State() {
				public void enter() {					
					setFillPaint(Color.white);
				}
				
				Transition toOver = new EnterOnShape(">> over") {};
				Transition pressOut = new Press (">> disarmed") {};
			};

			public State over = new State() {
				public void enter() {					
					setFillPaint(Color.gray);
				}				
				
				Transition leave = new LeaveOnShape(">> out") {};
				Transition arm = new Press(BUTTON1,">> armed") {
					public void action (){						
					}
				};

			};

			public State armed = new State() {
				public void enter() {
					setFillPaint(Color.blue);
				}

				Transition disarm = new LeaveOnShape(">> disarmed") {
					public void action (){
						int x = (int) (getPoint().getX() - position.getX());
						int y = (int) (getPoint().getY() - position.getY());
						
						color = new Color(bi.getRGB(x, y));
						
						
						rectangleTest.setFillPaint(color);
						
						int red = color.getRed();
						int green = color.getGreen();
						int blue = color.getBlue();
						System.out.println("red = " + red + "green = " + green + "blue = "+ blue);
						
						System.out.println("test reussi =========================");
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

				Transition rearm = new EnterOnShape(">> armed") {};
				Transition cancel = new Release(BUTTON1, ">> out") {};

			};
		};
		
		sm.attachTo(this);
	}
	
	public Color getColor (){
		return this.color;
	}

}
