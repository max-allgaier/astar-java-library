package me.maxallgaier.astar.cost;

import me.maxallgaier.astar.position.Position2D;
import me.maxallgaier.astar.position.Position3D;

public final class CostCalculators {
    public static final CostCalculator<Position2D> EUCLIDEAN_TWO_DIMENSION =
        (from, to) -> pythagorasAddition(from.getX() - to.getX(), from.getY() - to.getY());
    public static final CostCalculator<Position3D> EUCLIDEAN_THREE_DIMENSION =
        (from, to) -> pythagorasAddition(
            from.getX() - to.getX(),
            from.getY() - to.getY(),
            from.getZ() - to.getZ()
        );

    private static double pythagorasAddition(double... terms) {
        double sumOfTermsSquared = 0.0;
        for (double number : terms) {
            sumOfTermsSquared += number * number;
        }
        return Math.sqrt(sumOfTermsSquared);
    }

    public static final CostCalculator<Position2D> MANHATTAN_TWO_DIMENSION =
        (from, to) -> Math.abs(to.getX() - from.getX()) + Math.abs(to.getY() - from.getY());
    public static final CostCalculator<Position3D> MANHATTAN_THREE_DIMENSION =
        (from, to) ->
            Math.abs(to.getX() - from.getX()) + Math.abs(to.getY() - from.getY()) + Math.abs(to.getZ() - from.getZ());

    private CostCalculators() {
    }
}
