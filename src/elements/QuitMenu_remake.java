package elements;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JFrame;

import fr.lri.swingstates.canvas.CRectangle;
import fr.lri.swingstates.canvas.CShape;
import fr.lri.swingstates.canvas.CStateMachine;
import fr.lri.swingstates.canvas.CText;
import fr.lri.swingstates.canvas.Canvas;
import fr.lri.swingstates.canvas.transitions.EnterOnTag;
import fr.lri.swingstates.canvas.transitions.LeaveOnTag;
import fr.lri.swingstates.debug.StateMachineVisualization;
import fr.lri.swingstates.sm.JStateMachine;
import fr.lri.swingstates.sm.State;
import fr.lri.swingstates.sm.Transition;
import fr.lri.swingstates.sm.jtransitions.EnterOnComponent;
import fr.lri.swingstates.sm.transitions.Event;
import fr.lri.swingstates.sm.transitions.Press;
import fr.lri.swingstates.sm.transitions.Release;


public class QuitMenu_remake extends CShape{

	// La machine a état
	private CStateMachine sm;

	private int padding = 5;

	// On a utiliser des rectangles pour le moment !
	// TODO : vers Cercle

	private CRectangle parent;
	private CRectangle quit;
	private CRectangle confirm;
	private CRectangle cancel;

	private Color colorQuit = Color.GRAY;
	private Color colorHi = Color.ORANGE;
	private Color colorCancel = Color.GREEN;
	private Color colorConfirm = Color.MAGENTA;

	private CText labelQuit;
	private CText labelCancel;
	private CText labelConfirm;

	private int[] largeurBordure = {1, 2, 5};

	/**
	 * Constructeur de QuitMenu_remake
	 * @param canvas
	 */
	public QuitMenu_remake (Canvas canvas) {
		// On créer l'élément parent
		parent = new CRectangle(-1, -1, 2, 2);
		parent.setDrawable(false);

		parent.addTo(canvas);

		labelQuit = canvas.newText(0, 0, "QUIT", new Font("verdana", Font.PLAIN, 50));
		labelQuit.setPickable(false);

		quit = canvas.newRectangle(labelQuit.getMinX() - padding, 
				labelQuit.getMinY() - padding,
				labelQuit.getWidth() +  2 * padding, 
				labelQuit.getHeight()+  2 * padding);
		labelQuit.setParent(quit);
		quit.setParent(parent);
		quit.below(labelQuit);
		quit.addTag("quit");
		quit.setFillPaint(colorQuit);
		
		labelConfirm = canvas.newText(25, 25, "YES", new Font("verdana", Font.PLAIN, 25));
		labelConfirm.setPickable(false);
		labelCancel = canvas.newText(75, 75, "NO", new Font("verdana", Font.PLAIN, 25));
		labelCancel.setPickable(false);
		
		confirm = canvas.newRectangle(labelConfirm.getMinX() - padding, 
				labelConfirm.getMinY() - padding,
				labelConfirm.getWidth() +  2 * padding, 
				labelConfirm.getHeight()+  2 * padding);
		
		cancel = canvas.newRectangle(labelConfirm.getMinX() - padding, 
				labelCancel.getMinY() - padding,
				labelCancel.getWidth() +  2 * padding, 
				labelCancel.getHeight()+  2 * padding);

		labelConfirm.setParent(confirm);
		labelCancel.setParent(cancel);
		
		// Ajout au Canvas
		quit.addTo(canvas);
		confirm.setDrawable(false);
		confirm.addTo(canvas);
		cancel.setDrawable(false);
		cancel.addTo(canvas);

		parent.addTag("draggable");
		quit.addTag("draggable");
		confirm.setParent(quit);
		confirm.addTag("draggable");
		cancel.setParent(quit);
		cancel.addTag("draggable");

		sm = new CStateMachine() {

			public State idle = new State(){

				Transition topress = new Press(BUTTON1, ">> pressed"){};
			};

			public State pressed = new State(){
				Transition tooverQuit = new EnterOnTag("quit", ">> overQuit"){
					public void action(){
						QuitMenu_remake.this.EnterQuit();
					}
				};
				Transition tooidle = new Release(BUTTON1, ">> idle"){};
			};

			public State overQuit = new State(){
				Transition toidlePressed = new LeaveOnTag("quit", ">> pressed"){
					public void action(){
						QuitMenu_remake.this.LeaveQuit();
					};
				};
			};
		};

		sm.attachTo(quit);
		showStateMachine(sm);

	}

	/**
	 * Le bloc des actions
	 */

	/**
	 * Déclanchée quand on a un EnterOnTag sur Quit
	 */
	protected void EnterQuit() {
		// TODO Auto-generated method stub
		quit.setFillPaint(colorHi);
	}

	/**
	 * Déclanchée quand on a un LeaveOnTag sur Quit
	 */
	protected void LeaveQuit() {
		// TODO Auto-generated method stub
		quit.setFillPaint(colorQuit);
		// Afficher les rectangles de confirmation
		
	}

	/*
	 * Tempo !!!
	 */
	public static void showStateMachine(CStateMachine sm) {
		JFrame viz = new JFrame();
		viz.getContentPane().add(new StateMachineVisualization(sm));
		viz.pack();
		viz.setVisible(true);
	}

	public static void showJStateMachine(JStateMachine sm) {
		JFrame viz = new JFrame();
		viz.getContentPane().add(new StateMachineVisualization(sm));
		viz.pack();
		viz.setVisible(true);
	}

}
