package elements;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Point2D;

import fr.lri.swingstates.canvas.CImage;
import fr.lri.swingstates.canvas.CPolyLine;
import fr.lri.swingstates.canvas.CShape;
import fr.lri.swingstates.canvas.CStateMachine;
import fr.lri.swingstates.canvas.Canvas;
import fr.lri.swingstates.canvas.transitions.PressOnShape;
import fr.lri.swingstates.canvas.transitions.EnterOnShape;
import fr.lri.swingstates.canvas.transitions.LeaveOnShape;
import fr.lri.swingstates.sm.*;
import fr.lri.swingstates.sm.transitions.Drag;
import fr.lri.swingstates.sm.transitions.Move;
import fr.lri.swingstates.sm.transitions.Press;
import fr.lri.swingstates.sm.transitions.Release;

/**
 * <b>CImage pour le pinceau</b>
 * <p>Le pinceau a sa propre CStateMachine. 
 * Lorsque l'on crosse le pinceau, sa fonction est activée 
 * et une boîte apparaît pour choisir la taille et la couleur.</p>
 * 
 * @see CImage
 * 
 * @author ANDRIANIRINA Tojo
 * @author GABRIEL Damien
 * @author NGUYEN Julie
 */
public class Pinceau extends CImage {
	
	/**
	 * La couleur du pinceau. 
	 * @see Color
	 */
	private Color couleurPinceau;
	
	/**
	 * La taille du pinceau.
	 */
	private int taille;
	
	/**
	 * Booléen indiquant si le pinceau est actif.
	 */
	private boolean estActif;
	
	/**
	 * Le canvas sur lequel on ajoute la CStateMachine
	 * @see Canvas
	 */
	private Canvas canvas;
	
	/**
	 * La StateMachine du pinceau. Elle permet le Crossing.
	 * @see CStateMachine
	 */
	private CStateMachine sm;
	
	/**
	 * La StateMachine du dessin avec le pinceau. Elle permet de dessiner avec le pinceau.
	 * @see CStateMachine
	 */
	private static CStateMachine smd;	
	
	/**
	 * La trace de dessin avec le pinceau.
	 * @see CPolyline
	 */
	private static CPolyLine line;

	/**
	 * Constructeur de Pinceau.
	 * <p>A la création d'un Pinceau, la couleur de base est noir.
	 * La taille du trait est de 1 et le pinceau n'est pas actif.<br/>
	 * On instancie le canvas et on attache le pinceau au Canvas.</p> 
	 * 
	 * @param path : Le chemin vers l'image.
	 * @param position : La position de départ de l'image (ici le coin supérieur gauche de l'image)
	 */
	public Pinceau(String path, Point2D position, Canvas canvas) {
		super(path, position);
		this.couleurPinceau = Color.BLACK;
		this.taille = 1;
		this.estActif = false;
		this.canvas = canvas;
		
		this.addTo(canvas);		
	}
	
	/**
	 * Getter de couleurPinceau : retourne la couleur du pinceau. 
	 * @see Color
	 * @return la couleur du Pinceau
	 */
	public Color getCouleurPinceau() {
		return couleurPinceau;
	}

	/**
	 * Met à jour la couleur du Pinceau avec la nouvelle couleur. 
	 * @param couleurPinceau : la nouvelle couleur du Pinceau
	 */
	public void setCouleurPinceau(Color couleurPinceau) {
		this.couleurPinceau = couleurPinceau;
	}

	/**
	 * Retourne la taille du Pinceau
	 * @return la taille du Pinceau
	 */
	public int getTaille() {
		return taille;
	}

	/**
	 * Met à jour la taille du Pinceau avec la nouvelle taille.
	 * @param taille : la nouvelle taille du Pinceau
	 */
	public void setTaille(int taille) {
		this.taille = taille;
	}

	/**
	 * Retourne si le pinceau est actif ou non.
	 * @return true si le pinceau est actif, false sinon.
	 */
	public boolean isEstActif() {
		return estActif;
	}

	/**
	 * Met à jour l'état du Pinceau. S'il est actif, on met à true ; sinon false.
	 * @param estActif : le nouvel état du pinceau, false ou true.
	 */
	public void setEstActif(boolean estActif) {
		this.estActif = estActif;
	}	
	
	public void addPinceauStateMachine(CShape image) {
		sm = new CStateMachine() {
			State idle = new State() {
				Transition t1 = new Press (BUTTON1, ">> press") {
					public void action() {

					}					
				};
				
				Transition t2 = new PressOnShape (BUTTON1, ">> debut") {
					public void action() {
						image.scaleBy(2.0);
					}					
				};
			};
			
			State press = new State() {
				Transition t3 = new Release (">> idle") {
					public void action() {
					}
				};
				
				Transition t4 = new EnterOnShape (">> debut") {
					public void action() {
						image.scaleBy(2.0);
					}
				};
			};
			
			State debut = new State() {
				Transition t5 = new Release (">> idle") {
					public void action() {
						image.scaleBy(0.50);

					}
				};
				
				Transition t6 = new LeaveOnShape (">> fin") {
					public void action() {
						image.scaleBy(0.50);
					}
				};
			};
			
			State fin = new State() {
				Transition t7 = new Move (">> press") {
					public void action() {
						
					}
				};
				
				Transition t8 = new Release(">> idle") {
					public void action() {
					}
				};
			};
		};
		
		sm.attachTo(image);
	}
	
	
	public static CStateMachine addPinceauStateMachineDrawing(Canvas canvas, Color couleur, int taille) {
		smd = new CStateMachine() {
			State start = new State() {
				Transition press = new Press(BUTTON1, ">> draw") {
					public void action() {
						//Canvas canvas = (Canvas) getEvent().getSource();
						line = canvas.newPolyLine(getPoint());
						line.setStroke(new BasicStroke(taille));
						line.setOutlinePaint(couleur);
						//line.setFilled(false);
					}
				};
			};
			
			State draw = new State() {
				Transition draw = new Drag(BUTTON1) {
					public void action() {
						line.lineTo(getPoint());
					}
				};
				Transition stop = new Release(BUTTON1, ">> start") {
					public void action() {
						line.lineTo(getPoint());
						//fireEvent(new ShapeCreatedEvent(PathTool.this, line));
					}
				};
			};
		};		
		smd.attachTo(canvas);
		
		return smd;
	}
	
	
}
