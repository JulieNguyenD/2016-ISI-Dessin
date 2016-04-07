package main;

import java.util.EventObject;

import fr.lri.swingstates.canvas.CShape;

/**
 * N'est pas utilisé finalement.
 * @author Nicolas Roussel (roussel@lri.fr)
 * 
 */
public class ShapeCreatedEvent extends EventObject {

	CShape shape;

	public ShapeCreatedEvent(Object source, CShape s) {
		super(source);
		shape = s;
	}

	public CShape getShape() {
		return shape;
	}

}
