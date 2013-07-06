import cyfn.rubics.Direction;
import cyfn.rubics.RubicsCube;
import cyfn.rubics.Side;

public class RubicsCubeTest {

	public static void main(String[] args) {
		RubicsCube cube3x3 = new RubicsCube(3);
		displayCube(cube3x3);
		cube3x3.turnFace(Direction.CLOCKWISE, Side.UP);
		cube3x3.turnFace(Direction.CLOCKWISE, Side.RIGHT);
		cube3x3.turnFace(Direction.CLOCKWISE, Side.UP);
		cube3x3.turnFace(Direction.CLOCKWISE, Side.FRONT);
		cube3x3.turnFace(Direction.CLOCKWISE, Side.BACK);
		cube3x3.turnFace(Direction.CLOCKWISE, Side.DOWN);
		System.out.println("cube3x3.turnFace(Direction.COUNTERCLOCKWISE, Side.RIGHT)");
		displayCube(cube3x3);
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
