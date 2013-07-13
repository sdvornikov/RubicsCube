package cyfn.rubics;

public class CubeSolver {

	private RubicsCube cube;
	private int maxTurns;
	
	private enum Action {CW,CCW,HALFTURN}
	private Side[] sides = {Side.UP, Side.RIGHT, Side.FRONT};
	
	public CubeSolver(RubicsCube cube, int maxTurns) {
		this.cube = cube;
		this.maxTurns = maxTurns;
	}
	
	public void run() {
		solve(null,0);
	}
	
	private boolean solve(Side lastSide, int depth) {
		if(cube.isSolved()) return true;
		if(depth>=maxTurns) return false;
		
		for (Side side: sides) {
			if(lastSide==side) continue;
			for (Action action : Action.values()) {
				switch(action) {
				case CW: 
					cube.turnFace(Direction.CLOCKWISE, side); 
					break;
				case CCW: 
					cube.turnFace(Direction.COUNTERCLOCKWISE, side); 
					break;
				case HALFTURN: 
					cube.turnFace(Direction.COUNTERCLOCKWISE, side);
					cube.turnFace(Direction.COUNTERCLOCKWISE, side);
				}
				boolean result = solve(side, depth+1);
				if(result == false)
					cube.undoLastTurn();
				else
					return true;
			}
		}
		return false;
	}
	
	
}
