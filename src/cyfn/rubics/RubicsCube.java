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
		
		
		// TODO WORKS FOR 3x3 ONLY (one layer depth)
		
		
		// Pick a side to build an array
		for (Side side : faces.keySet()) {
			
			Piece adjacentPiecesArray[][][] = new Piece[(int)(this.dimension/2)][4][this.dimension];
			int adjSideCount = 0;
			// Take an ordered array of adjacent sides
			for (Side adjSide : adjacentFaces.get(side)) {
				// some crazy magic :)
				Side magicSides[] = adjacentFaces.get(adjSide);
				for (int i = 0; i<magicSides.length;i++) {
					if(magicSides[i] == side) {
						switch (i) {
						case 0:
							for(int k = 0; k<dimension;k++) { 
								adjacentPiecesArray[0][adjSideCount][k] = faces.get(adjSide).getPieceAt(0, k);
							}
							break;
						case 1:
							for(int k = 0; k<dimension;k++) { 
								adjacentPiecesArray[0][adjSideCount][k] = faces.get(adjSide).getPieceAt(k, 2);
							}
							break;
						case 2:
							for(int k = 0; k<dimension;k++) { 
								adjacentPiecesArray[0][adjSideCount][dimension-k-1] = faces.get(adjSide).getPieceAt(2, k);
							}
							break;
						case 3:
							for(int k = 0; k<dimension;k++) { 
								adjacentPiecesArray[0][adjSideCount][dimension-k-1] = faces.get(adjSide).getPieceAt(k, 0);
							}
							
						}
					}
				}
				adjSideCount++;
			}
			faces.get(side).setAdjacentPieces(adjacentPiecesArray);
		}
		
	}
	
	public void turnFace(Direction dir,Side side,int depth) {
		faces.get(side).turn(dir, depth);
	}
	public void turnFace(Direction dir,Side side) {
		turnFace(dir,side, 0);
	}
	public String[][] getSidePicesColor(Side side) {
		return faces.get(side).getPicesColor();
	}
}
