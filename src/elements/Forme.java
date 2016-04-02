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

public class Forme extends CImage {
	
	/**
	 * Booléen indiquant si la forme est actif.
	 */
	private boolean estActif;
	
	/**
	 * Le canvas sur lequel on ajoute la CStateMachine
	 * @see Canvas
	 */
	private Canvas canvas;
	
	/**
	 * La StateMachine de la Forme. Elle permet le Crossing.
	 * @see CStateMachine
	 */
	private CStateMachine sm;

	/**
	 * Constructeur de Forme.
	 * <p>A la création d'une Forme.
	 * Le forme n'est pas actif.<br/>
	 * On instancie le canvas et on attache le forme au Canvas.</p> 
	 * 
	 * @param path : Le chemin vers l'image.
	 * @param position : La position de départ de l'image (ici le coin supérieur gauche de l'image)
	 */
	public Forme(String path, Point2D position, Canvas canvas) {
		super(path, position);
		this.estActif = false;
		this.canvas = canvas;
		
		this.addTo(canvas);		
	}
	
	/**
	 * Retourne si le Forme est actif ou non.
	 * @return true si le Forme est actif, false sinon.
	 */
	public boolean isEstActif() {
		return estActif;
	}

	/**
	 * Met à jour l'état du Forme. S'il est actif, on met à true ; sinon false.
	 * @param estActif : le nouveau état du Forme, false ou true.
	 */
	public void setEstActif(boolean estActif) {
		this.estActif = estActif;
	}
	
	public void addFormeStateMachine(CShape image) {
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
