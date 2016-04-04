import fr.lri.swingstates.canvas.CPolyLine;
import fr.lri.swingstates.canvas.CStateMachine;
import fr.lri.swingstates.canvas.Canvas;
import fr.lri.swingstates.debug.StateMachineVisualization;
import fr.lri.swingstates.events.VirtualEvent;
import fr.lri.swingstates.sm.JStateMachine;
import fr.lri.swingstates.sm.State;
import fr.lri.swingstates.sm.Transition;
import fr.lri.swingstates.sm.jtransitions.EnterOnJTag;
import fr.lri.swingstates.sm.jtransitions.LeaveOnJTag;
import fr.lri.swingstates.sm.transitions.Drag;
import fr.lri.swingstates.sm.transitions.Event;
import fr.lri.swingstates.sm.transitions.Press;
import fr.lri.swingstates.sm.transitions.Release;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;

/**
 * <b>Machine permettant d'afficher la trace du curseur sur le GlassPane<b>
 * 
 * @see CStateMachine
 * 
 * @author ANDRIANIRINA Tojo
 * @author GABRIEL Damien
 * @author NGUYEN Julie
 * (depuis un fichier de Arnaud PROUZEAU)
 */

class CrossingTrace extends CStateMachine {

    private Point2D pInit = new Point2D.Double(0, 0);

    private CPolyLine line;

    private Canvas c;

    State waiting, onPress;

    CrossingTrace(Canvas ca) {
        this.c = ca;

        waiting = new State() {
            Transition press = new Press(BUTTON1, ">> onPress"){
                public void action(){
                    fireEvent(new VirtualEvent("pressEvent"));
                    pInit.setLocation(getPoint());
                    line = c.newPolyLine(getPoint());
                    line.setDrawable(true);
                    line.setFilled(false);
                }
            };
        };

        onPress = new State(){
            Transition drag = new Drag(BUTTON1){
                public void action(){
                    line.lineTo(getPoint());
                }
            };
            Transition release = new Release(BUTTON1, ">> waiting"){
                public void action(){
                    fireEvent(new VirtualEvent("releaseEvent"));
                    line.setDrawable(false);
                }
            };
        };

    }

} ;
