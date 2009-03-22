package net.todd.games.boardgame;

import javax.vecmath.Color3f;
import javax.vecmath.Vector3f;

public class PieceInfo {
	private Vector3f position;
	private Color3f color;

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public Color3f getColor() {
		return color;
	}

	public void setColor(Color3f color) {
		this.color = color;
	}
}
