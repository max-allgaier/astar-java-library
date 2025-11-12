package me.maxallgaier.astar.close;

import me.maxallgaier.astar.position.Position;

import java.util.HashSet;

public final class HashSetClosedPositionsCollection<P extends Position> implements ClosedPositionsCollection<P> {
    private final HashSet<P> closedPositions;

    public HashSetClosedPositionsCollection() {
        this.closedPositions = new HashSet<>();
    }

    @Override
    public void add(P position) {
        this.closedPositions.add(position);
    }

    @Override
    public boolean contains(P position) {
        return this.closedPositions.contains(position);
    }
}
