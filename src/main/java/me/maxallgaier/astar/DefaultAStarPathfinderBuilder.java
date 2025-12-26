package me.maxallgaier.astar;

import me.maxallgaier.astar.close.ClosedPositionsCollection;
import me.maxallgaier.astar.cost.CostCalculator;
import me.maxallgaier.astar.movement.MovementController;
import me.maxallgaier.astar.open.OpenNodesQueue;
import me.maxallgaier.astar.position.Position;

import java.util.function.Supplier;

public final class DefaultAStarPathfinderBuilder<P extends Position> {
    private MovementController<P> movementController;
    private CostCalculator<P> gCostCalculator;
    private CostCalculator<P> hCostCalculator;
    private Supplier<OpenNodesQueue<P>> openNodesQueueFactory;
    private Supplier<ClosedPositionsCollection<P>> closedPositionsCollectionFactory;

    public DefaultAStarPathfinderBuilder<P> setMovementController(MovementController<P> movementController) {
        this.movementController = movementController;
        return this;
    }

    public DefaultAStarPathfinderBuilder<P> setCostCalculator(CostCalculator<P> costCalculator) {
        this.gCostCalculator = costCalculator;
        this.hCostCalculator = costCalculator;
        return this;
    }

    public DefaultAStarPathfinderBuilder<P> setGCostCalculator(CostCalculator<P> costCalculator) {
        this.gCostCalculator = costCalculator;
        return this;
    }

    public DefaultAStarPathfinderBuilder<P> setHCostCalculator(CostCalculator<P> costCalculator) {
        this.hCostCalculator = costCalculator;
        return this;
    }

    public DefaultAStarPathfinderBuilder<P> setOpenNodesQueueFactory(
        Supplier<OpenNodesQueue<P>> openNodesQueueFactory
    ) {
        this.openNodesQueueFactory = openNodesQueueFactory;
        return this;
    }

    public DefaultAStarPathfinderBuilder<P> setClosedPositionsCollectionFactory(
        Supplier<ClosedPositionsCollection<P>> closedPositionsCollectionFactory
    ) {
        this.closedPositionsCollectionFactory = closedPositionsCollectionFactory;
        return this;
    }

    public DefaultAStarPathfinder<P> build() {
        return new DefaultAStarPathfinder<>(
                this.movementController,
                this.gCostCalculator,
                this.hCostCalculator,
                this.openNodesQueueFactory,
                this.closedPositionsCollectionFactory
        );
    }
}
