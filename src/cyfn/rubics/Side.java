package cyfn.rubics;

public enum Side {TOP("white"), BOTTOM("yellow"), FRONT("red"), REAR("orange"), RIGHT("blue"), LEFT("green");
	private final String defaultColor;
	Side(String color) {
		defaultColor=color;
	}
	public String getDefaultColor() {
		return defaultColor;
	}
}
