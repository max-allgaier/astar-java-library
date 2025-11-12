package me.maxallgaier.astar;

import me.maxallgaier.astar.close.ClosedPositionsCollection;
import me.maxallgaier.astar.cost.CostCalculator;
import me.maxallgaier.astar.movement.MovementController;
import me.maxallgaier.astar.open.OpenNodesQueue;
import me.maxallgaier.astar.position.Position;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

public final class DefaultAStarPathfinder<P extends Position> implements AStarPathfinder<P> {
    private final MovementController<P> movementController;
    private final CostCalculator<P> gCostCalculator;
    private final CostCalculator<P> hCostCalculator;
    private final Supplier<OpenNodesQueue<P>> openNodesQueueFactory;
    private final Supplier<ClosedPositionsCollection<P>> closedPositionsCollectionFactory;

    public DefaultAStarPathfinder(
        MovementController<P> movementController,
        CostCalculator<P> gCostCalculator,
        CostCalculator<P> hCostCalculator,
        Supplier<OpenNodesQueue<P>> openNodesQueueFactory,
        Supplier<ClosedPositionsCollection<P>> closedPositionsCollectionFactory
    ) {
        this.movementController = movementController;
        this.gCostCalculator = gCostCalculator;
        this.hCostCalculator = hCostCalculator;
        this.openNodesQueueFactory = openNodesQueueFactory;
        this.closedPositionsCollectionFactory = closedPositionsCollectionFactory;
    }

    @Override
    public List<? extends P> findPath(P startPos, P endPos) throws RuntimeException {
        OpenNodesQueue<P> openNodesQueue = this.openNodesQueueFactory.get();
        ClosedPositionsCollection<P> closedPositionsCollection = this.closedPositionsCollectionFactory.get();

        openNodesQueue.add(new Node<>(startPos, null, 0.0, 0.0));

        while (openNodesQueue.hasNodes()) {
            Node<P> currentNode = openNodesQueue.getAndRemoveFirst();
            P currentPos = currentNode.getPosition();

            if (currentPos.equals(endPos)) {
                return this.getPathFromStartToEnd(currentNode);
            }

            closedPositionsCollection.add(currentPos);

            for (P neighbourPos : this.movementController.getTraversableNeighbours(currentPos)) {
                if (closedPositionsCollection.contains(neighbourPos)) {
                    continue;
                }

                double neighbourGCost = currentNode.getGCost() +
                    this.gCostCalculator.calculate(currentPos, neighbourPos);

                Node<P> neighbourNode = openNodesQueue.get(neighbourPos);
                if (neighbourNode != null) {
                    if (neighbourGCost < neighbourNode.getGCost()) {
                        neighbourNode.setParent(currentNode);
                        neighbourNode.setGCost(neighbourGCost);
                        openNodesQueue.update(neighbourNode);
                    }
                    continue;
                }

                double neighbourHCost = this.hCostCalculator.calculate(neighbourPos, endPos);
                openNodesQueue.add(new Node<>(neighbourPos, currentNode, neighbourGCost, neighbourHCost));
            }
        }

        throw new PathNotFoundException();
    }

    private List<P> getPathFromStartToEnd(Node<P> endNode) {
        LinkedList<P> startToEndPath = new LinkedList<>();
        Node<P> childNode = endNode;
        while (childNode != null) {
            startToEndPath.addFirst(childNode.getPosition());
            childNode = childNode.getParent();
        }
        return startToEndPath;
    }
}
