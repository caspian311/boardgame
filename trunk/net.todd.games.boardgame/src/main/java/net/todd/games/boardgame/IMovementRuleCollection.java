package net.todd.games.boardgame;

import java.util.List;

public interface IMovementRuleCollection {
	List<IRule> getRules();

	void addRule(IRule rule);
}
