import java.io.IOException;

import javax.swing.JFrame;

import fr.lri.swingstates.canvas.CStateMachine;
import fr.lri.swingstates.debug.StateMachineVisualization;
import fr.lri.swingstates.sm.JStateMachine;

/**
 * <b>Lancement de l'application.</b>
 * <p>Fonction main qui fait une nouvelle fenêtre Application</p>
 *  
 * @see Application
 *  
 * @author ANDRIANIRINA Tojo
 * @author GABRIEL Damien
 * @author NGUYEN Julie
 */
public class Lancement {

	public static void main(String[] args) throws IOException {
		new Application();
	}
	
	/**
	 * <b>Affiche la CStateMachine sm.</b>
	 * <p>Ouvre une nouvelle fenêtre avec l'état de la CStateMachine sm.</p>
	 * 
	 * @see CStateMachine
	 * 
	 * @param sm
	 */
	public static void showStateMachine(CStateMachine sm) {
        JFrame viz = new JFrame();
        viz.getContentPane().add(new StateMachineVisualization(sm));
        viz.pack();
        viz.setVisible(true);
    }
	
	/**
	 * <b>Affiche la JStateMachine sm</b>
	 * <p>Ouvre une nouvelle fenêtre avec l'état de la JStateMachine sm.</p>
	 * 
	 * @see JStateMachine
	 * 
	 * @param sm
	 */
    public static void showJStateMachine(JStateMachine sm) {
        JFrame viz = new JFrame();
        viz.getContentPane().add(new StateMachineVisualization(sm));
        viz.pack();
        viz.setVisible(true);
    }

}
