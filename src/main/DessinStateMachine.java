package main;

import java.awt.BasicStroke;
import java.awt.geom.Point2D;

import elements.Couleur;
import elements.Taille;
import fr.lri.swingstates.canvas.CEllipse;
import fr.lri.swingstates.canvas.CPolyLine;
import fr.lri.swingstates.canvas.CRectangle;
import fr.lri.swingstates.canvas.CShape;
import fr.lri.swingstates.canvas.CStateMachine;
import fr.lri.swingstates.canvas.Canvas;
import fr.lri.swingstates.canvas.transitions.EnterOnTag;
import fr.lri.swingstates.canvas.transitions.LeaveOnTag;
import fr.lri.swingstates.sm.State;
import fr.lri.swingstates.sm.Transition;
import fr.lri.swingstates.sm.transitions.Drag;
import fr.lri.swingstates.sm.transitions.Press;
import fr.lri.swingstates.sm.transitions.Release;
import widgets.WidgetCouleurTaille;
import widgets.WidgetOutils;

public class DessinStateMachine extends CStateMachine {
	
	private CShape shape;
	private CEllipse ell;
	private CRectangle rec;
	private CPolyLine line;
	private Point2D p1;
	
	public State out, over, armed, disarmed, draw, changePinceau;
	// voir si mettre d'autres état. Genre, armedPinceau, etc.
	
	public DessinStateMachine(WidgetOutils widgetOutils) {
		out = new State() {
//			Transition toOverPinceau = new EnterOnTag("pinceau", ">> over") {
//				public void action() {
//				}
//			};
//			
//			Transition toOverPot = new EnterOnTag("pot", ">> over") {
//				
//			};
//			
//			Transition toOverGomme = new EnterOnTag("gomme", ">> over") {
//				
//			};
//			
//			Transition toOverForme = new EnterOnTag("forme", ">> over") {
//				
//			};

			Transition pressOut = new Press (BUTTON3,">> disarmed") {
//				public boolean guard() {
//					return pinceau.isEstActif();
//				}
				public void action() {						

				}
			};
			
			Transition pressDessin = new Press (BUTTON1,">> draw") {
//				public boolean guard() {
//					return pinceau.isEstActif();
//				}
				public void action() {						
					if (widgetOutils.getPinceau().isEstActif()) {
						Canvas canvas = (Canvas) getEvent().getSource();
						line = canvas.newPolyLine(getPoint());
						line.setStroke(new BasicStroke(widgetOutils.getPinceau().getTaille()));
						line.setOutlinePaint(widgetOutils.getPinceau().getCouleurPinceau());
						line.setFilled(false);
					}
				}
			};
		};
		
		draw = new State() {
			Transition drawing = new Drag (BUTTON1){
				public void action() {
					if (widgetOutils.getPinceau().isEstActif())	line.lineTo(getPoint());
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

		over = new State() {				
			Transition leave = new LeaveOnTag("pinceau",">> out") {};
			Transition arm = new Press(BUTTON3, ">> armed") {
			};
		};

		armed = new State() {
			Transition disarmPinceau = new LeaveOnTag("pinceau", ">> disarmed") {};
			
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
			
			Transition cancel2 = new Release(BUTTON3, ">> over") {				
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
			
			Transition enterPinceau = new EnterOnTag("pinceau", ">> armed") {
				public void action() {
					shape = getShape();
					shape.setStroke(Utilitaires.augmente);
					System.out.println("Etat DEBUT pinceau");
					widgetOutils.getPinceau().setEstActif(true);
					widgetOutils.getChoixPinceau().montrer(true);
				}
			};
			
			Transition enterChoixPinceau = new EnterOnTag("choixPinceau", ">> changePinceau") {};
			
			Transition enterGomme = new EnterOnTag("gomme", ">> armed") {
				public void action() {
					shape = getShape();
					shape.setStroke(Utilitaires.augmente);
					System.out.println("Etat DEBUT gomme");
					widgetOutils.getChoixGomme().montrer(true);
				}
			};
			
			Transition enterPot = new EnterOnTag("pot", ">> armed") {
				public void action() {
					shape = getShape();
					shape.setStroke(Utilitaires.augmente);
					System.out.println("Etat DEBUT pot");
					widgetOutils.getChoixPot().montrer(true);
				}
			};
			
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
					shape = getShape();
					shape.setStroke(Utilitaires.augmente);
					System.out.println("ChangePinceau Taille");
					widgetOutils.getPinceau().setTaille((int) ((Taille)shape).getTaille());
				}
			};
			
			Transition choixPinceauCouleur = new EnterOnTag("couleur", ">> changePinceau") {
				public void action() {
					shape = getShape();
					shape.setStroke(Utilitaires.augmente);
					System.out.println("ChangePinceau Couleur");
					widgetOutils.getPinceau().setCouleurPinceau(((Couleur) shape).getColor());
				}
			};
			
			Transition fin = new Release("out") {
				public void action() {
					shape.setStroke(Utilitaires.normal);
					widgetOutils.getChoixPinceau().montrer(false);
				}
			};
			
		};
		
		
	}

}
