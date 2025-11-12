package me.maxallgaier.astar.open;

import me.maxallgaier.astar.Node;
import me.maxallgaier.astar.position.Position;

/**
 * A queue for open nodes.
 */
public interface OpenNodesQueue<P extends Position> {
    /**
     * Adds a node to this queue.
     *
     * @param node The node to add.
     */
    void add(Node<P> node);

    /**
     * Updates a node inside this queue.
     * Can be useful when a node's g-cost is updated.
     * Only nodes already contained in this list should be updated, otherwise unpredictable behaviors may occur.
     *
     * @param node The node to update.
     */
    void update(Node<P> node);

    /**
     * Gets and removes the highest-prioritized node in this queue.
     *
     * @return The node with the highest priority in this queue.
     */
    Node<P> getAndRemoveFirst();

    /**
     * Gets a node off the provided position if it exists.
     *
     * @param position The position used to find a related node.
     * @return The node associated with the provided position, otherwise null if none.
     */
    Node<P> get(P position);

    /**
     * Checks if this queue has at least one node.
     *
     * @return true, if this queue is not empty (has at least one node), otherwise false.
     */
    boolean hasNodes();
}
