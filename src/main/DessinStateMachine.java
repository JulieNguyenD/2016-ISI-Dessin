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
import fr.lri.swingstates.canvas.CSegment;
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
	private CRectangle rect;
	private CSegment seg;
	private CPolyLine line;
	private Point2D p1;
	
	public State out, armed, disarmed, draw, changePinceau, changePot, changeGomme, changeForme;
	// voir si mettre d'autres état. Genre, armedPinceau, etc.
	
	public DessinStateMachine(WidgetOutils widgetOutils) {
		out = new State() {
			Transition pressOut = new Press (BUTTON3,">> disarmed") {};
			
//			Transition clickTag = new ClickOnTag("dessin", BUTTON1) {
//				public void action() {
//					shape = getShape();
//					if (widgetOutils.getPot().isEstActif())	{
//						shape.setFillPaint(widgetOutils.getPot().getCouleurPot());
//					}					
//					if (widgetOutils.getGomme().isEstActif()) {
//						if (widgetOutils.getChoixGomme().getFonction() == "forme") shape.remove();
//					}
//				}
//			};
			
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
			
			Transition pressDessin = new Press (BUTTON1, ">> draw") {

				public void action() {
					Canvas canvas = (Canvas) getEvent().getSource();
					p1 = getPoint();

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
					if (widgetOutils.getForme().isEstActif()) {
						if (widgetOutils.getForme().getFonction() == "rectangle") {
							rect = canvas.newRectangle(p1, 1, 1);
							rect.setStroke(new BasicStroke(widgetOutils.getForme().getTaille()));
							rect.setOutlinePaint(widgetOutils.getForme().getCouleur());
							rect.setFilled(false);
						} else if (widgetOutils.getForme().getFonction() == "ligne"){
							seg = canvas.newSegment(p1, p1);
							seg.setStroke(new BasicStroke(widgetOutils.getForme().getTaille()));
							seg.setOutlinePaint(widgetOutils.getForme().getCouleur());
							seg.setFilled(false);
						} else if (widgetOutils.getForme().getFonction() == "ellipse") {
							ell = canvas.newEllipse(p1, 1, 1);
							ell.setStroke(new BasicStroke(widgetOutils.getForme().getTaille()));
							ell.setOutlinePaint(widgetOutils.getForme().getCouleur());
							ell.setFilled(false);
						}
					}
					
					
				}
			};
		};
		
		draw = new State() {
			
			Transition drawing = new Drag (BUTTON1){
				public void action() {
					if (widgetOutils.getPinceau().isEstActif())	line.lineTo(getPoint());
					if (widgetOutils.getGomme().isEstActif()) line.lineTo(getPoint());
					if (widgetOutils.getForme().isEstActif()) {
						if (widgetOutils.getForme().getFonction() == "rectangle") rect.setDiagonal(p1, getPoint());
						else if (widgetOutils.getForme().getFonction() == "ligne") seg.setPoints(p1, getPoint());
						else if (widgetOutils.getForme().getFonction() == "ellipse") ell.setDiagonal(p1, getPoint());
					}
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
					if (widgetOutils.getForme().isEstActif()) {
						if (widgetOutils.getForme().getFonction() == "rectangle") rect.setDiagonal(p1, getPoint());
						else if (widgetOutils.getForme().getFonction() == "ligne") seg.setPoints(p1, getPoint());
						else if (widgetOutils.getForme().getFonction() == "ellipse") ell.setDiagonal(p1, getPoint());
					}
				}
			};
		};

		armed = new State() {
			Transition disarmPinceau = new LeaveOnTag("pinceau", ">> disarmed") {};
			
			Transition disarmPot = new LeaveOnTag("pot", ">> disarmed") {};
			
			Transition disarmGomme = new LeaveOnTag("gomme", ">> disarmed") {};
			
			Transition disarmForme = new LeaveOnTag("forme", ">> disarmed") {};

			
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
					widgetOutils.getForme().setEstActif(true);
					System.out.println("Etat DEBUT gomme");
					widgetOutils.getChoixFormes().montrer(true);
					
					widgetOutils.getPinceau().setEstActif(false);
					widgetOutils.getPot().setEstActif(false);
					widgetOutils.getGomme().setEstActif(false);
				}
			};			
			
			Transition enterChoixForme = new EnterOnTag("choixFormes", ">> changeForme") {};
			
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
		
		changeForme = new State() {			
			
			Transition choixFormef = new EnterOnTag("Annexeforme", ">> changeForme") {
				public void action() {
					shape.setStroke(Utilitaires.normal);
					shape = getShape();
					shape.setStroke(Utilitaires.augmente);
					System.out.println("changeForme Forme");
					widgetOutils.getForme().setFonction(((Annexe_forme)shape).getForme());
				}
			};			
			
			Transition choixFormeCouleur = new EnterOnTag("couleur", ">> changeForme") {
				public void action() {
					shape.setStroke(Utilitaires.normal);
					shape = getShape();
					shape.setStroke(Utilitaires.augmente);
					System.out.println("changeForme Couleur");
					widgetOutils.getForme().setCouleur(((Couleur) shape).getColor());
				}
			};
			
			Transition finForme = new Release("out") {
				public void action() {
					shape.setStroke(Utilitaires.normal);
					widgetOutils.getPinceau().setStroke(Utilitaires.normal);
					widgetOutils.getChoixFormes().montrer(false);
				}
			};
		};
		
	}

}
