package me.maxallgaier.astar.close;

import me.maxallgaier.astar.position.Position;

/**
 * A collection for the closed nodes (positions are used because the entire node is not needed).
 */
public interface ClosedPositionsCollection<P extends Position> {
    /**
     * Adds a position to the closed list.
     *
     * @param position The position to add.
     */
    void add(P position);

    /**
     * Checks if the provided position is in this collection.
     *
     * @param position The position to check.
     * @return true if the provided position is in this collection, otherwise false.
     */
    boolean contains(P position);
}
