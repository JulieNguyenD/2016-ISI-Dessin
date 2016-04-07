package elements;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import fr.lri.swingstates.canvas.CImage;
import fr.lri.swingstates.canvas.CShape;
import fr.lri.swingstates.canvas.CStateMachine;
import fr.lri.swingstates.canvas.Canvas;
import fr.lri.swingstates.canvas.transitions.EnterOnTag;
import fr.lri.swingstates.canvas.transitions.LeaveOnTag;
import fr.lri.swingstates.sm.State;
import fr.lri.swingstates.sm.Transition;
import fr.lri.swingstates.sm.transitions.Press;
import fr.lri.swingstates.sm.transitions.Release;
import widgets.WidgetOutils;

public class BarTaille extends CImage{
	int taille;
	private BufferedImage biScaled;
	private BufferedImage bi;
	private CStateMachine sm;
	private WidgetOutils widgetOutil;

	public BarTaille(String path, Point2D position, Canvas canvas, WidgetOutils widgetOutil) throws IOException {
		super(path, position);
		this.widgetOutil = widgetOutil;
		
		this.setStroke(new BasicStroke(0));
		this.scaleBy(0.2);
		
		bi = ImageIO.read(new File(path));
		int heightOriginal = bi.getHeight();
		int widthOriginal = bi.getWidth();	
		
		biScaled = BarCouleur.scale (bi, 0.2);
		int heightScaled = biScaled.getHeight();
        int widthScaled = biScaled.getWidth();        
        
        this.translateBy(- (widthOriginal / 2 - widthScaled /2), - (heightOriginal / 2 - heightScaled /2) );
                
        this.addTo(canvas);
        
        sm = new CStateMachine (){
			public State out = new State() {
				public void enter() {					
					setFillPaint(Color.white);
				}
				
				Transition toOver = new EnterOnTag("BarTaille", ">> over") {};		
				
				Transition pressOut = new Press (BUTTON3, ">> disarmed"){};
			};

			public State over = new State() {								
				Transition leave = new LeaveOnTag("BarTaille",">> out") {};
				Transition arm = new Press(BUTTON3, ">> armed") {
					public void action (){						
					}
				};

			};

			public State armed = new State() {
				Transition disarm = new LeaveOnTag("BarTaille", ">> out") {
					public void action (){
						double maxY = this.getShape().getMaxY();
						double thisY = getPoint().getY();
						double ecart = maxY - thisY;
						int taille = (int) ((ecart / heightScaled) * widthScaled);
						
//						widgetOutil.getPinceau().setEstActif(true);
//						widgetOutil.getPinceau().setTaille(taille);
						
						if (widgetOutil.getPinceau().isEstActif()) widgetOutil.getPinceau().setTaille(taille);
						if (widgetOutil.getForme().isEstActif()) widgetOutil.getForme().setTaille(taille);
												
//						ArrayList <CShape> outils = widgetOutil.getOutilsList();
//						for (int i = 0; i < outils.size(); i ++){
//							// Cas pour pinceau
//							if (outils.get(i).getClass().getName().equals("elements.Pinceau")){
//								System.out.println ("Jesuis contennt PINCEAU !!!!!!!!!!!!!!!!!!!!!!!");
//								Pinceau pinceau = (Pinceau)outils.get(i);
//								if (pinceau.isEstActif()){
//									pinceau.setTaille(taille);
//								}
//							}
//							
//							// Cas pour pour formes
//							if (outils.get(i).getClass().getName().equals("elements.Forme")){
//								System.out.println ("Jesuis contennt FORME !!!!!!!!!!!!!!!!!!!!!!!");
//								Forme forme = (Forme)outils.get(i);
//								if (forme.isEstActif()){
//									forme.setTaille(taille);
//								}
//							}
//							
//						}
					}
				};
				Transition act = new Release(BUTTON3, ">> over") {};
			};

			public State disarmed = new State() {
				Transition rearm = new EnterOnTag("BarTaille", ">> armed") {};
				Transition cancel = new Release(BUTTON3	, ">> out") {
					public void action (){
				
					}
				};

			};
		};
		
		sm.attachTo(this);
	}
	
}
