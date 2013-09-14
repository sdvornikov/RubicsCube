package cyfn.rubics;

import java.util.HashMap;
import java.util.LinkedList;
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
	private LinkedList<TurnInfo> turnLog = new LinkedList<>();
	
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
		turnLog.add(new TurnInfo(side, dir, depth));
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
		TurnInfo lastTurn = turnLog.pollLast();
		if (lastTurn == null) return;
		
		if (lastTurn.turnedDirection == Direction.CLOCKWISE) {
			turnFace(Direction.COUNTERCLOCKWISE, lastTurn.turnedSide, lastTurn.turnedDepth);
		} else if (lastTurn.turnedDirection == Direction.COUNTERCLOCKWISE) {
			turnFace(Direction.CLOCKWISE, lastTurn.turnedSide, lastTurn.turnedDepth);
		} else if (lastTurn.turnedDirection == Direction.HALFTURN) {
			turnFace(Direction.HALFTURN, lastTurn.turnedSide, lastTurn.turnedDepth);
		}
		turnLog.pollLast();
	}

	public void scramble() {
		int numberOfTurns = (int)(Math.random()*10*dimension) + 10*dimension;
		for (int i=0;i<numberOfTurns;i++) {
			turnFace(Direction.values()[(int)(Math.random()*Direction.values().length)], 
					Side.values()[(int)(Math.random()*Side.values().length)], 
					(int)(Math.random()*(int)(dimension/2)));
		}
	}
	
	public String getTurnLog() {
		String result = "";
		for(TurnInfo turn : turnLog) {
			result+=turn + " ";
		}
		return result;
	}
}

class TurnInfo {
	Side turnedSide;
	Direction turnedDirection;
	int turnedDepth;
	
	public TurnInfo(Side side, Direction dir, int depth) {
		turnedDepth = depth;
		turnedDirection = dir;
		turnedSide = side;
	}
	
	public String toString() {
		String result = turnedSide.toString().substring(0, 1);
		if(turnedDirection == Direction.COUNTERCLOCKWISE)
			result+="i";
		if(turnedDirection == Direction.HALFTURN)
			result+="2";
		if(turnedDepth==1) 
			result = result.toLowerCase();
		return result;
	}
}