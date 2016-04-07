package widgets.widget_sous_barre;

import java.awt.BasicStroke;
import java.awt.geom.Point2D;

import elements.Annexe_forme;
import elements.Couleur;
import elements.Forme;
import elements.Pinceau;
import elements.Taille;
import fr.lri.swingstates.canvas.CRectangle;
import fr.lri.swingstates.canvas.CShape;
import fr.lri.swingstates.canvas.CStateMachine;
import fr.lri.swingstates.canvas.Canvas;
import fr.lri.swingstates.canvas.transitions.EnterOnTag;
import fr.lri.swingstates.canvas.transitions.LeaveOnTag;
import fr.lri.swingstates.sm.State;
import fr.lri.swingstates.sm.Transition;
import fr.lri.swingstates.sm.transitions.Press;
import fr.lri.swingstates.sm.transitions.Release;
import main.Utilitaires;

public class ChoixFormes extends CRectangle {
	
	private Canvas canvas;
	private Couleur_Widget choixCouleurContour;
	private Forme_Widget choixForme;
	private Point2D position_widget;
	private CStateMachine smWidgetForms;
	
	public ChoixFormes(Canvas canvas, Point2D position) {
		super(position.getX()-70*2-10*2, position.getY(), 70*2, 290);
		
		position_widget = new Point2D.Double(position.getX()-70*2-10*2, position.getY());

		this.canvas = canvas;	
		this.addTo(canvas);		
		
		this.setStroke(new BasicStroke(1));
		
		this.addTag("choixFormes");
		
		choixCouleurContour = new Couleur_Widget(canvas, position_widget);
		choixForme = new Forme_Widget(canvas, position_widget);
		choixForme.translateBy(70, 0);
		
		choixCouleurContour.setParent(this);
		choixForme.setParent(this);
		
		this.montrer(false);			
	}
	
	public void montrer(boolean b) {
		this.setDrawable(b);
		this.setPickable(b);
		this.choixCouleurContour.montrer(b);
		this.choixForme.montrer(b);
	}
	
	public void ajouterStateMachineChoixPinceau(Forme forme) {
		smWidgetForms = new CStateMachine() {			
			CShape shape;
			
			public State out = new State() {
				Transition toOver = new EnterOnTag("forme", ">> over") {
					public void action() {
					}
				};

				Transition pressOut = new Press (BUTTON3,">> disarmed") {
//					public boolean guard() {
//						return forme.isEstActif();
//					}
					public void action() {						

					}
				};
			};

			public State over = new State() {				
				Transition leave = new LeaveOnTag("forme",">> out") {};
				Transition arm = new Press(BUTTON3, ">> armed") {
				};
			};

			public State armed = new State() {
				Transition disarmForm = new LeaveOnTag("forme", ">> disarmed") {
					public void action() {
					}
					
				};
				
				Transition disarmChoix = new LeaveOnTag("choix", ">> disarmed") {
					public void action() {
//						Faire ici le choix de la forme a faire
						String a = ((Annexe_forme) shape).getForme();
						
						forme.setForme(a);
						shape.setStroke(Utilitaires.normal);
						System.out.println("STRING PRIS LORS DE LA SORTIE !!!!!!!!!!!!!!!!!!!!!!!!!!!" + a );
						
					}
					
				};
				
				Transition disarmCouleur = new LeaveOnTag("couleur", ">> disarmed") {
					public void action() {
						forme.setCouleur(((Couleur) shape).getColor());
						shape.setStroke(Utilitaires.normal);
						
					}
					
				};
				
				Transition act = new Release(BUTTON3, ">> over") {};				
			};

			public State disarmed = new State() {
				
				Transition enterCouleur = new EnterOnTag("couleur", ">> armed") {
					public void action() {
						shape = getShape();
						shape.setStroke(Utilitaires.augmente);
						System.out.println("Etat DEBUT couleurS");

					}
				};
				
				Transition enterForm = new EnterOnTag("forme", ">> armed") {
					public void action() {
						shape = getShape();
						shape.setStroke(Utilitaires.augmente);
						System.out.println("Etat DEBUT pinceau");

					}
				};
				
				Transition enterChoix = new EnterOnTag("choix", ">> armed") {
					public void action() {
						shape = getShape();
						shape.setStroke(Utilitaires.augmente);
						System.out.println("Etat DEBUT taille");

					}
				};
				
				
				Transition cancel = new Release(BUTTON3, ">> out") {
					public void action() {
					}
				};

			};
			
		};

		smWidgetForms.attachTo(canvas);
		Utilitaires.showStateMachine(smWidgetForms);
	}

	public CStateMachine getSmWidgetPinceau() {
		return smWidgetForms;
	}

}
