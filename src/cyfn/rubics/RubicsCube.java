package cyfn.rubics;

import java.util.HashMap;
import java.util.Map;

public class RubicsCube {

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
	}
	
	public void turnFace(Direction dir,Side side,int depth) {
		faces.get(side).turn(dir, depth);
	}
	public String[][] getSidePicesColor(Side side) {
		return faces.get(side).getPicesColor();
	}
}
