package widget_Sous_Barre;

import fr.lri.swingstates.canvas.Canvas;
import fr.lri.swingstates.canvas.CStateMachine;
import fr.lri.swingstates.canvas.CShape;
import fr.lri.swingstates.canvas.CText;
import fr.lri.swingstates.canvas.CTag;
import fr.lri.swingstates.canvas.CExtensionalTag;
import fr.lri.swingstates.canvas.CHierarchyTag;
import fr.lri.swingstates.sm.StateMachineListener;
import fr.lri.swingstates.debug.StateMachineVisualization;
import fr.lri.swingstates.sm.State;
import fr.lri.swingstates.sm.Transition;
import fr.lri.swingstates.canvas.transitions.*;
import fr.lri.swingstates.sm.transitions.*;

import javax.swing.JFrame;
import javax.swing.JLabel;

import elements.EllipseTool;
import elements.LineTool;
import elements.PathTool;
import elements.RectangleTool;
import elements.SelectionTool;
import elements.ShapeCreatedEvent;

import javax.swing.BoxLayout;
import java.awt.Container;
import java.awt.Color;
import java.awt.Font;
import java.awt.BasicStroke;
import java.awt.event.KeyEvent;
import java.util.EventObject;
import java.util.ArrayList;

/**
 * @author Nicolas Roussel (roussel@lri.fr)
 *
 */
public class GraphicalEditor_Widget extends JFrame {

	private Canvas canvas;
	private CShape palette;
	private SelectionTool selector;
	private ArrayList<CStateMachine> forms;
	private JFrame smviz;

	class ToolTag extends CExtensionalTag {
		private CStateMachine form;

		ToolTag(CStateMachine csm) {
			form = csm;
		}

		CStateMachine getTool() {
			return form;
		}
	}

	private StateMachineListener smlistener = new StateMachineListener() {
		public void eventOccured(EventObject e) {
			ShapeCreatedEvent csce = (ShapeCreatedEvent) e;
			csce.getShape().addTag(selector.getBaseTag())
					.setFillPaint(Color.white);
			new CHierarchyTag(palette).aboveAll();
		}
	};

	public GraphicalEditor_Widget(String title, int width, int height) {
		super(title);
		canvas = new Canvas(width, height);
		canvas.setAntialiased(true);
		getContentPane().add(canvas);
		smviz = null;{

		forms = new ArrayList<CStateMachine>();
		selector = new SelectionTool();
		forms.add(selector);
		forms.add(new RectangleTool());
		forms.add(new EllipseTool());
		forms.add(new LineTool());
		forms.add(new PathTool());

		int iconsize = 50;
		palette = canvas.newRectangle(20, 5, iconsize,  20);
		palette.addTag(selector.getBaseTag());

		/*State machine qui permet la selection des formes*/
		new CStateMachine() {
			State start = new State() {
				Transition changeTool = new PressOnTag(ToolTag.class, BUTTON1,
						NOMODIFIER) {
					public void action() {
						ToolTag tt = (ToolTag) getTag();
						canvas.getTag(ToolTag.class).setStroke(
								new BasicStroke(1));
						tt.setStroke(new BasicStroke(4));
						tt.aboveAll();
						CStateMachine theTool = tt.getTool();
						for (CStateMachine aTool : forms)
							aTool.setActive(aTool == theTool);
						consumes(true);
					}
				};
			};
		}.attachTo(canvas);

		for (int i = 0; i < forms.size(); i++) {
			CStateMachine form = forms.get(i);
			form.addStateMachineListener(smlistener);
			form.attachTo(canvas);
			if (form != selector)
				form.setActive(false);

			CShape s = canvas.newImage(20,  i * iconsize + 20, "resources/"
					+ form.getClass().getName() + ".png");
			s.setParent(palette);
			s.addTag(new ToolTag(form));

			//System.out.println ("classe = " + form.getClass());
		}



		CStateMachine vizToggler = new CStateMachine() {
			public State invisible = new State() {
				Transition help = new KeyPress(KeyEvent.VK_F1, ">> visible") {
					public void action() {
						if (smviz == null) {
							smviz = new JFrame("StateMachine Viz");
							Container pane = smviz.getContentPane();
							pane.setLayout(new BoxLayout(pane,
									BoxLayout.PAGE_AXIS));
							for (CStateMachine csm : forms) {
								pane.add(new JLabel(csm.getClass().getName()));
								pane.add(new StateMachineVisualization(csm));
							}
							smviz.pack();
						}
						smviz.setVisible(true);
					}
				};
			};
			public State visible = new State() {
				Transition help = new KeyPress(KeyEvent.VK_F1, ">> invisible") {
					public void action() {
						smviz.setVisible(false);
					}
				};
			};
		};
		vizToggler.attachTo(canvas);

		pack();
		setVisible(true);
		canvas.requestFocusInWindow();
	}

	}
	/*public static void main(String[] args) {
		GraphicalEditor_Widget editor = new GraphicalEditor_Widget("Graphical Editor", 400,
				600);
		editor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}*/

}
