package me.maxallgaier.astar.movement;

import me.maxallgaier.astar.position.Position;

import java.util.List;

/**
 * Controls the movement that the pathfinder will take.
 */
public interface MovementController<P extends Position> {
    /**
     * Provides traversable neighbors from a given position.
     *
     * @param position The position where neighbors will be located.
     * @return A list of positions, which are traversable neighbors next to the provided position.
     */
    List<? extends P> getTraversableNeighbours(P position);
}
