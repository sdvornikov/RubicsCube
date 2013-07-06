package cyfn.rubics;

public enum Side {UP("white"), DOWN("yellow"), FRONT("red"), BACK("orange"), RIGHT("blue"), LEFT("green");
	private final String defaultColor;
	Side(String color) {
		defaultColor=color;
	}
	public String getDefaultColor() {
		return defaultColor;
	}
}
