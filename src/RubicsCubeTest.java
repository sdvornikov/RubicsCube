import cyfn.rubics.CubeSolver;
import cyfn.rubics.Direction;
import cyfn.rubics.RubicsCube;
import cyfn.rubics.Side;

public class RubicsCubeTest {

	public static void main(String[] args) {
		//test2x2Cube();
		test3x3Cube();
		//test2x2Solver();
	}
	
	static void test2x2Solver() {
		RubicsCube cube = new RubicsCube(2);
		cube.scramble();
		/*
		cube.turnFace(Direction.CLOCKWISE, Side.UP);
		cube.turnFace(Direction.COUNTERCLOCKWISE, Side.RIGHT);
		cube.turnFace(Direction.COUNTERCLOCKWISE, Side.UP);
		cube.turnFace(Direction.COUNTERCLOCKWISE, Side.FRONT);
		cube.turnFace(Direction.COUNTERCLOCKWISE, Side.BACK);
		cube.turnFace(Direction.CLOCKWISE, Side.DOWN);
		*/
		
		System.out.println(cube.getTurnLog());
		displayCube(cube);
		System.out.println("Solved = "+cube.isSolved());
		CubeSolver solver = new CubeSolver(cube, 9);
		solver.run();
		displayCube(cube);
		System.out.println("Solved = "+cube.isSolved());
		System.out.println("Solver Log: " + solver.getSolution());
		System.out.println("Cube Log: " + cube.getTurnLog());
	}
	
	static void test3x3Cube() {
		RubicsCube cube3x3 = new RubicsCube(3);
		cube3x3.scramble();
		displayCube(cube3x3);
		
		cube3x3.turnFace(Direction.HALFTURN, Side.UP);
		cube3x3.turnFace(Direction.CLOCKWISE, Side.RIGHT);
		cube3x3.turnFace(Direction.CLOCKWISE, Side.FRONT);
		cube3x3.turnFace(Direction.COUNTERCLOCKWISE, Side.FRONT);
		cube3x3.turnFace(Direction.COUNTERCLOCKWISE, Side.RIGHT);
		cube3x3.turnFace(Direction.COUNTERCLOCKWISE, Side.UP);
		//System.out.println("U R F Fi Ri Ui");
		
		//
		displayCube(cube3x3);
		System.out.println("Solved = "+cube3x3.isSolved());
	}
	
	static void test2x2Cube() {
		RubicsCube cube2x2 = new RubicsCube(2);
		displayCube(cube2x2);
		cube2x2.turnFace(Direction.CLOCKWISE, Side.UP);
		cube2x2.turnFace(Direction.CLOCKWISE, Side.RIGHT);
		cube2x2.turnFace(Direction.COUNTERCLOCKWISE, Side.UP);
		cube2x2.turnFace(Direction.COUNTERCLOCKWISE, Side.FRONT);
		cube2x2.turnFace(Direction.CLOCKWISE, Side.BACK);
		cube2x2.turnFace(Direction.CLOCKWISE, Side.DOWN);
		System.out.println(cube2x2.getTurnLog());
		displayCube(cube2x2);
	}
	
	static void test4x4Cube() {
		RubicsCube cube4x4 = new RubicsCube(4);
		displayCube(cube4x4);
		cube4x4.turnFace(Direction.CLOCKWISE, Side.UP, 1);
		cube4x4.turnFace(Direction.COUNTERCLOCKWISE, Side.UP, 1);
		System.out.println("u ui");
		displayCube(cube4x4);
	}
	
	static void printArray(String[][] array) {
		for(int i=0;i<array.length;i++) {
			for(int j=0;j<array[0].length;j++) {
				System.out.format("[%s]", array[i][j]);
			}
			System.out.println("");
		}
	}
	
	static void displayCube(RubicsCube cube) {
		for(Side side: Side.values()) {
			System.out.println(side + " side");
			printArray(cube.getSidePicesColor(side));
			System.out.println("");
		}
	}

}
