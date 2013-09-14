package cyfn.rubics;

class Face {
	private final int dimension;
	private Piece[][] facePieces;
	private Piece[][][] adjacentPieces;		//[depth][adjacent side][piece]. Starts from [0][0] facePiece and continues clockwise
	private boolean isFaseInitialized = false;	// Prevents face from turning until adjacent pieces are set
	
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
		if(isFaseInitialized) throw new IllegalStateException("Cannot set adjacent pieces more than once!");
		for(int i=0;i<adjacentPieces.length;i++) {
			for(int j=0;j<adjacentPieces[0].length;j++) {
				for(int k=0;k<adjacentPieces[0][0].length;k++) {
					if (pieces[i][j][k] == null)
						throw new NullPointerException("Cannot use null as adjacent piece");
					adjacentPieces[i][j][k] = pieces[i][j][k];
				}
			}
		}
		isFaseInitialized = true;
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
		if(!isFaseInitialized) throw new IllegalStateException("Cannot turn a face before adjacent pieces are set!");
		
		if(depth < 0 || depth > dimension/2 - 1) 
			throw new IllegalArgumentException(String.format("Depth %i is illegal for %ix%i RubicsCube", depth,dimension,dimension));
		
		if(depth == 0) {
			turnFacePieces(dir);
		}
		
		if(dir == Direction.CLOCKWISE)
			turnAdjacentPiecesCW(depth);
		else if(dir == Direction.COUNTERCLOCKWISE)
			turnAdjacentPiecesCCW(depth);
		else if(dir == Direction.HALFTURN) {
			turnAdjacentPiecesCCW(depth);
			turnAdjacentPiecesCCW(depth);
		}
	}
	
	private void transposeFacePieces() {
		for(int i=0;i<=dimension-2;i++) {
			for(int j=i+1;j<=dimension-1;j++) {
				String temp = facePieces[i][j].getColor();
				facePieces[i][j].setColor(facePieces[j][i].getColor());
				facePieces[j][i].setColor(temp);
			}
		}
	}
	
	private void swapFacePieces(int x1,int y1,int x2,int y2) {
		String temp = facePieces[x1][y1].getColor();
		facePieces[x1][y1].setColor(facePieces[x2][y2].getColor());
		facePieces[x2][y2].setColor(temp);
	}
	
	private void swapRowsVertically() {
		for(int pieceIndex=0;pieceIndex<dimension;pieceIndex++) 
			for (int rowIndex = 0;rowIndex<(int)(dimension/2);rowIndex++) 
				swapFacePieces(pieceIndex, rowIndex, pieceIndex, dimension-rowIndex-1);
	}
	
	private void swapRowsHorizontally() {
		for(int pieceIndex=0;pieceIndex<dimension;pieceIndex++) 
			for (int rowIndex = 0;rowIndex<(int)(dimension/2);rowIndex++) 
				swapFacePieces(rowIndex, pieceIndex, dimension-rowIndex-1, pieceIndex);
	}
		
	private void turnFacePieces(Direction dir) {

		if(dir == Direction.CLOCKWISE) {
			transposeFacePieces();
			swapRowsVertically();
		} else if(dir == Direction.COUNTERCLOCKWISE) {
			transposeFacePieces();
			swapRowsHorizontally();
		} else if(dir == Direction.HALFTURN) {
			swapRowsVertically();
			swapRowsHorizontally();
		}
		
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
