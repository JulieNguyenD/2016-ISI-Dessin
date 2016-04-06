package elements;

import java.awt.Color;
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
import widgets.widget_sous_barre.ChoixPot;

/**
 * <b>CImage pour le pot</b>
 * <p>Le pot a sa propre CStateMachine. 
 * Lorsque l'on crosse le pot, sa fonction est activée 
 * et une boîte apparaît pour choisir la couleur.</p>
 * 
 * @see CImage
 * 
 * @author ANDRIANIRINA Tojo
 * @author GABRIEL Damien
 * @author NGUYEN Julie
 */
public class Pot extends CImage {
	
	/**
	 * La couleur du pot. 
	 * @see Color
	 */
	private Color couleurPot;
		
	/**
	 * Booléen indiquant si le pot est actif.
	 */
	private boolean estActif;
	
	/**
	 * Le canvas sur lequel on ajoute la CStateMachine
	 * @see Canvas
	 */
	private Canvas canvas;
	
	/**
	 * La StateMachine du Pot. Elle permet le Crossing.
	 * @see CStateMachine
	 */
	private CStateMachine sm, smd;

	/**
	 * Constructeur de Pot.
	 * <p>A la création d'un Pot, la couleur de base est blanc.
	 * Le pot n'est pas actif.<br/>
	 * On instancie le canvas et on attache le pot au Canvas.</p> 
	 * 
	 * @param path : Le chemin vers l'image.
	 * @param position : La position de départ de l'image (ici le coin supérieur gauche de l'image)
	 */
	public Pot(String path, Point2D position, Canvas canvas) {
		super(path, position);
		this.couleurPot = Color.WHITE;
		this.estActif = false;
		this.canvas = canvas;
		
		this.addTo(canvas);		
	}
	
	/**
	 * Getter de couleurPot : retourne la couleur du pot. 
	 * @see Color
	 * @return la couleur du Pot
	 */
	public Color getCouleurPot() {
		return couleurPot;
	}

	/**
	 * Met à jour la couleur du pot avec la nouvelle couleur. 
	 * @param couleurPot : la nouvelle couleur du pot
	 */
	public void setCouleurPot(Color couleurPot) {
		this.couleurPot = couleurPot;
	}
	
	/**
	 * Retourne si le Pot est actif ou non.
	 * @return true si le Pot est actif, false sinon.
	 */
	public boolean isEstActif() {
		return estActif;
	}

	/**
	 * Met à jour l'état du Pot. S'il est actif, on met à true ; sinon false.
	 * @param estActif : le nouvel état du Pot, false ou true.
	 */
	public void setEstActif(boolean estActif) {
		this.estActif = estActif;
	}
	
	public void addPotStateMachine(Pot pot, WidgetOutils widget) {
		sm = new CStateMachine() {
			
			State idle = new State() {
				Transition t1 = new Press (BUTTON3, CONTROL, ">> press") {
					public void action() {

					}					
				};
				
				Transition t2 = new PressOnShape (BUTTON3, CONTROL, ">> debut") {
					public void action() {
						pot.setStroke(Utilitaires.augmente);
						//widget.montrer(true);
						widget.getChoixPot().montrer(true);
					}					
				};
			};
			
			State press = new State() {
				Transition t3 = new Release (">> idle") {
					public void action() {
						//widget.montrer(false);
						widget.getChoixPot().montrer(false);
					}
				};
				
				Transition t4 = new EnterOnShape (">> debut") {
					public void action() {
						pot.setStroke(Utilitaires.augmente);
						//widget.montrer(true);
						widget.getChoixPot().montrer(true);
					}
				};
			};
			
			State debut = new State() {
				Transition t5 = new Release (">> idle") {
					public void action() {
						pot.setStroke(Utilitaires.normal);
						//widget.montrer(false);
						widget.getChoixPot().montrer(false);

					}
				};
				
				Transition t6 = new LeaveOnShape (">> fin") {
					public void action() {
						pot.setStroke(Utilitaires.normal);
						
						pot.setEstActif(true);
						widget.getPinceau().setEstActif(false);
						widget.getForme().setEstActif(false);
						widget.getGomme().setEstActif(false);
						
						boolean b = pot.isEstActif();
						System.out.println("Le pot est : " + b);
						
						//widget.montrer(true);
						widget.getChoixPot().montrer(true);
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
						widget.getChoixPot().montrer(false);
					}
				};
			};
		};
		
		sm.attachTo(pot);
	}
	
	


	public CStateMachine createPotStateMachineDrawing(Pot pot, Canvas canvas) {		

		smd = new CStateMachine (){
			public State out = new State() {
				Transition toOver = new EnterOnTag("NonDrawable", ">> over") {};							
				Transition pressOut = new Press (BUTTON1,">> disarmed") {
					public boolean guard() {
						return pot.isEstActif();
					}
					public void action() {
//						line = canvas.newPolyLine(getPoint());
//						line.setStroke(new BasicStroke(pot.getTaille()));
//						line.setOutlinePaint(pot.getCouleurPinceau());
//						line.setFilled(false);
//						line.setPickable(false);
						System.out.println("remplissage ici");
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
