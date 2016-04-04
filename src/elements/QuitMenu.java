package elements;

import java.awt.BasicStroke;
import java.awt.Color;
import javax.swing.JFrame;

import java.awt.Font;

import fr.lri.swingstates.canvas.CEllipse;
import fr.lri.swingstates.canvas.CRectangle;
import fr.lri.swingstates.canvas.CShape;
import fr.lri.swingstates.canvas.CStateMachine;
import fr.lri.swingstates.canvas.CText;
import fr.lri.swingstates.canvas.Canvas;
import fr.lri.swingstates.canvas.transitions.EnterOnShape;
import fr.lri.swingstates.canvas.transitions.EnterOnTag;
import fr.lri.swingstates.canvas.transitions.LeaveOnShape;
import fr.lri.swingstates.canvas.transitions.LeaveOnTag;
import fr.lri.swingstates.canvas.transitions.ReleaseOnShape;
import fr.lri.swingstates.debug.StateMachineVisualization;
import fr.lri.swingstates.sm.JStateMachine;
import fr.lri.swingstates.sm.State;
import fr.lri.swingstates.sm.Transition;
import fr.lri.swingstates.sm.transitions.Leave;
import fr.lri.swingstates.sm.transitions.Press;
import fr.lri.swingstates.sm.transitions.Release;

public class QuitMenu extends CShape{

	// TODO : remettre des cercles !!!
	// Calculer les positions de façons correcte
	// Ajouter la dépendance vis à vis de la souris
	
	private CStateMachine sm;

	private int padding = 5;
	
	private CRectangle cercle_quit;
	private Color cerclecolor = Color.MAGENTA;
	
	private CRectangle cercle_confirm;
	private Color confirmcolor = Color.CYAN;
	
	private CEllipse cercle_cancel;
	private Color cancelcolor = Color.GREEN;
	
	
	private CText label;
	private Color labelcolor = Color.RED;

	private int[] borderwidth = { 1, 2, 3};

	// Uniquement pour tester le nombre d'appel
	int nb_quit = 0;

	public QuitMenu (Canvas canvas){
		super();
		this.addTo(canvas);
		// x : upper-left bounding box y : upper-left bounding bounding box

		label = canvas.newText(0, 0, "QUIT", new Font("verdana", Font.PLAIN, 50));
		label.setPickable(false);
		label.setFillPaint(labelcolor);
		
		cercle_confirm = new CRectangle(100, 100, 200, 200);

		// TODO : Calculer les coordonées pour que le label soit au centre du cercle

		double maxdim = Math.max(label.getWidth(), label.getHeight());

		cercle_quit = canvas.newRectangle(label.getMinX() - padding, 
				1./2 * maxdim - 2 * label.getMinX(), 
				maxdim +  2 * padding, 
				maxdim +  2 * padding);

		label.setParent(cercle_quit);
		cercle_quit.below(label);
		cercle_quit.addTag("circle");
		cercle_confirm.addTag("cercle_confirm");
		cercle_confirm.setDrawable(false);

		cercle_quit.addTo(canvas);
		cercle_confirm.addTo(canvas);
		
		
		// ATTENTION : pour que la forme soit EFFECTIVEMENT "draggable" il faut mettre le tag APRES
		// son rattachement au canavas !
		cercle_quit.addTag("draggable");
		cercle_confirm.addTag("draggable");


		// TODO : corriger les bugs des états
		// Ca va pas : il y a beaucoup trop d'appel à Quitter !
		sm = new CStateMachine() {

			public State idling = new State(){
				EnterOnTag toOver = new EnterOnTag("circle", ">> over"){};
				Transition toPres = new Press(BUTTON1, ">> press_idling"){};
			};

			public State over = new State(){
				public void enter(){
					cercle_quit.setStroke(new BasicStroke(borderwidth[1]));
					cercle_quit.setFillPaint(Color.ORANGE);
				}

				LeaveOnTag leave = new LeaveOnTag("circle", ">> idling"){
					public void action(){
						cercle_quit.setStroke(new BasicStroke(borderwidth[0]));
						cercle_quit.setFillPaint(cerclecolor);
					};
				};

			};

			public State press_idling = new State(){
				Transition click = new EnterOnTag("circle", ">>click"){};
				Transition release = new Release(" >> idling"){};
			};
			
			public State click = new State(){
				Transition release_over = new ReleaseOnShape(">> over"){};
				Transition release = new Release(">> press_idling"){};
				Leave leave = new Leave(">> press_idling"){
					public void action(){
						QuitMenu.this.Confirmation();
						//fireEvent(new VirtualEvent("pressEvent"));
					}
				};
			};
			
			public State quit = new State(){
				Transition exit = new EnterOnTag("cercle_confirm", ">>close"){
					public void action(){
						QuitMenu.this.Quitter();
						//fireEvent(new VirtualEvent("pressEvent"));
					}
				};
				Transition release = new Release(" >> idling"){};
			};


			
			public State close = new State(){
				public void action(){
				System.out.println("On QUITTE !");
				QuitMenu.this.Quitter();
				}
			};
			
		};

		sm.attachTo(cercle_quit);
		showStateMachine(sm);
	}
	
	/**
	 * Les actions qui sont réalisées
	 */

	protected void Confirmation() {
		System.out.println("Confirmation");
		cercle_confirm.setDrawable(true);
	}

	protected void Cancel() {
		// TODO Auto-generated method stub
		System.out.println("Cancel");
		cercle_confirm.setDrawable(false);
	}

	protected void Quitter() {
		// TODO Auto-generated method stub
		System.out.println("Quitter Vraiment !");
		// System.exit(0);
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
