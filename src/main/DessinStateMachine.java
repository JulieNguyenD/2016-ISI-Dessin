package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Point2D;

import elements.Couleur;
import elements.Taille;
import elements.Annexe_forme;

import fr.lri.swingstates.canvas.CEllipse;
import fr.lri.swingstates.canvas.CPolyLine;
import fr.lri.swingstates.canvas.CRectangle;
import fr.lri.swingstates.canvas.CShape;
import fr.lri.swingstates.canvas.CStateMachine;
import fr.lri.swingstates.canvas.Canvas;
import fr.lri.swingstates.canvas.transitions.ClickOnShape;
import fr.lri.swingstates.canvas.transitions.ClickOnTag;
import fr.lri.swingstates.canvas.transitions.EnterOnTag;
import fr.lri.swingstates.canvas.transitions.LeaveOnTag;
import fr.lri.swingstates.sm.State;
import fr.lri.swingstates.sm.Transition;
import fr.lri.swingstates.sm.transitions.Click;
import fr.lri.swingstates.sm.transitions.Drag;
import fr.lri.swingstates.sm.transitions.Press;
import fr.lri.swingstates.sm.transitions.Release;
import widgets.WidgetOutils;

public class DessinStateMachine extends CStateMachine {
	
	private CShape shape;
	private CEllipse ell;
	private CRectangle rec;
	private CPolyLine line;
	private Point2D p1;
	
	public State out, armed, disarmed, draw, changePinceau, changePot, changeGomme;
	// voir si mettre d'autres état. Genre, armedPinceau, etc.
	
	public DessinStateMachine(WidgetOutils widgetOutils) {
		out = new State() {
			Transition pressOut = new Press (BUTTON3,">> disarmed") {
//				public boolean guard() {
//					return pinceau.isEstActif();
//				}
				public void action() {						

				}
			};
			
			Transition pressDessin = new Press (BUTTON1, ">> draw") {

				public void action() {
					Canvas canvas = (Canvas) getEvent().getSource();
					if (widgetOutils.getPinceau().isEstActif()) {						
						line = canvas.newPolyLine(getPoint());
						line.setStroke(new BasicStroke(widgetOutils.getPinceau().getTaille()));
						line.setOutlinePaint(widgetOutils.getPinceau().getCouleurPinceau());
						line.setFilled(false);
					}
					
					if (widgetOutils.getGomme().isEstActif()) {
						line = canvas.newPolyLine(getPoint());
						line.setStroke(new BasicStroke(widgetOutils.getPinceau().getTaille()));
						line.setOutlinePaint(Color.WHITE);
						line.setFilled(false);
					}
				}
			};
		};
		
		draw = new State() {
			
			Transition clickTag = new ClickOnTag("dessin", BUTTON1) {
				public void action() {
					shape = getShape();
					if (widgetOutils.getPot().isEstActif())	{
						shape.setFillPaint(widgetOutils.getPot().getCouleurPot());
					}					
					if (widgetOutils.getGomme().isEstActif()) {
						if (widgetOutils.getChoixGomme().getFonction() == "forme") shape.remove();
					}
				}
			};
			
			Transition click = new ClickOnShape(BUTTON1) {
				public void action() {
					shape = getShape();
					if (widgetOutils.getPot().isEstActif())	{
						shape.setFillPaint(widgetOutils.getPot().getCouleurPot());
					}					
					if (widgetOutils.getGomme().isEstActif()) {
						if (widgetOutils.getChoixGomme().getFonction() == "forme") shape.remove();
					}
				}
			};
			
			Transition drawing = new Drag (BUTTON1){
				public void action() {
					if (widgetOutils.getPinceau().isEstActif())	line.lineTo(getPoint());
					if (widgetOutils.getGomme().isEstActif()) line.lineTo(getPoint());
				}
			};
			
			Transition notdrawing = new EnterOnTag("NonDrawable", ">> draw") {
				public void action() {
					if (widgetOutils.getPinceau().isEstActif())	line.remove();
					
				}
			};			
			
			Transition cancel = new Release(BUTTON1, ">> out") {
				public void action() {
					if (widgetOutils.getPinceau().isEstActif())	line.lineTo(getPoint());
				}
			};
		};

		armed = new State() {
			Transition disarmPinceau = new LeaveOnTag("pinceau", ">> disarmed") {};
			
			Transition disarmPot = new LeaveOnTag("pot", ">> disarmed") {};
			
			Transition disarmGomme = new LeaveOnTag("gomme", ">> disarmed") {};
			
			Transition disarmTaille = new LeaveOnTag("taille", ">> disarmed") {
				public void action() {
					System.out.println("Etat CROSSING SORTIE");
					widgetOutils.getPinceau().setTaille((int)((Taille) shape).getTaille());
					System.out.println("Taille obtenu =============== "+ ((Taille) shape).getTaille());
					shape.setStroke(Utilitaires.normal);
				}
				
			};
			
			Transition disarmCouleur = new LeaveOnTag("couleur", ">> disarmed") {
				public void action() {
					System.out.println("Etat CROSSING SORTIE COULEUR ");
					widgetOutils.getPinceau().setCouleurPinceau(((Couleur) shape).getColor());
					System.out.println("Couleur obtenu =============== "+ ((Couleur) shape).getColor());
					shape.setStroke(Utilitaires.normal);
				}
				
			};
			
			Transition cancel2 = new Release(BUTTON3, ">> out") {				
				public void action() {
					widgetOutils.getChoixPinceau().montrer(false);
					widgetOutils.getChoixPot().montrer(false);
					widgetOutils.getChoixGomme().montrer(false);
					widgetOutils.getChoixFormes().montrer(false);
					shape.setStroke(Utilitaires.normal);
				}				
			};				
		};

		disarmed = new State() {
			// ------------- PINCEAU
			Transition enterPinceau = new EnterOnTag("pinceau", ">> armed") {
				public void action() {
					shape = getShape();
					shape.setStroke(Utilitaires.augmente);
					System.out.println("Etat DEBUT pinceau");
					widgetOutils.getPinceau().setEstActif(true);
					widgetOutils.getChoixPinceau().montrer(true);
					
					widgetOutils.getPot().setEstActif(false);
					widgetOutils.getForme().setEstActif(false);
					widgetOutils.getGomme().setEstActif(false);
				}
			};
			
			Transition enterChoixPinceau = new EnterOnTag("choixPinceau", ">> changePinceau") {};
			
			// ------------- POT
			Transition enterPot = new EnterOnTag("pot", ">> armed") {
				public void action() {
					shape = getShape();
					shape.setStroke(Utilitaires.augmente);
					System.out.println("Etat DEBUT pot");
					widgetOutils.getPot().setEstActif(true);
					widgetOutils.getChoixPot().montrer(true);
					
					widgetOutils.getPinceau().setEstActif(false);
					widgetOutils.getForme().setEstActif(false);
					widgetOutils.getGomme().setEstActif(false);
				}
			};
			
			Transition enterChoixPot = new EnterOnTag("choixPot", ">> changePot") {};
			
			// ------------- GOMME
			Transition enterGomme = new EnterOnTag("gomme", ">> armed") {
				public void action() {
					shape = getShape();
					shape.setStroke(Utilitaires.augmente);
					widgetOutils.getGomme().setEstActif(true);
					System.out.println("Etat DEBUT gomme");
					widgetOutils.getChoixGomme().montrer(true);
					
					widgetOutils.getPinceau().setEstActif(false);
					widgetOutils.getPot().setEstActif(false);
					widgetOutils.getForme().setEstActif(false);
				}
			};
			
			Transition enterChoixGomme = new EnterOnTag("choixGomme", ">> changeGomme") {};
			
			// ------------- FORME
			Transition enterForme = new EnterOnTag("forme", ">> armed") {
				public void action() {
					shape = getShape();
					shape.setStroke(Utilitaires.augmente);
					System.out.println("Etat DEBUT forme");
					widgetOutils.getChoixFormes().montrer(true);
				}
			};			
			
			Transition cancel = new Release(BUTTON3, ">> out") {
				public void action() {
					widgetOutils.getChoixPinceau().montrer(false);
					widgetOutils.getChoixPot().montrer(false);
					widgetOutils.getChoixGomme().montrer(false);
					widgetOutils.getChoixFormes().montrer(false);
					shape.setStroke(Utilitaires.normal);
				}
			};

		};
		
		
		changePinceau = new State () {
			
			Transition choixPinceauTaille = new EnterOnTag("taille", ">> changePinceau") {
				public void action() {
					shape.setStroke(Utilitaires.normal);
					shape = getShape();
					shape.setStroke(Utilitaires.augmente);
					System.out.println("ChangePinceau Taille");
					widgetOutils.getPinceau().setTaille((int) ((Taille)shape).getTaille());
				}
			};
			
			Transition choixPinceauCouleur = new EnterOnTag("couleur", ">> changePinceau") {
				public void action() {
					shape.setStroke(Utilitaires.normal);
					shape = getShape();
					shape.setStroke(Utilitaires.augmente);
					System.out.println("ChangePinceau Couleur");
					widgetOutils.getPinceau().setCouleurPinceau(((Couleur) shape).getColor());
				}
			};			
			
			Transition fin = new Release("out") {
				public void action() {
					shape.setStroke(Utilitaires.normal);
					widgetOutils.getPinceau().setStroke(Utilitaires.normal);
					widgetOutils.getChoixPinceau().montrer(false);
				}
			};
			
		};
		
		changePot = new State() {
			
			Transition choixPotCouleur = new EnterOnTag("couleur", ">> changePot") {
				public void action() {
					shape.setStroke(Utilitaires.normal);
					shape = getShape();
					shape.setStroke(Utilitaires.augmente);
					System.out.println("ChangePot Couleur");
					widgetOutils.getPot().setCouleurPot(((Couleur) shape).getColor());
				}
			};
			
			Transition finPot = new Release("out") {
				public void action() {
					shape.setStroke(Utilitaires.normal);
					widgetOutils.getChoixPot().montrer(false);
				}
			};
		};	
		
		changeGomme = new State() {
			Transition choixGomme = new EnterOnTag("Annexeforme", ">> changeGomme") {
				public void action() {
					shape.setStroke(Utilitaires.normal);
					shape = getShape();
					shape.setStroke(Utilitaires.augmente);
					System.out.println("changeGomme fonction");
					widgetOutils.getChoixGomme().setFonction(((Annexe_forme)shape).getForme());
				}
			};
			
			Transition finGomme = new Release("out") {
				public void action() {
					shape.setStroke(Utilitaires.normal);
					widgetOutils.getChoixGomme().montrer(false);
				}
			};
		};
		
	}

}
