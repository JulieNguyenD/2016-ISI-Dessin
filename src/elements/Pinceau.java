package elements;

import java.awt.Color;
import java.awt.geom.Point2D;

import fr.lri.swingstates.canvas.CImage;
import fr.lri.swingstates.canvas.CShape;
import fr.lri.swingstates.canvas.CStateMachine;
import fr.lri.swingstates.canvas.Canvas;
import fr.lri.swingstates.canvas.transitions.PressOnShape;
import fr.lri.swingstates.canvas.transitions.EnterOnShape;
import fr.lri.swingstates.canvas.transitions.LeaveOnShape;
import fr.lri.swingstates.sm.*;
import fr.lri.swingstates.sm.transitions.Move;
import fr.lri.swingstates.sm.transitions.Press;
import fr.lri.swingstates.sm.transitions.Release;

/**
 * <b>CImage pour le pinceau</b>
 * <p>Le pinceau a sa propre CStateMachine. 
 * Lorsque l'on crosse le pinceau, sa fonction est activ�e 
 * et une bo�te appara�t pour choisir la taille et la couleur.</p>
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
	 * Bool�en indiquant si le pinceau est actif.
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
	 * Constructeur de Pinceau.
	 * <p>A la cr�ation d'un Pinceau, la couleur de base est noir.
	 * La taille du trait est de 1 et le pinceau n'est pas actif.</p> 
	 * 
	 * @param path : Le chemin vers l'image.
	 * @param position : La position de d�part de l'image (ici le coin sup�rieur gauche de l'image)
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
	 * Met � jour la couleur du Pinceau avec la nouvelle couleur. 
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
	 * Met � jour la taille du Pinceau avec la nouvelle taille.
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
	 * Met � jour l'�tat du Pinceau. S'il est actif, on met � true ; sinon false.
	 * @param estActif : le nouveau �tat du pinceau, false ou true.
	 */
	public void setEstActif(boolean estActif) {
		this.estActif = estActif;
	}
	
	public void addPinceauStateMachine(CShape image) {
		sm = new CStateMachine() {
			State idle = new State() {
				Transition t1 = new Press (BUTTON3, ">> press") {
					public void action() {
						
					}					
				};
				
				Transition t2 = new PressOnShape (BUTTON3, ">> debut") {
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
	
	public CStateMachine getSM() {
		return sm;
	}
	
	public void attacheStateMachine() {
		getSM().attachTo(this);
	}
	
}
