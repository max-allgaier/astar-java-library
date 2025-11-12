# astar-java-library
Flexible A* pathfinding Java library.

## Installation
Coming soon...

## Usage
To use this library, follow these steps:
1. Implement a custom Position 
    (or use provided implementations [Position2D](./src/main/java/me/maxallgaier/astar/position/Position2D.java) 
    and [Position3D](./src/main/java/me/maxallgaier/astar/position/Position3D.java))
2. Implement a movement controller. This should provide all possible traversable neighbour nodes at a given position.
3. Implement a custom CostCalculator for custom costs
    (or use provided implementations in
    [CostCalculators](./src/main/java/me/maxallgaier/astar/cost/CostCalculators.java))
4. Create an instance of an AStarPathfinder (provided implementation: 
    [DefaultAStarPathfinder](./src/main/java/me/maxallgaier/astar/DefaultAStarPathfinder.java))
5. Call the findPath method
##### Here is an example usage:
```java
public final class Example {
    public static void main(String[] args) {
        // Creating a map (1 = solid).
        // 2 = start, 3 = end
        int[][] map = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 2, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 1, 1},
            {1, 1, 0, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 0, 1},
            {1, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 1, 0, 1, 1},
            {1, 1, 0, 1, 0, 0, 1, 0, 1, 0, 1, 1, 0, 0, 1},
            {1, 1, 0, 1, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1},
            {1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1},
            {1, 1, 0, 1, 1, 0, 0, 1, 0, 1, 1, 0, 0, 0, 1},
            {1, 1, 0, 0, 0, 0, 1, 1, 1, 0, 1, 1, 0, 0, 1},
            {1, 0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1},
            {1, 0, 0, 0, 1, 0, 1, 0, 0, 1, 1, 1, 0, 0, 3},
            {1, 0, 0, 0, 1, 0, 1, 0, 0, 1, 1, 1, 1, 1, 1},
            {1, 0, 1, 0, 1, 0, 1, 1, 0, 0, 0, 0, 1, 1, 1},
            {1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1}
        };

        // Implement a simple movement controller to traverse through the graph above.
        MovementController<Position2D> movementController = position -> {
            int x = (int) position.getX();
            int y = (int) position.getY();

            // Make sure nearby nodes are traversable (not solid).
            List<Position2D> traversableNeighbours = new ArrayList<>();
            if (x > 0 && map[x - 1][y] != 1) traversableNeighbours.add(new Position2D(x - 1, y));
            if (y > 0 && map[x][y - 1] != 1) traversableNeighbours.add(new Position2D(x, y - 1));
            if (x < map.length - 1 && map[x + 1][y] != 1) traversableNeighbours.add(new Position2D(x + 1, y));
            if (y < map[0].length - 1 && map[x][y + 1] != 1) traversableNeighbours.add(new Position2D(x, y + 1));

            return traversableNeighbours;
        };

        // Create the pathfinder.
        DefaultAStarPathfinder<Position2D> pathfinder = new DefaultAStarPathfinderBuilder<Position2D>()
            .setMovementController(movementController)
            .setCostCalculator(CostCalculators.MANHATTAN_TWO_DIMENSION)
            .setClosedPositionsCollectionFactory(HashSetClosedPositionsCollection::new)
            .setOpenNodesQueueFactory(BinaryHeapHashMapOpenNodesQueue::new)
            .build();

        // Use the pathfinder and display the path that was found.
        pathfinder
            .findPath(new Position2D(1, 1), new Position2D(10, 14))
            .forEach(position -> map[(int) position.getX()][(int) position.getY()] = 9);
        Stream.of(map).map(Arrays::toString).forEach(System.out::println);
        /*
        Output (9 = path):
        [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]
        [1, 9, 9, 0, 1, 0, 1, 9, 9, 9, 1, 0, 0, 1, 1]
        [1, 1, 9, 0, 1, 0, 1, 9, 1, 9, 1, 1, 0, 0, 1]
        [1, 1, 9, 1, 1, 0, 1, 9, 1, 9, 1, 1, 0, 1, 1]
        [1, 1, 9, 1, 0, 0, 1, 9, 1, 9, 1, 1, 0, 0, 1]
        [1, 1, 9, 1, 1, 0, 1, 9, 1, 9, 9, 9, 1, 0, 1]
        [1, 0, 9, 1, 0, 0, 9, 9, 1, 0, 1, 9, 0, 0, 1]
        [1, 1, 9, 1, 1, 9, 9, 1, 0, 1, 1, 9, 9, 0, 1]
        [1, 1, 9, 9, 9, 9, 1, 1, 1, 0, 1, 1, 9, 0, 1]
        [1, 0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 9, 1, 1]
        [1, 0, 0, 0, 1, 0, 1, 0, 0, 1, 1, 1, 9, 9, 9]
        [1, 0, 0, 0, 1, 0, 1, 0, 0, 1, 1, 1, 1, 1, 1]
        [1, 0, 1, 0, 1, 0, 1, 1, 0, 0, 0, 0, 1, 1, 1]
        [1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1]
         */
    }
}
```
