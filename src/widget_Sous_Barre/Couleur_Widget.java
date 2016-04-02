package widget_Sous_Barre;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JFrame;

import elements.Couleur;
import fr.lri.swingstates.canvas.CRectangle;
import fr.lri.swingstates.canvas.CShape;
import fr.lri.swingstates.canvas.CStateMachine;
import fr.lri.swingstates.canvas.Canvas;

public class Couleur_Widget extends CRectangle {
	private Canvas canvas;
	private CShape boiteCouleur;
	private ArrayList <Couleur> couleurList;
	
	

	public Couleur_Widget (Canvas canvas){
		super(0, 0, 70, 300);
		this.canvas = canvas;	
		couleurList = new ArrayList<Couleur>();		
		couleurList.add(new Couleur (Color.red, canvas));
		couleurList.add(new Couleur (Color.green, canvas));
		couleurList.add(new Couleur (Color.blue, canvas));
		couleurList.add(new Couleur (Color.yellow, canvas));
		couleurList.add(new Couleur (Color.white, canvas));
				
		for (int i = 0; i < couleurList.size(); i++){
			couleurList.get(i).translateBy (0, (55 * i));
		}
	}
	
	static public void main (String[] args){
		JFrame frame = new JFrame();
		Canvas canvas = new Canvas(450, 200);
		frame.getContentPane().add(canvas);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		new Couleur_Widget(canvas);
		
	}
	
	
}
