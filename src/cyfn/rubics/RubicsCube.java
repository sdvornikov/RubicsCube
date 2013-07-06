package cyfn.rubics;

import java.util.HashMap;
import java.util.Map;

public class RubicsCube {

	private static final Map<Side,Side[]> adjacentFaces = new HashMap<Side, Side[]>();
	static {
		adjacentFaces.put(Side.UP, new Side[] {Side.BACK,Side.RIGHT,Side.FRONT,Side.LEFT});
		adjacentFaces.put(Side.FRONT, new Side[] {Side.UP,Side.RIGHT,Side.DOWN,Side.LEFT});
		adjacentFaces.put(Side.DOWN, new Side[] {Side.BACK,Side.LEFT,Side.FRONT,Side.RIGHT});
		adjacentFaces.put(Side.BACK, new Side[] {Side.DOWN,Side.RIGHT,Side.UP,Side.LEFT});
		adjacentFaces.put(Side.RIGHT, new Side[] {Side.BACK,Side.DOWN,Side.FRONT,Side.UP});
		adjacentFaces.put(Side.LEFT, new Side[] {Side.BACK,Side.UP,Side.FRONT,Side.DOWN});
	}
	
	
	private Map<Side, Face> faces;
	private final int dimension;
	
	public RubicsCube(int dimension) {
		this.dimension = dimension;
		
		faces = new HashMap<Side, Face>();
		
		// construct faces with default color
		for (Side side : Side.values()) {
			faces.put(side, new Face(this.dimension, side.getDefaultColor()));
		}
		// TODO build adjacent arrays
		for (Side side : faces.keySet()) {
			for (Side adjSide : adjacentFaces.get(side)) {
				
			}
		}
	}
	
	public void turnFace(Direction dir,Side side,int depth) {
		faces.get(side).turn(dir, depth);
	}
	public String[][] getSidePicesColor(Side side) {
		return faces.get(side).getPicesColor();
	}
}
