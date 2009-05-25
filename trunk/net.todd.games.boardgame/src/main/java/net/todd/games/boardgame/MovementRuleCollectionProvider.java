package net.todd.games.boardgame;

public class MovementRuleCollectionProvider {
	private static final MovementRuleCollection movementRuleCollection = new MovementRuleCollection();

	static {
		movementRuleCollection.addRule(new AvailabilityRule(GameStateProvider
				.getGameState()));
		movementRuleCollection.addRule(new ProximityRule());
		movementRuleCollection.addRule(new TakeTurnsRule(GameStateProvider
				.getGameState()));
	}

	public static IMovementRuleCollection getRuleCollection() {
		return movementRuleCollection;
	}
}
