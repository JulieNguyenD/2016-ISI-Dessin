package elements;

import java.awt.geom.Point2D;

import fr.lri.swingstates.canvas.CImage;
import fr.lri.swingstates.canvas.CShape;
import fr.lri.swingstates.canvas.CStateMachine;
import fr.lri.swingstates.canvas.Canvas;
import fr.lri.swingstates.canvas.transitions.EnterOnShape;
import fr.lri.swingstates.canvas.transitions.LeaveOnShape;
import fr.lri.swingstates.canvas.transitions.PressOnShape;
import fr.lri.swingstates.sm.State;
import fr.lri.swingstates.sm.Transition;
import fr.lri.swingstates.sm.transitions.Move;
import fr.lri.swingstates.sm.transitions.Press;
import fr.lri.swingstates.sm.transitions.Release;

/**
 * <b>CImage pour la gomme</b>
 * <p>La gomme a sa propre CStateMachine. 
 * Lorsque l'on crosse la gomme, sa fonction est activée 
 * et une boîte apparaît pour choisir sa fonction.</p>
 * 
 * @see CImage
 * 
 * @author ANDRIANIRINA Tojo
 * @author GABRIEL Damien
 * @author NGUYEN Julie
 */
public class Gomme extends CImage {
	
	/**
	 * Booléen indiquant si la gomme est actif.
	 */
	private boolean estActif;
	
	/**
	 * Le canvas sur lequel on ajoute la CStateMachine
	 * @see Canvas
	 */
	private Canvas canvas;
	
	/**
	 * La StateMachine de la Gomme. Elle permet le Crossing.
	 * @see CStateMachine
	 */
	private CStateMachine sm;

	/**
	 * Constructeur de Gomme.
	 * <p>A la création d'une Gomme.
	 * Le gomme n'est pas actif.<br/>
	 * On instancie le canvas et on attache le gomme au Canvas.</p> 
	 * 
	 * @param path : Le chemin vers l'image.
	 * @param position : La position de départ de l'image (ici le coin supérieur gauche de l'image)
	 */
	public Gomme(String path, Point2D position, Canvas canvas) {
		super(path, position);
		this.estActif = false;
		this.canvas = canvas;
		
		this.addTo(canvas);		
	}
	
	/**
	 * Retourne si la Gomme est actif ou non.
	 * @return true si la Gomme est actif, false sinon.
	 */
	public boolean isEstActif() {
		return estActif;
	}

	/**
	 * Met à jour l'état du Gomme. S'il est actif, on met � true ; sinon false.
	 * @param estActif : le nouvel état de la Gomme, false ou true.
	 */
	public void setEstActif(boolean estActif) {
		this.estActif = estActif;
	}
	
	public void addGommeStateMachine(CShape image) {
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

}
