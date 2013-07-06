package cyfn.rubics;

class Face {
	private final int dimension;
	private Piece[][] facePieces;
	private Piece[][][] adjacentPieces;		//[depth][adjacent side][piece]. Starts from [0][0] facePiece and continues clockwise
	
	Face(int dimension, String color) {
		this.dimension = dimension;
		facePieces = new Piece[this.dimension][this.dimension];
		adjacentPieces = new Piece[(int)(this.dimension/2)][4][this.dimension];
		
		for(int i=0;i<this.dimension;i++) {
			for(int j=0;j<this.dimension;j++) {
				facePieces[i][j] = new Piece(color);
			}
		}
				
	}
	
	public Piece getPieceAt(int x, int y) {
		return facePieces[x][y];
	}
	
	public void setAdjacentPieces(Piece[][][] pieces) {
		// allow only once ?
		for(int i=0;i<adjacentPieces.length;i++) {
			for(int j=0;j<adjacentPieces[0].length;j++) {
				for(int k=0;k<adjacentPieces[0][0].length;k++) {
					if (pieces[i][j][k] == null)
						throw new NullPointerException("Cannot use null as adjacent piece");
					adjacentPieces[i][j][k] = pieces[i][j][k];
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
	
	private void turnFacePiecesCW() {
		for(int i=0;i<=dimension-2;i++) {
			for(int j=i+1;j<=dimension-1;j++) {
				String temp = facePieces[i][j].getColor();
				facePieces[i][j].setColor(facePieces[j][i].getColor());
				facePieces[j][i].setColor(temp);
			}
		}
		
		// !!! Works only for 3x3 TODO
		for(int i=0;i<dimension;i++) {
			String temp = facePieces[i][0].getColor();
			facePieces[i][0].setColor(facePieces[i][2].getColor());
			facePieces[i][2].setColor(temp);
		}
	}
	private void turnFacePiecesCCW() {
		// TODO implement		
	}
	private void turnAdjacentPiecesCW(int depth) {
		//store 4-th side
		String[] tempRow = new String[dimension];
		for(int i=0;i<dimension;i++) {
			tempRow[i] = adjacentPieces[depth][3][i].getColor();
		}
		//rotate colors
		for(int i = 3;i>0;i--) {
			for(int j=0;j<dimension;j++) {
				adjacentPieces[depth][i][j].setColor(adjacentPieces[depth][i-1][j].getColor());
			}
		}
		//restore 4-th side onto 1-th
		for(int i=0;i<dimension;i++) {
			 adjacentPieces[depth][0][i].setColor(tempRow[i]);
		}		
		
	}
	private void turnAdjacentPiecesCCW(int depth) {
		//store 4-th side
		String[] tempRow = new String[dimension];
		for(int i=0;i<dimension;i++) {
			tempRow[i] = adjacentPieces[depth][0][i].getColor();
		}
		//rotate colors
		for(int i = 0;i<3;i++) {
			for(int j=0;j<dimension;j++) {
				adjacentPieces[depth][i][j].setColor(adjacentPieces[depth][i+1][j].getColor());
			}
		}
		//restore 4-th side onto 1-th
		for(int i=0;i<dimension;i++) {
			 adjacentPieces[depth][3][i].setColor(tempRow[i]);
		}	
	}	
}
