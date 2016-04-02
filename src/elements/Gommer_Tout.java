package elements;

import java.awt.Color;
import java.awt.geom.Point2D;

import fr.lri.swingstates.canvas.CExtensionalTag;
import fr.lri.swingstates.canvas.CImage;
import fr.lri.swingstates.canvas.CPolyLine;
import fr.lri.swingstates.canvas.CRectangle;
import fr.lri.swingstates.canvas.CStateMachine;
import fr.lri.swingstates.canvas.Canvas;
import fr.lri.swingstates.canvas.transitions.EnterOnShape;
import fr.lri.swingstates.canvas.transitions.LeaveOnShape;
import fr.lri.swingstates.canvas.transitions.PressOnTag;
import fr.lri.swingstates.sm.State;
import fr.lri.swingstates.sm.Transition;
import fr.lri.swingstates.sm.transitions.Press;
import fr.lri.swingstates.sm.transitions.Release;

public class Gommer_Tout extends CImage{
	private CStateMachine sm;
	private CPolyLine outil_effaceur;
	private CExtensionalTag baseTag;

	public Gommer_Tout(String arg0, Point2D arg1, Canvas canvas) {
		super(arg0, arg1);				
		
		this.addTo(canvas);		
		
		sm = new CStateMachine (){
			public State out = new State() {
				/*public void enter() {					
					setFillPaint(Color.white);
				}*/
				
				Transition toOver = new EnterOnShape(">> over") {};
				//Transition pressOut = new Press (">> disarmed") {};
			};

			public State over = new State() {
				/*public void enter() {					
					setFillPaint(Color.gray);
				}*/				
				
				Transition leave = new LeaveOnShape(">> out") {};
				Transition arm = new Press(BUTTON1,">> armed") {
					public void action (){
						// Dessiner du polyline blanc
						
					}
				};

			};

			public State armed = new State() {
				/*public void enter() {
					setFillPaint(Color.blue);
				}*/

				Transition disarm = new LeaveOnShape(">> disarmed") {
					public void action (){
						// Arreter de dessiner du polyline blanc
						
						System.out.println("test reussi =========================");
					}
				};
				
				Transition act = new Release(BUTTON1, ">> over") {
					public void action (){
						// Arreter de dessiner du polyline blanc						
						
					}
				};
				
			};

			public State disarmed = new State() {
				/*public void enter() {
					//setFillPaint(Color.white);
					// Faire ici les grandes actions
					// il y a un choix de couleur quelque part qui se fait
					
				}*/

				Transition rearm = new EnterOnShape(">> armed") {};
				Transition cancel = new Release(BUTTON1, ">> out") {};

			};
		};
		
		sm.attachTo(this);

		
	}

}
