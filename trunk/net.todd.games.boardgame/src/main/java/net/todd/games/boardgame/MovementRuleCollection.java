package net.todd.games.boardgame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MovementRuleCollection implements IMovementRuleCollection {
	private final List<IRule> movementRules;

	public MovementRuleCollection() {
		movementRules = new ArrayList<IRule>();
	}

	public void addRule(IRule rule) {
		movementRules.add(rule);
	}

	public List<IRule> getRules() {
		return Collections.unmodifiableList(movementRules);
	}
}
