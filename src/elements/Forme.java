package elements;

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
import widgets.widget_sous_barre.ChoixFormes;

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
	private CStateMachine sm, smd;

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
	
	public void addFormeStateMachine(Forme forme, WidgetOutils widget) {
		sm = new CStateMachine() {
			
			State idle = new State() {
				Transition t1 = new Press (BUTTON3, ">> press") {
					public void action() {

					}					
				};
				
				Transition t2 = new PressOnShape (BUTTON3, ">> debut") {
					public void action() {
						forme.setStroke(Utilitaires.augmente);
						//widget.montrer(true);
						widget.getChoixFormes().montrer(true);
					}					
				};
			};
			
			State press = new State() {
				Transition t3 = new Release (BUTTON3,">> idle") {
					public void action() {
						//widget.montrer(false);
						widget.getChoixFormes().montrer(false);
					}
				};
				
				Transition t4 = new EnterOnShape (">> debut") {
					public void action() {
						forme.setStroke(Utilitaires.augmente);
						//widget.montrer(true);
						widget.getChoixFormes().montrer(true);
					}
				};
			};
			
			State debut = new State() {
				Transition t5 = new Release (BUTTON3,">> idle") {
					public void action() {
						forme.setStroke(Utilitaires.normal);
						//widget.montrer(false);
						widget.getChoixFormes().montrer(false);

					}
				};
				
				Transition t6 = new LeaveOnShape (">> fin") {
					public void action() {
						forme.setStroke(Utilitaires.normal);
						
						forme.setEstActif(true);
						widget.getPinceau().setEstActif(false);
						widget.getGomme().setEstActif(false);
						widget.getPot().setEstActif(false);
						
						boolean b = forme.isEstActif();
						System.out.println("La forme est : " + b);
						
						//widget.montrer(true);
						widget.getChoixFormes().montrer(true);
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
						widget.getChoixFormes().montrer(false);
					}
				};
			};
		};
		
		sm.attachTo(forme);
	}
	
	


	public CStateMachine createFormeStateMachineDrawing(Forme forme, Canvas canvas) {		

		smd = new CStateMachine (){
			public State out = new State() {
				Transition toOver = new EnterOnTag("NonDrawable", ">> over") {};							
				Transition pressOut = new Press (BUTTON1,">> disarmed") {
					public boolean guard() {
						return forme.isEstActif();
					}
					public void action() {
//						line = canvas.newPolyLine(getPoint());
//						line.setStroke(new BasicStroke(forme.getTaille()));
//						line.setOutlinePaint(forme.getCouleurPinceau());
//						line.setFilled(false);
//						line.setPickable(false);
						System.out.println("forme ici");
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
