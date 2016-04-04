package elements;

import java.awt.Color;
import java.awt.Font;

import fr.lri.swingstates.canvas.CRectangle;
import fr.lri.swingstates.canvas.CShape;
import fr.lri.swingstates.canvas.CStateMachine;
import fr.lri.swingstates.canvas.CText;
import fr.lri.swingstates.canvas.Canvas;

public class QuitMenu_remake extends CShape{

	// La machine a état
	private CStateMachine sm;

	private int padding = 5;

	// On a utiliser des rectangles pour le moment !
	// TODO : vers Cercle

	private CRectangle parent;
	private CRectangle quit;
	private CRectangle confirm;
	private CRectangle cancel;

	private Color colorQuit = Color.DARK_GRAY;
	private Color colorHi = Color.ORANGE;
	private Color colorCancel = Color.GREEN;
	private Color colorConfirm = Color.MAGENTA;

	private CText labelQuit;
	private CText labelCancel;
	private CText labelConfirm;

	private int[] largeurBordure = {1, 2, 5};

	/**
	 * Constructeur de QuitMenu_remake
	 * @param canvas
	 */
	public QuitMenu_remake (Canvas canvas) {
		// On créer l'élément parent
		parent = new CRectangle(-1, -1, 2, 2);
		parent.setDrawable(false);

		parent.addTo(canvas);

		labelQuit = canvas.newText(0, 0, "QUIT", new Font("verdana", Font.PLAIN, 50));
		labelQuit.setPickable(false);

		quit = canvas.newRectangle(labelQuit.getMinX() - padding, 
				labelQuit.getMinY() - padding,
				labelQuit.getWidth() +  2 * padding, 
				labelQuit.getHeight()+  2 * padding);
		labelQuit.setParent(quit);
		quit.setParent(parent);
		quit.below(labelQuit);


		// Ajout au Canvas
		quit.addTo(canvas);

		parent.addTag("draggable");
		quit.addTag("draggable");
	}

}
