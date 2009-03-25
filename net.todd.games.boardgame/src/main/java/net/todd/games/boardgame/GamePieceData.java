package net.todd.games.boardgame;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Vector3f;

public class GamePieceData {
	public List<PieceInfo> getTeamOnePieces() {
		List<PieceInfo> teamOne = new ArrayList<PieceInfo>();

		PieceInfo piece1 = new PieceInfo();
		piece1.setPosition(new Vector3f(-15f, 0f, -35f));
		piece1.setColor(GameColors.TEAM_ONE_COLOR);
		piece1.setTeam(Team.ONE);
		teamOne.add(piece1);

		PieceInfo piece2 = new PieceInfo();
		piece2.setPosition(new Vector3f(-5f, 0f, -35f));
		piece2.setColor(GameColors.TEAM_ONE_COLOR);
		piece2.setTeam(Team.ONE);
		teamOne.add(piece2);

		PieceInfo piece3 = new PieceInfo();
		piece3.setPosition(new Vector3f(5f, 0f, -35f));
		piece3.setColor(GameColors.TEAM_ONE_COLOR);
		piece3.setTeam(Team.ONE);
		teamOne.add(piece3);

		PieceInfo piece4 = new PieceInfo();
		piece4.setPosition(new Vector3f(15f, 0f, -35f));
		piece4.setColor(GameColors.TEAM_ONE_COLOR);
		piece4.setTeam(Team.ONE);
		teamOne.add(piece4);

		return teamOne;
	}

	public List<PieceInfo> getTeamTwoPieces() {
		List<PieceInfo> teamTwo = new ArrayList<PieceInfo>();

		PieceInfo piece1 = new PieceInfo();
		piece1.setPosition(new Vector3f(-15f, 0f, 35f));
		piece1.setColor(GameColors.TEAM_TWO_COLOR);
		piece1.setTeam(Team.TWO);
		teamTwo.add(piece1);

		PieceInfo piece2 = new PieceInfo();
		piece2.setPosition(new Vector3f(-5f, 0f, 35f));
		piece2.setColor(GameColors.TEAM_TWO_COLOR);
		piece2.setTeam(Team.TWO);
		teamTwo.add(piece2);

		PieceInfo piece3 = new PieceInfo();
		piece3.setPosition(new Vector3f(5f, 0f, 35f));
		piece3.setColor(GameColors.TEAM_TWO_COLOR);
		piece3.setTeam(Team.TWO);
		teamTwo.add(piece3);

		PieceInfo piece4 = new PieceInfo();
		piece4.setPosition(new Vector3f(15f, 0f, 35f));
		piece4.setColor(GameColors.TEAM_TWO_COLOR);
		piece4.setTeam(Team.TWO);
		teamTwo.add(piece4);

		return teamTwo;
	}
}
