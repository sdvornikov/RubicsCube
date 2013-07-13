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
	
	private Side lastTurnedSide;
	private Direction lastTurnedDirection;
	private int lastTurnedDepth;
	
	public RubicsCube(int dimension) {
		this.dimension = dimension;
		
		faces = new HashMap<Side, Face>();
		
		// construct faces with default color
		for (Side side : Side.values()) {
			faces.put(side, new Face(this.dimension, side.getDefaultColor()));
		}
		
		// Pick a side to build an array
		for (Side side : faces.keySet()) {
			Piece adjacentPiecesArray[][][] = new Piece[(int)(this.dimension/2)][4][this.dimension];
			for (int depthIndex=0;depthIndex<(int)(this.dimension/2);depthIndex++) {
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
									adjacentPiecesArray[depthIndex][adjSideCount][k] = faces.get(adjSide).getPieceAt(depthIndex, k);
								}
								break;
							case 1:
								for(int k = 0; k<dimension;k++) { 
									adjacentPiecesArray[depthIndex][adjSideCount][k] = faces.get(adjSide).getPieceAt(k, dimension-depthIndex-1);
								}
								break;
							case 2:
								for(int k = 0; k<dimension;k++) { 
									adjacentPiecesArray[depthIndex][adjSideCount][dimension-k-1] = faces.get(adjSide).getPieceAt(dimension-depthIndex-1, k);
								}
								break;
							case 3:
								for(int k = 0; k<dimension;k++) { 
									adjacentPiecesArray[depthIndex][adjSideCount][dimension-k-1] = faces.get(adjSide).getPieceAt(k, depthIndex);
								}
							}
						}
					}
					adjSideCount++;
				}
			}
			faces.get(side).setAdjacentPieces(adjacentPiecesArray);
		}
		
	}
	
	public void turnFace(Direction dir,Side side,int depth) {
		faces.get(side).turn(dir, depth);
		lastTurnedDirection = dir;
		lastTurnedSide = side;
		lastTurnedDepth = depth;
	}
	public void turnFace(Direction dir,Side side) {
		turnFace(dir,side, 0);
	}
	public String[][] getSidePicesColor(Side side) {
		return faces.get(side).getPicesColor();
	}
	public boolean isSolved() {
		for (Side side: faces.keySet()) {
			String[][] pieceColor = getSidePicesColor(side);
			String sideColor = pieceColor[0][0];
			for(int i=0;i<dimension;i++)
				for(int j=0;j<dimension;j++)
					if(sideColor != pieceColor[i][j])
						return false;
		}
		
		return true;
	}
	public void undoLastTurn() {
		if (lastTurnedDirection == null || lastTurnedSide == null) return;
		if (lastTurnedDirection == Direction.CLOCKWISE) {
			turnFace(Direction.COUNTERCLOCKWISE, lastTurnedSide, lastTurnedDepth);
		} else if (lastTurnedDirection == Direction.COUNTERCLOCKWISE) {
			turnFace(Direction.CLOCKWISE, lastTurnedSide, lastTurnedDepth);
		}
		lastTurnedDirection = null;
		lastTurnedSide = null;
	}
}
