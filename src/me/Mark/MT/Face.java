package me.Mark.MT;

public enum Face {
	FORWARD(0),  RIGHT(1), BACK(2), LEFT(3), UP(4), DOWN(5);
	
	private int dir;
	
	Face(int dir) {
		this.dir = dir;
	}
	
	public int getDir() {
		return dir;
	}
}
