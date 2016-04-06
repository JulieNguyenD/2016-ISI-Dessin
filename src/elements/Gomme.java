package elements;

import java.awt.BasicStroke;
import java.awt.geom.Point2D;

import fr.lri.swingstates.canvas.CImage;
import fr.lri.swingstates.canvas.CShape;
import fr.lri.swingstates.canvas.CStateMachine;
import fr.lri.swingstates.canvas.Canvas;
import fr.lri.swingstates.canvas.transitions.EnterOnShape;
import fr.lri.swingstates.canvas.transitions.EnterOnTag;
import fr.lri.swingstates.canvas.transitions.LeaveOnShape;
import fr.lri.swingstates.canvas.transitions.LeaveOnTag;
import fr.lri.swingstates.canvas.transitions.PressOnShape;
import fr.lri.swingstates.sm.State;
import fr.lri.swingstates.sm.Transition;
import fr.lri.swingstates.sm.transitions.Drag;
import fr.lri.swingstates.sm.transitions.Move;
import fr.lri.swingstates.sm.transitions.Press;
import fr.lri.swingstates.sm.transitions.Release;
import main.Utilitaires;
import widgets.WidgetOutils;
import widgets.widget_sous_barre.ChoixGomme;

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
	
	private CStateMachine smd;

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
	
	public void addGommeStateMachine(Gomme gomme, WidgetOutils widget) {
		sm = new CStateMachine() {
			
			State idle = new State() {
				Transition t1 = new Press (BUTTON3, CONTROL, ">> press") {
					public void action() {

					}					
				};
				
				Transition t2 = new PressOnShape (BUTTON3, CONTROL, ">> debut") {
					public void action() {
						gomme.setStroke(Utilitaires.augmente);
						//widget.montrer(true);
						widget.getChoixGomme().montrer(true);
					}					
				};
			};
			
			State press = new State() {
				Transition t3 = new Release (">> idle") {
					public void action() {
						//widget.montrer(false);
						widget.getChoixGomme().montrer(false);
					}
				};
				
				Transition t4 = new EnterOnShape (">> debut") {
					public void action() {
						gomme.setStroke(Utilitaires.augmente);
						//widget.montrer(true);
						widget.getChoixGomme().montrer(true);
					}
				};
			};
			
			State debut = new State() {
				Transition t5 = new Release (">> idle") {
					public void action() {
						gomme.setStroke(Utilitaires.normal);
						//widget.montrer(false);
						widget.getChoixGomme().montrer(false);

					}
				};
				
				Transition t6 = new LeaveOnShape (">> fin") {
					public void action() {
						gomme.setStroke(Utilitaires.normal);
						
						gomme.setEstActif(true);
						widget.getPinceau().setEstActif(false);
						widget.getForme().setEstActif(false);
						widget.getPot().setEstActif(false);
						
						boolean b = gomme.isEstActif();
						System.out.println("La gomme est : " + b);
						
						//widget.montrer(true);
						widget.getChoixGomme().montrer(true);
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
						//widget.montrer(false);
						widget.getChoixGomme().montrer(false);
					}
				};
			};
		};
		
		sm.attachTo(gomme);
	}
	
	


	public CStateMachine createGommeStateMachineDrawing(Gomme gomme, Canvas canvas) {		

		smd = new CStateMachine (){
			public State out = new State() {
				Transition toOver = new EnterOnTag("NonDrawable", ">> over") {};							
				Transition pressOut = new Press (BUTTON1,">> disarmed") {
					public boolean guard() {
						return gomme.isEstActif();
					}
					public void action() {
//						line = canvas.newPolyLine(getPoint());
//						line.setStroke(new BasicStroke(gomme.getTaille()));
//						line.setOutlinePaint(gomme.getCouleurPinceau());
//						line.setFilled(false);
//						line.setPickable(false);
						System.out.println("Suppression ici");
					}
				};
			};

			public State over = new State() {				
				Transition leave = new LeaveOnTag("NonDrawable",">> out") {};
				Transition arm = new Press(BUTTON1, ">> armed") {};
			};

			public State armed = new State() {
				Transition disarm = new LeaveOnTag("NonDrawable", ">> disarmed") {};
				Transition act = new Release(BUTTON1, ">> over") {};				
			};

			public State disarmed = new State() {
				Transition draw = new Drag (BUTTON1){
					public void action() {
//						line.lineTo(getPoint());
					}
				};
				Transition rearm = new EnterOnTag("NonDrawable", ">> armed") {
					public void action() {
//						line.remove();
					}
				};
				Transition cancel = new Release(BUTTON1, ">> out") {
					public void action() {
//						line.lineTo(getPoint());
					}
				};

			};						

		};		

//		smd.attachTo(this);
//		Utilitaires.showStateMachine(smd);
		
		return smd;
	}			
}