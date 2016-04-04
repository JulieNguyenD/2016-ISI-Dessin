import javax.swing.JFrame;

import fr.lri.swingstates.canvas.CStateMachine;
import fr.lri.swingstates.debug.StateMachineVisualization;
import fr.lri.swingstates.sm.JStateMachine;

public class Utilitaires {

    public static void showStateMachine(CStateMachine sm) {
        JFrame viz = new JFrame();
        viz.getContentPane().add(new StateMachineVisualization(sm));
        viz.pack();
        viz.setVisible(true);
    }

    public static void showJStateMachine(JStateMachine sm) {
        JFrame viz = new JFrame();
        viz.getContentPane().add(new StateMachineVisualization(sm));
        viz.pack();
        viz.setVisible(true);
    }

}
