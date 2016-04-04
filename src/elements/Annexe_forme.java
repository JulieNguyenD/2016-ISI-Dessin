package elements;

import java.awt.Color;
import java.awt.geom.Point2D;

import fr.lri.swingstates.canvas.CImage;
import fr.lri.swingstates.canvas.CStateMachine;
import fr.lri.swingstates.canvas.Canvas;
import fr.lri.swingstates.canvas.transitions.EnterOnShape;
import fr.lri.swingstates.canvas.transitions.LeaveOnShape;
import fr.lri.swingstates.sm.State;
import fr.lri.swingstates.sm.Transition;
import fr.lri.swingstates.sm.transitions.Press;
import fr.lri.swingstates.sm.transitions.Release;

public class Annexe_forme extends CImage {
	
	private String forme;
	
	/**
	 * Le canvas sur lequel on ajoute la CStateMachine
	 * @see Canvas
	 */
	private Canvas canvas;
	
	private CStateMachine sm;

	public Annexe_forme(String chemin, Point2D position_depart, Canvas canvas, String forme) {
		super(chemin, position_depart);
		// TODO Auto-generated constructor stub
		this.canvas = canvas;
		this.forme = forme;

		this.addTo(canvas);
		
		sm = new CStateMachine (){
			public State out = new State() {
				public void enter() {					
					setFillPaint(Color.white);
				}
				
				Transition toOver = new EnterOnShape(">> over") {};
				Transition pressOut = new Press (">> disarmed") {};
			};

			public State over = new State() {
				public void enter() {					
					setFillPaint(Color.gray);
				}				
				
				Transition leave = new LeaveOnShape(">> out") {};
				Transition arm = new Press(BUTTON1,">> armed") {};

			};

			public State armed = new State() {
				public void enter() {
					setFillPaint(Color.blue);
				}

				Transition disarm = new LeaveOnShape(">> disarmed") {
					public void action (){						
						System.out.println("test reussi ================" + forme);
					}
				};
				Transition act = new Release(BUTTON1, ">> over") {};
				
			};

			public State disarmed = new State() {
				public void enter() {
					setFillPaint(Color.white);
					// Faire ici les grandes actions
					// il y a un choix de couleur quelque part qui se fait
					
				}

				Transition rearm = new EnterOnShape(">> armed") {};
				Transition cancel = new Release(BUTTON1, ">> out") {};

			};
		};
		
		sm.attachTo(this);

	}

	public String getForme() {
		return forme;
	}

	public void setForme(String forme) {
		this.forme = forme;
	}
	
	public void montrer(boolean b) {
		this.setDrawable(b);
	}

}
