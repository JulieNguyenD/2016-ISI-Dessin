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
		
		this.addTag("choixPinceau");
		
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
	
//	public void ajouterStateMachineChoixPinceau(Pinceau pinceau) {
//		smWidgetPinceau = new CStateMachine() {			
//			CShape shape;
//			
//			public State out = new State() {
//				Transition toOver = new EnterOnTag("pinceau", ">> over") {
//					public void action() {
//					}
//				};
//
//				Transition pressOut = new Press (BUTTON3,">> disarmed") {
////					public boolean guard() {
////						return pinceau.isEstActif();
////					}
//					public void action() {						
//
//					}
//				};
//			};
//
//			public State over = new State() {				
//				Transition leave = new LeaveOnTag("pinceau",">> out") {};
//				Transition arm = new Press(BUTTON3, ">> armed") {
//				};
//			};
//
//			public State armed = new State() {
//				Transition disarmPinceau = new LeaveOnTag("pinceau", ">> disarmed") {
//					public void action() {
//					}
//					
//				};
//				
//				Transition disarmTaille = new LeaveOnTag("taille", ">> disarmed") {
//					public void action() {
//						System.out.println("Etat CROSSING SORTIE");
//						pinceau.setTaille((int)((Taille) shape).getTaille());
//						System.out.println("Taille obtenu =============== "+ ((Taille) shape).getTaille());
//						shape.setStroke(Utilitaires.normal);
//					}
//					
//				};
//				
//				Transition disarmCouleur = new LeaveOnTag("couleur", ">> disarmed") {
//					public void action() {
//						System.out.println("Etat CROSSING SORTIE COULEUR ");
//						pinceau.setCouleurPinceau(((Couleur) shape).getColor());
//						System.out.println("Couleur obtenu =============== "+ ((Couleur) shape).getColor());
//						shape.setStroke(Utilitaires.normal);
//					}
//					
//				};
//				
//				Transition act = new Release(BUTTON3, ">> over") {};				
//			};
//
//			public State disarmed = new State() {
//				
//				Transition enterTag = new EnterOnTag("couleur", ">> armed") {
//					public void action() {
//						shape = getShape();
//						shape.setStroke(Utilitaires.augmente);
//						System.out.println("Etat DEBUT couleurS");
//
//					}
//				};
//				
//				Transition enterPinceau = new EnterOnTag("pinceau", ">> armed") {
//					public void action() {
//						shape = getShape();
//						shape.setStroke(Utilitaires.augmente);
//						System.out.println("Etat DEBUT pinceau");
//
//					}
//				};
//				
//				Transition enterTag1 = new EnterOnTag("taille", ">> armed") {
//					public void action() {
//						shape = getShape();
//						shape.setStroke(Utilitaires.augmente);
//						System.out.println("Etat DEBUT taille");
//
//					}
//				};
//				
//				
//				Transition cancel = new Release(BUTTON3, ">> out") {
//					public void action() {
//					}
//				};
//
//			};
//			
//		};
//
//		smWidgetPinceau.attachTo(canvas);
//		Utilitaires.showStateMachine(smWidgetPinceau);
//	}

	public CStateMachine getSmWidgetPinceau() {
		return smWidgetPinceau;
	}
	
	

}
