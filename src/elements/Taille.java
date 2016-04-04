package elements;

import java.awt.BasicStroke;
import java.awt.Color;

import javax.swing.JFrame;

import fr.lri.swingstates.canvas.CRectangle;
import fr.lri.swingstates.canvas.CStateMachine;
import fr.lri.swingstates.canvas.Canvas;
import fr.lri.swingstates.canvas.transitions.EnterOnShape;
import fr.lri.swingstates.canvas.transitions.LeaveOnShape;
import fr.lri.swingstates.debug.StateMachineVisualization;
import fr.lri.swingstates.sm.State;
import fr.lri.swingstates.sm.Transition;
import fr.lri.swingstates.sm.transitions.Press;
import fr.lri.swingstates.sm.transitions.Release;

public class Taille extends CRectangle {
	private CRectangle rectangle;
	private CStateMachine sm;
	private double tailleThis;

	public Taille (final double taille, Canvas canvas){
		super (0, 0, 50, 50);
		rectangle = canvas.newRectangle(5, 25, 40, taille);
		rectangle.rotateBy(95.0);	
		
		rectangle.setStroke(new BasicStroke(0));
		rectangle.setFillPaint(Color.BLACK);
		rectangle.setPickable(false);
		this.setFillPaint(Color.WHITE);
				
		rectangle.setParent(this);
		this.addTo(canvas);		
		
		this.below(rectangle);				
		
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
						tailleThis = taille;
						System.out.println("test reussi =========================");
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
	
	public double getColor() {
		return this.tailleThis;
		
	}
	
	public void montrer(boolean b) {
		this.setDrawable(b);
		this.rectangle.setDrawable(b);
	}
}
