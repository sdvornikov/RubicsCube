package cyfn.rubics;

class Face {
	private final int dimension;
	private Piece[][] facePieces;
	private Piece[][][] adgacentPieces;		//[depth][adjacent side][piece]. Starts from [0][0] facePiece and continues clockwise
	
	Face(int dimension, String color) {
		this.dimension = dimension;
		facePieces = new Piece[this.dimension][this.dimension];
		adgacentPieces = new Piece[(int)(this.dimension/2)][4][this.dimension];
		
		for(int i=0;i<this.dimension;i++) {
			for(int j=0;j<this.dimension;j++) {
				facePieces[i][j] = new Piece(color);
			}
		}
				
	}
	
	public void setAdjacentPieces(Piece[][][] pieces) {
		// allow only once ?
		for(int i=0;i<adgacentPieces.length;i++) {
			for(int j=0;j<adgacentPieces[0].length;j++) {
				for(int k=0;k<adgacentPieces[0][0].length;k++) {
					if (pieces[i][j][k] == null)
						throw new NullPointerException("Cannot use null as adjacent piece");
					adgacentPieces[i][j][k] = pieces[i][j][k];
				}
			}
		}
	}
	public String[][] getPicesColor() {
		String[][] result = new String[dimension][dimension];
		for(int i=0;i<dimension;i++) {
			for(int j=0;j<dimension;j++) {
				result[i][j] = facePieces[i][j].getColor();
			}
		}
		return result;
	}
	
	public void turn(Direction dir, int depth) {
		if(depth < 0 || depth > dimension/2 - 1) 
			throw new IllegalArgumentException(String.format("Depth %i is illegal for %ix%i RubicsCube", depth,dimension,dimension));
		
		if(depth == 0) {
			if(dir == Direction.CLOCKWISE)
				turnFacePiecesCW();
			else if(dir == Direction.COUNTERCLOCKWISE)
				turnFacePiecesCCW();
		}
		
		if(dir == Direction.CLOCKWISE)
			turnAdjacentPiecesCW(depth);
		else if(dir == Direction.COUNTERCLOCKWISE)
			turnAdjacentPiecesCCW(depth);
	}
	
	// to transpose a face:
	/*
	for n = 0 to N - 2
		    for m = n + 1 to N - 1
		        swap A(n,m) with A(m,n)
	*/
	private void turnFacePiecesCW() {
		// TODO implement
	}
	private void turnFacePiecesCCW() {
		// TODO implement		
	}
	private void turnAdjacentPiecesCW(int depth) {
		// TODO implement
	}
	private void turnAdjacentPiecesCCW(int depth) {
		// TODO implement
	}	
}
