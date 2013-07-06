package cyfn.rubics;

class Face {
	private final int dimension;
	private Piece[][] facePieces;
	private Piece[][][] adgacentPieces;		//[depth][adjacent side][piece]
	
	Face(int dimension) {
		this.dimension = dimension;
		facePieces = new Piece[this.dimension][this.dimension];
		adgacentPieces = new Piece[(int)(this.dimension/2)][4][this.dimension];
		// TODO create facePices
	}
	
	public void rotate(Direction dir, int turns, int depth) {
		if(depth < 0 || depth > dimension/2 - 1) 
			throw new IllegalArgumentException(String.format("Depth %i is illegal for %ix%i RubicsCube", depth,dimension,dimension));
		if(turns < 0) 
			throw new IllegalArgumentException("Number of turns must be positive");
		
		// TODO if depth=0 turn facePieces
		// then turn adjacent pieces
	}
	
	// TODO
	/*
	rotateHalfTurn() - helpers...
	rotateQuarterTurnCW()
	rotateQuarterTurnCCW()
	*/
}
