import cyfn.rubics.RubicsCube;
import cyfn.rubics.Side;

public class RubicsCubeTest {

	public static void main(String[] args) {
		RubicsCube cube3x3 = new RubicsCube(3);
		for(Side side: Side.values()) {
			System.out.println(side + " side");
			printArray(cube3x3.getSidePicesColor(side));
			System.out.println("");
		}

	}
	
	static void printArray(String[][] array) {
		for(int i=0;i<array.length;i++) {
			for(int j=0;j<array[0].length;j++) {
				System.out.format("[%s]", array[i][j]);
			}
			System.out.println("");
		}
	}

}
