package elements;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EventObject;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import fr.lri.swingstates.canvas.CImage;
import fr.lri.swingstates.canvas.CNamedTag;
import fr.lri.swingstates.canvas.CRectangle;
import fr.lri.swingstates.canvas.CShape;
import fr.lri.swingstates.canvas.CStateMachine;
import fr.lri.swingstates.canvas.Canvas;
import fr.lri.swingstates.canvas.transitions.EnterOnShape;
import fr.lri.swingstates.canvas.transitions.LeaveOnShape;
import fr.lri.swingstates.canvas.transitions.LeaveOnTag;
import fr.lri.swingstates.debug.StateMachineVisualization;
import fr.lri.swingstates.canvas.transitions.EnterOnTag;
import fr.lri.swingstates.sm.State;
import fr.lri.swingstates.sm.StateMachine;
import fr.lri.swingstates.sm.Transition;
import fr.lri.swingstates.sm.transitions.Press;
import fr.lri.swingstates.sm.transitions.Release;
import main.Application;
import main.Lancement;
import widgets.WidgetOutils;

public class BarCouleur extends CImage{
	
	private CStateMachine sm;	
	private Color color;
	private CRectangle rectangleTest;
	// State Machine qui gere le dessin de pinceau 
	// A mettre dans WidgetCouleurTaille apres
	private BufferedImage biScaled;
	private BufferedImage bi;
	private WidgetOutils widgetOutil;

	public BarCouleur(String path, Point2D position, Canvas canvas, WidgetOutils widgetOutil) throws IOException {
		super(path, position);
		this.widgetOutil = widgetOutil;
		
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
				Transition toOver = new EnterOnTag("BarColor", ">> over") {
					public void action (){
						System.out.println("entrée reussi dans le tag============================");
					}
				};						
				
				Transition pressOut = new Press (BUTTON3,">> disarmed"){};
								
			};

			public State over = new State() {								
				Transition leave = new LeaveOnTag("BarColor",">> out") {};
				Transition arm = new Press(BUTTON3, ">> armed") {};

			};

			public State armed = new State() {
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
						
//						widgetOutil.getPinceau().setEstActif(true);
//						widgetOutil.getPinceau().setCouleurPinceau(color);
						
						ArrayList <CShape> outils = widgetOutil.getOutilsList();
						for (int i = 0; i < outils.size(); i ++){
							// Cas pour pinceau
							if (outils.get(i).getClass().getName().equals("elements.Pinceau")){
								Pinceau pinceau = (Pinceau)outils.get(i);
								if (pinceau.isEstActif()){
									pinceau.setCouleurPinceau(color);
								}
							}
							
							// Cas pour pour formes
							if (outils.get(i).getClass().getName().equals("elements.Forme")){
								Forme forme = (Forme)outils.get(i);
								if (forme.isEstActif()){
									forme.setCouleur(color);
								}
							}
							
						}
						
					}
				};
				Transition act = new Release(BUTTON3, ">> over") {};
			};

			public State disarmed = new State() {
				Transition rearm = new EnterOnTag("BarColor",">> armed") {};
				Transition cancel = new Release(BUTTON3	, ">> out") {};

			};
		};
		
		sm.attachTo(this);		
//		Lancement.showStateMachine(sm);
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
}
