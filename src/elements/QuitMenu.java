package elements;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;

import fr.lri.swingstates.canvas.CEllipse;
import fr.lri.swingstates.canvas.CRectangle;
import fr.lri.swingstates.canvas.CShape;
import fr.lri.swingstates.canvas.CStateMachine;
import fr.lri.swingstates.canvas.CText;
import fr.lri.swingstates.canvas.Canvas;
import fr.lri.swingstates.canvas.transitions.EnterOnShape;
import fr.lri.swingstates.canvas.transitions.LeaveOnShape;
import fr.lri.swingstates.canvas.transitions.ReleaseOnShape;
import fr.lri.swingstates.sm.State;
import fr.lri.swingstates.sm.Transition;
import fr.lri.swingstates.sm.transitions.Press;
import fr.lri.swingstates.sm.transitions.Release;

public class QuitMenu extends CShape{

	private CStateMachine sm;

	private int padding = 5;
	private Color color = Color.WHITE;
	private Color hicolor = Color.YELLOW; // couleur de mise en évidence

	private CEllipse cercle_quit;
	private CText label;
	private Color labelcolor = Color.RED;

	private int[] borderwidth = { 1, 2 };
	
	// Uniquement pour tester le nombre d'appel
	int nb_quit = 0;

	public QuitMenu (Canvas canvas){
		super();
		this.addTo(canvas);
		// x : upper-left bounding box y : upper-left bounding bounding box

		label = canvas.newText(0, 0, "QUIT", new Font("verdana", Font.PLAIN, 50));
		label.setPickable(false);
		label.setFillPaint(labelcolor);

		// Pour avoir un cercle et pas une ellipse
		// détermination des dimmensions max

		// TODO : Calculer les coordonées pour que le label soit au centre du cercle

		double maxdim = Math.max(label.getWidth(), label.getHeight());

		cercle_quit = canvas.newEllipse(label.getMinX() - padding, 
				1./2 * maxdim - 2 * label.getMinX(), 
				maxdim +  2 * padding, 
				maxdim +  2 * padding);

		label.setParent(cercle_quit);
		cercle_quit.below(label);

		cercle_quit.addTo(canvas);
		// ATTENTION : pour que la forme soit EFFECTIVEMENT "draggable" il faut mettre le tag APRES
		// son rattachement au canavas !
		cercle_quit.addTag("draggable");

		
		// TODO : corriger les bugs des états
		// Ca va pas : il y a beaucoup trop d'appel à Quitter !
		sm = new CStateMachine() {
			// l'état par défaut, pas de pressage du bouton

			public State idling = new State(){
				Transition toOver = new EnterOnShape(">> over"){};
				Transition toPres = new Press(BUTTON1, ">> press_idling"){};
			};

			public State over = new State(){
				public void enter(){
					cercle_quit.setStroke(new BasicStroke(borderwidth[1]));
				}

				Transition leave = new LeaveOnShape(">> idling"){
					public void action(){
						cercle_quit.setStroke(new BasicStroke(borderwidth[0]));
					};
				};

			};

			public State press_idling = new State(){
				Transition click = new EnterOnShape(">> click"){};
				Transition release = new Release(" >> idling"){};
			};


			public State click = new State(){
				Transition release_over = new ReleaseOnShape(">> over"){};
				Transition release = new Release(">> press_idling"){};
				Transition leave = new LeaveOnShape(" >> press_idling"){
					public void action(){
						QuitMenu.this.Quitter();
						//fireEvent(new VirtualEvent("pressEvent"));
					}
				};
			};
		};

		sm.attachTo(cercle_quit);
	}

	protected void Quitter() {
		// TODO Auto-generated method stub
		System.out.println("TODO : quitter !" + nb_quit++);
		// Quitter brutalement
		// TODO : ajouter une confirmation
		
		CRectangle confirm = new CRectangle(100, 100, 200, 200);
		confirm.setFillPaint(Color.CYAN);
		
		confirm.addTo(canvas);
		
		// System.exit(0);
	}

}
