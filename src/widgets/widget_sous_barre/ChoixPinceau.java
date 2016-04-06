package widgets.widget_sous_barre;

import java.awt.BasicStroke;
import java.awt.geom.Point2D;

import elements.Couleur;
import elements.Pinceau;
import elements.Taille;
import fr.lri.swingstates.canvas.CRectangle;
import fr.lri.swingstates.canvas.CShape;
import fr.lri.swingstates.canvas.CStateMachine;
import fr.lri.swingstates.canvas.Canvas;
import fr.lri.swingstates.canvas.transitions.EnterOnTag;
import fr.lri.swingstates.canvas.transitions.LeaveOnTag;
import fr.lri.swingstates.canvas.transitions.PressOnShape;
import fr.lri.swingstates.canvas.transitions.PressOnTag;
import fr.lri.swingstates.sm.State;
import fr.lri.swingstates.sm.Transition;
import fr.lri.swingstates.sm.transitions.Press;
import fr.lri.swingstates.sm.transitions.Release;
import main.Utilitaires;

public class ChoixPinceau extends CRectangle {
	
	private Canvas canvas;
	private Pinceau pinceau;
	private Couleur_Widget choixPinceauCouleur;
	private Taille_Widget choixPinceauTaille;
	private CStateMachine smWidgetPinceau;
	private Point2D position_widget;
	
	public ChoixPinceau(Canvas canvas, Point2D position, Pinceau pinceau) {
		super(position.getX()-70*2-10*2, position.getY(), 70*2, 290);
		this.canvas = canvas;
		this.pinceau = pinceau;
		
		position_widget = new Point2D.Double(position.getX()-70*2-10*2, position.getY());
		
		this.canvas = canvas;		
		this.addTo(canvas);		
		
		this.setStroke(new BasicStroke(1));
		
		choixPinceauCouleur = new Couleur_Widget(canvas, position_widget);
		choixPinceauTaille = new Taille_Widget(canvas, position_widget);
		choixPinceauTaille.translateBy(70, 0);
		
		choixPinceauCouleur.setParent(this);
		choixPinceauTaille.setParent(this);
		
		this.montrer(false);			
	}
	
	public void montrer(boolean b) {
		this.setDrawable(b);
		this.setPickable(b);
		this.choixPinceauCouleur.montrer(b);
		this.choixPinceauTaille.montrer(b);
	}
	
	public void ajouterStateMachineChoixPinceau(Pinceau pinceau) {
		smWidgetPinceau = new CStateMachine() {			
			CShape shape;
			boolean bdebut = false;
			
			State idle = new State() {
				Transition press = new Press(BUTTON3, ">> crossing") {
					public void action (){
//						shape = getParent();
//						shape.setStroke(Utilitaires.augmente);
//						if (shape instanceof Couleur) {
//							pinceau.setCouleurPinceau(((Couleur) shape).getColor());
//						}
//						if (shape instanceof Taille) {
//							pinceau.setTaille((int) ((Taille) shape).getTaille());
//						}
						System.out.println("Etat CROSSING choixPinceau");

					}					
				};
				
				Transition pressTag = new PressOnTag("couleur", BUTTON3, ">> debut") {
					public void action (){
						
						bdebut = true;
						System.out.println("Etat DEBUT");

					}
				};
			};
			
			State debut = new State() {
				Transition release = new Release(">> idle") {
					public void action() {
						System.out.println("Etat IDLE");
					}
				};
				
				Transition leaveTag = new LeaveOnTag("couleur", ">> crossing") {
					public void action() {
						System.out.println("Etat CROSSING SORTIE");

						if (bdebut) {
							pinceau.setCouleurPinceau(((Couleur) shape).getColor());
						}
					}
				};
			};
			
			State crossing = new State() {
				Transition enterTag = new EnterOnTag("couleur", ">> debut") {
					public void action() {
						shape = getShape();
						shape.setStroke(Utilitaires.augmente);
						System.out.println("Etat DEBUT couleurS");

					}
				};
				
				Transition enterPinceau = new EnterOnTag("pinceau", ">> debut") {
					public void action() {
						shape = getShape();
						shape.setStroke(Utilitaires.augmente);
						System.out.println("Etat DEBUT pinceau");

					}
				};
				
				Transition enterTag1 = new EnterOnTag("taille", ">> debut") {
					public void action() {
						shape = getShape();
						shape.setStroke(Utilitaires.augmente);
						System.out.println("Etat DEBUT taille");

					}
				};
				
				Transition rel = new Release(">> idle") {
					
				};
				
			};
		};
		smWidgetPinceau.attachTo(this);
		Utilitaires.showStateMachine(smWidgetPinceau);
	}

}
