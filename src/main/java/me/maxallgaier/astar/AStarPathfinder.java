package me.maxallgaier.astar;

import me.maxallgaier.astar.position.Position;

import java.util.List;

/**
 * A pathfinder which finds paths to destinations.
 * Implementations may vary.
 *
 * @param <P> The position type.
 */
public interface AStarPathfinder<P extends Position> {
    /**
     * Finds a path between two positions.
     * The returned path may vary depending on the implementation.
     * An optimal path, suboptimal path, etc. are not guaranteed (depends on the implementation), but "a" path is.
     *
     * @param start The starting position.
     * @param end The ending/destination position.
     * @return A list of the paths from start to end.
     * @throws PathNotFoundException If a path cannot be found.
     */
    List<? extends P> findPath(P start, P end) throws PathNotFoundException;
}
