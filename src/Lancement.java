import javax.swing.JFrame;

import fr.lri.swingstates.canvas.CStateMachine;
import fr.lri.swingstates.debug.StateMachineVisualization;
import fr.lri.swingstates.sm.JStateMachine;

/**
 * Lancement de l'application.
 * On lance new Application()
 *  
 * @author ANDRIANIRINA Tojo
 * @author GABRIEL Damien
 * @author NGUYEN Julie
 */
public class Lancement {

	public static void main(String[] args) {
		new Application();
	}
	
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
