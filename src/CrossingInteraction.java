
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


/*
 * @author : Arnaud PROUZEAU (e11)
 */

class CrossingInteraction extends JStateMachine{
    State waiting, ext, inte;

    CrossingInteraction(JFrame frame){
        waiting = new State(){
            Transition press = new Event("pressEvent", ">> ext"){
            };
        };

        ext = new State(){
            Transition enter = new EnterOnJTag(AbstractButton.class.getName(), ">> inte"){
                public void action() {
                }
            };
            Transition release = new Event("releaseEvent", ">> waiting"){
            };
        };

        inte = new State(){
            Transition leave = new LeaveOnJTag(AbstractButton.class.getName(), ">> ext"){
                public void action() {
                    ((AbstractButton) getComponent()).doClick();
                }
            };
            Transition release = new Event("releaseEvent", ">> waiting"){
            };
        };

    }
}