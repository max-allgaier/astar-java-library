package me.maxallgaier.astar.cost;

import me.maxallgaier.astar.position.Position;

public interface CostCalculator<P extends Position> {
    /**
     * Calculates the cost from one node to another.
     *
     * @param from The starting position
     * @param to The ending position
     * @return The cost from one position to a second position
     */
    double calculate(P from, P to);
}
