package net.todd.games.boardgame;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.vecmath.Vector3f;

public class GamePieceData {
	private final List<PieceInfo> teamOne;
	private final List<PieceInfo> teamTwo;

	public GamePieceData() {
		teamOne = new ArrayList<PieceInfo>();

		PieceInfo piece1 = new PieceInfo();
		piece1.setId(UUID.randomUUID().toString());
		piece1.setPosition(new Vector3f(-15f, 0f, -35f));
		piece1.setColor(GameColors.TEAM_ONE_COLOR);
		piece1.setTeam(Team.ONE);
		teamOne.add(piece1);

		PieceInfo piece2 = new PieceInfo();
		piece2.setId(UUID.randomUUID().toString());
		piece2.setPosition(new Vector3f(-5f, 0f, -35f));
		piece2.setColor(GameColors.TEAM_ONE_COLOR);
		piece2.setTeam(Team.ONE);
		teamOne.add(piece2);

		PieceInfo piece3 = new PieceInfo();
		piece3.setId(UUID.randomUUID().toString());
		piece3.setPosition(new Vector3f(5f, 0f, -35f));
		piece3.setColor(GameColors.TEAM_ONE_COLOR);
		piece3.setTeam(Team.ONE);
		teamOne.add(piece3);

		PieceInfo piece4 = new PieceInfo();
		piece4.setId(UUID.randomUUID().toString());
		piece4.setPosition(new Vector3f(15f, 0f, -35f));
		piece4.setColor(GameColors.TEAM_ONE_COLOR);
		piece4.setTeam(Team.ONE);
		teamOne.add(piece4);

		teamTwo = new ArrayList<PieceInfo>();

		PieceInfo piece5 = new PieceInfo();
		piece5.setId(UUID.randomUUID().toString());
		piece5.setPosition(new Vector3f(-15f, 0f, 35f));
		piece5.setColor(GameColors.TEAM_TWO_COLOR);
		piece5.setTeam(Team.TWO);
		teamTwo.add(piece5);

		PieceInfo piece6 = new PieceInfo();
		piece6.setId(UUID.randomUUID().toString());
		piece6.setPosition(new Vector3f(-5f, 0f, 35f));
		piece6.setColor(GameColors.TEAM_TWO_COLOR);
		piece6.setTeam(Team.TWO);
		teamTwo.add(piece6);

		PieceInfo piece7 = new PieceInfo();
		piece7.setId(UUID.randomUUID().toString());
		piece7.setPosition(new Vector3f(5f, 0f, 35f));
		piece7.setColor(GameColors.TEAM_TWO_COLOR);
		piece7.setTeam(Team.TWO);
		teamTwo.add(piece7);

		PieceInfo piece8 = new PieceInfo();
		piece8.setId(UUID.randomUUID().toString());
		piece8.setPosition(new Vector3f(15f, 0f, 35f));
		piece8.setColor(GameColors.TEAM_TWO_COLOR);
		piece8.setTeam(Team.TWO);
		teamTwo.add(piece8);
	}

	public List<PieceInfo> getTeamOnePieces() {
		return teamOne;
	}

	public List<PieceInfo> getTeamTwoPieces() {
		return teamTwo;
	}
}
