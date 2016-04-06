package main;

import java.awt.BasicStroke;
import java.awt.geom.Point2D;

import javax.swing.JFrame;

import fr.lri.swingstates.canvas.CShape;
import fr.lri.swingstates.canvas.CStateMachine;
import fr.lri.swingstates.canvas.Canvas;
import fr.lri.swingstates.canvas.transitions.PressOnTag;
import fr.lri.swingstates.debug.StateMachineVisualization;
import fr.lri.swingstates.sm.JStateMachine;
import fr.lri.swingstates.sm.State;
import fr.lri.swingstates.sm.Transition;
import fr.lri.swingstates.sm.transitions.Drag;
import fr.lri.swingstates.sm.transitions.Release;

public class Utilitaires {
	
	public static BasicStroke normal = new BasicStroke(1);
	public static BasicStroke augmente = new BasicStroke(2);
	
	/**
	 * <b>Affiche la CStateMachine sm.</b>
	 * <p>Ouvre une nouvelle fenêtre avec l'état de la CStateMachine sm.</p>
	 * 
	 * @see CStateMachine
	 * 
	 * @param sm
	 */
	public static void showStateMachine(CStateMachine sm) {
        JFrame viz = new JFrame();
        viz.getContentPane().add(new StateMachineVisualization(sm));
        viz.pack();
        viz.setVisible(true);
    }
	
	/**
	 * <b>Affiche la JStateMachine sm</b>
	 * <p>Ouvre une nouvelle fenêtre avec l'état de la JStateMachine sm.</p>
	 * 
	 * @see JStateMachine
	 * 
	 * @param sm
	 */
    public static void showJStateMachine(JStateMachine sm) {
        JFrame viz = new JFrame();
        viz.getContentPane().add(new StateMachineVisualization(sm));
        viz.pack();
        viz.setVisible(true);
    }
    
    /**
	 * Créé un CStateMachine pour les tag "draggable" et l'attache au canvas.
	 * <p>Les CElements avec un tag "draggable" peuvent être bougé sur le canvas.</p>
	 *
	 * @param canvas : canvas sur lequel on attache le CStateMachine
	 */
	public static void addDragger(Canvas canvas) {

		// Create the state machine and attach it to the canvas
		CStateMachine dragger = new CStateMachine(canvas) {

			Point2D pressLocation;
			Point2D shapeLocation;
			CShape shape;

			public State idling = new State() {
				// The mouse button "BUTTON3" is the right mouse button
				// ("BUTTON1" is the left mouse button)
				Transition down = new PressOnTag("draggable",
						CStateMachine.BUTTON3, ">> dragging") {
					public void action() {
						pressLocation = getPoint();
						shape = getShape();
						shapeLocation = new Point2D.Double(shape.getCenterX(),
								shape.getCenterY());
					};
				};
			};
			public State dragging = new State() {
				Transition move = new Drag() {
					public void action() {
						// Translate the shape (use shape.translateTo())
						pressLocation = getPoint();
						shape.translateTo(pressLocation.getX(), pressLocation.getY());
					};
				};
				Transition release = new Release(">> idling") {					
				};
			};
		};

	}

}
