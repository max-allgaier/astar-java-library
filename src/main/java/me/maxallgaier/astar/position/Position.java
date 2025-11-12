package me.maxallgaier.astar.position;

/**
 * An immutable position.
 * The "dimensional space" this position belongs to is held abstracted.
 */
public interface Position {
    /**
     * Checks if this position is equal to another position.
     *
     * @param other The other position.
     * @return If this position is equal to the other position.
     */
    @Override
    boolean equals(Object other);

    /**
     * Generates a hash code of the position.
     *
     * @return A hash code representing this position.
     */
    @Override
    int hashCode();
}
