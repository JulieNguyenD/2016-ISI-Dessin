package elements;

import java.awt.Color;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import fr.lri.swingstates.canvas.CImage;
import fr.lri.swingstates.canvas.CRectangle;
import fr.lri.swingstates.canvas.CStateMachine;
import fr.lri.swingstates.canvas.Canvas;
import fr.lri.swingstates.canvas.transitions.LeaveOnTag;
import fr.lri.swingstates.canvas.transitions.EnterOnTag;
import fr.lri.swingstates.sm.State;
import fr.lri.swingstates.sm.Transition;
import fr.lri.swingstates.sm.transitions.Press;
import fr.lri.swingstates.sm.transitions.Release;
import widgets.WidgetOutils;
/**
 * <b>CImage pour la Barre de Couleur </b>
 * <p>Cette barre de couleur a sa propre CStateMachine. <br/>
 * Lorsque l'on crosse la barre, la couleur des autres éléments est changée si l'outils est actif.</p>
 * 
 * @see CImage
 * 
 * @author ANDRIANIRINA Tojo
 * @author GABRIEL Damien
 * @author NGUYEN Julie
 */
public class BarCouleur extends CImage {
	
	/**
	 * La CStateMachine de BarCouleur.
	 */
	private CStateMachine sm;
	
	/**
	 * La couleur que l'on récupère au moment du crossing.
	 */
	private Color color;
	
	/**
	 * Le rectangle dans lequel on affiche la couleur que l'on a crossé.
	 */
	private CRectangle rectangleTest;
	
	/**
	 * Les BufferedImage pour la barre. L'original et la re-scaled.
	 */
	private BufferedImage biScaled, bi;
	
	/**
	 * Le WidgetOutils, pour pouvoir avoir accès aux autres outils.
	 * @see WidgetOutils
	 */
	private WidgetOutils widgetOutil;

	/**
	 * Constructeur de BarCouleur.
	 * <p>A la création de BarCouleur, on initialise l'image avec le path et sa position.<br/>
	 * On fournit aussi le widgetOutils qui est le widget principal.</p>
	 * @param path : chemin vers l'image.
	 * @param position : position de départ de l'image.
	 * @param canvas : canvas sur lequel on place le CImage.
	 * @param widgetOutil : le WidgetOutils, widget principal.
	 * 
	 * @see WidgetOutils
	 * 
	 * @throws IOException
	 */
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
						
						if (widgetOutil.getPinceau().isEstActif()) widgetOutil.getPinceau().setCouleurPinceau(color);
						if (widgetOutil.getForme().isEstActif()) widgetOutil.getForme().setCouleur(color);
						if (widgetOutil.getPot().isEstActif()) widgetOutil.getPot().setCouleurPot(color);
						
//						ArrayList <CShape> outils = widgetOutil.getOutilsList();
//						for (int i = 0; i < outils.size(); i ++){
//							// Cas pour pinceau
//							if (outils.get(i).getClass().getName().equals("elements.Pinceau")){
//								Pinceau pinceau = (Pinceau)outils.get(i);
//								if (pinceau.isEstActif()){
//									pinceau.setCouleurPinceau(color);
//								}
//							}
//							
//							// Cas pour pour formes
//							if (outils.get(i).getClass().getName().equals("elements.Forme")){
//								Forme forme = (Forme)outils.get(i);
//								if (forme.isEstActif()){
//									forme.setCouleur(color);
//								}
//							}
//							
//						}
						
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
	}

	/**
	 * Getter de Color
	 * @return : la couleur de BarCouleur.
	 */
	public Color getColor (){
		return this.color;
	}		
	
	/**
	 * Fonction qui fait un scale de BufferImage
	 * @param bi : l'image de départ
	 * @param scaleValue : la valeur pour le scale
	 * @return l'image qui a été redimentionnée
	 */
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
