
import fr.lri.swingstates.canvas.Canvas;
import fr.lri.swingstates.canvas.CStateMachine;
import fr.lri.swingstates.canvas.CShape;
import fr.lri.swingstates.canvas.CText;
import fr.lri.swingstates.canvas.CTag;
import fr.lri.swingstates.canvas.CExtensionalTag;
import fr.lri.swingstates.canvas.CHierarchyTag;
import fr.lri.swingstates.sm.StateMachineListener;
import fr.lri.swingstates.debug.StateMachineVisualization;
import fr.lri.swingstates.sm.JStateMachine;
import fr.lri.swingstates.sm.State;
import fr.lri.swingstates.sm.Transition;
import fr.lri.swingstates.canvas.transitions.*;
import fr.lri.swingstates.sm.transitions.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Font;
import java.awt.BasicStroke;
import java.awt.event.KeyEvent;
import java.util.EventObject;
import java.util.ArrayList;
/*
 * @author Damien GABRIEL
 */

public class GranDessin extends JFrame{
	
	private Canvas canvas;
	
	class ToolTag extends CExtensionalTag {
		private CStateMachine tool;

		ToolTag(CStateMachine csm) {
			tool = csm;
		}

		CStateMachine getTool() {
			return tool;
		}
	}
	

	public GranDessin(String title, int width, int height) {
		super(title);
		
		Dimension minsize = new Dimension(600,600);
		
		this.setPreferredSize(minsize);
		this.setMinimumSize(minsize);
		
		
		canvas = new Canvas(width, height);
		canvas.setAntialiased(true);
		getContentPane().add(canvas);
		
		CrossingTrace ct = new CrossingTrace(canvas) ;
        ct.attachTo(canvas);
        canvas.setVisible(true);
        canvas.setOpaque(false);
		
		// TODO : ajouter les CStateMachine
		
        //On définit le canvas comme le glassPane de la fenêtre
        this.setGlassPane(canvas);
        //Par défaut le glassPane n'est pas visible, il faut donc lui préciser de l'être
        this.getGlassPane().setVisible(true);
        
      //On définit et on attache la JStateMachine pour l'interactiond e crossing elle même
        CrossingInteraction ci = new CrossingInteraction(this);
        ci.attachTo(this);

        // Affiche la machine a état
        //showJStateMachine(ci);

        //On ajoute ci comme StateMachineListener de ct, pour pouvoir faire passer des évenements de ct à ci
        ct.addStateMachineListener(ci);
		
		pack();
		setVisible(true);
		canvas.requestFocusInWindow();
	}

	public static void main(String[] args) {
		GranDessin  editor = new GranDessin("Projet ISI", 600, 600);
		editor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
