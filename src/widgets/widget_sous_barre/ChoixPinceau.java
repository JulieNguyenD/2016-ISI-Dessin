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
import fr.lri.swingstates.canvas.transitions.PressOnShape;
import fr.lri.swingstates.sm.State;
import fr.lri.swingstates.sm.Transition;

public class ChoixPinceau extends CRectangle {
	
	private Canvas canvas;
	private Pinceau pinceau;
	private Couleur_Widget choixPinceauCouleur;
	private Taille_Widget choixPinceauTaille;
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
	
	public CStateMachine ajouterStateMachineChoixPinceau(Pinceau pinceau) {
		CStateMachine cm = new CStateMachine() {			
			State idle = new State() {
				Transition pressOnShape = new PressOnShape(BUTTON2, CONTROL, ">> debut") {
					public void action (){
						CShape shape = getShape();
						if (shape instanceof Couleur) {
							pinceau.setCouleurPinceau(((Couleur) shape).getColor());
						}
						if (shape instanceof Taille) {
							pinceau.setTaille((int) ((Taille) shape).getTaille());
						}
					}					
				};
			};
			
			State debut = new State() {
				
			};
		};
		return cm;
	}

}
