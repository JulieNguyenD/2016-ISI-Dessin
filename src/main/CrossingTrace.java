package main;

import fr.lri.swingstates.canvas.CPolyLine;
import fr.lri.swingstates.canvas.CStateMachine;
import fr.lri.swingstates.canvas.Canvas;
import fr.lri.swingstates.sm.State;
import fr.lri.swingstates.sm.Transition;
import fr.lri.swingstates.sm.transitions.Drag;
import fr.lri.swingstates.sm.transitions.Press;
import fr.lri.swingstates.sm.transitions.Release;

import java.awt.geom.Point2D;

/**
 * <b>Classe CrossingTrace qui permet de faire la trace sur l'écran</b>
 * <p>Quand on appuie sur le click gauche, affiche la trace sur l'écran.<br/>
 * Quand on release, la trace s'efface.</p>
 * 
 * @see CStateMachine
 * @see Point2D
 * @see CPolyLine
 * 
 * @author ANDRIANIRINA Tojo
 * @author GABRIEL Damien
 * @author NGUYEN Julie
 */
public class CrossingTrace extends CStateMachine {

	/**
	 * pInit : Point de départ de la PolyLine
	 */
    private Point2D pInit = new Point2D.Double(0, 0);

    /**
     * line : CPolyLine que l'on dessine pour faire la trace.
     */
    private CPolyLine line;

    /**
     * c : Canvas sur lequel on dessine le PolyLine.
     * @see CrossingTrace#CrossingTrace(Canvas)
     */
    private Canvas c;

    /**
     * Etats de la CStateMachine.
     */
    State waiting, onPress;

    /**
     * Constructeur de la stateMachine CrossingTrace.
     * <p>A la création d'un CrossingTrace, on attribut son Canvas c à ca et <br/>
     * à chaque onPress évènement, on dessine le PolyLine line jusqu'au relâchement de la souris.</p>
     * @param ca : canvas sur lequel on dessine.
     */
    CrossingTrace(Canvas ca) {
        this.c = ca;

        waiting = new State() {
            Transition press = new Press(BUTTON3, ">> onPress"){
                public void action(){
                    line = c.newPolyLine(getPoint());
                    line.setFilled(false);
                }
            };
        };

        onPress = new State(){
            Transition drag = new Drag(BUTTON3){
                public void action(){
                	line.setPickable(false);
                    line.lineTo(getPoint());
                }
            };
            Transition release = new Release(BUTTON3, ">> waiting"){
                public void action(){
                	line.remove();
                    line.setDrawable(false);
                }
            };
        };

    }

} ;
