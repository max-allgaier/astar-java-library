package me.maxallgaier.astar.open;

import me.maxallgaier.astar.Node;
import me.maxallgaier.astar.position.Position;

import java.util.HashMap;
import java.util.Map;

/**
 * A min binary heap combined with a hash map.
 * <p>
 * Operation time complexities:
 * <br>add: O(log n)
 * <br>update: O(log n)
 * <br>poll(getAndRemoveFirst): O(log n)
 * <br>get: O(1)
 * <br>empty(hasNodes): O(1)
 */
public final class BinaryHeapHashMapOpenNodesQueue<P extends Position> implements OpenNodesQueue<P> {
    private Node<?>[] list;
    private final Map<Position, Node<P>> map;
    private int size;

    public BinaryHeapHashMapOpenNodesQueue() {
        this(50);
    }

    public BinaryHeapHashMapOpenNodesQueue(int initialCapacity) {
        this.list = new Node[initialCapacity];
        this.map = new HashMap<>(initialCapacity);
        this.size = 0;
    }

    @Override
    public void add(Node<P> node) {
        if (this.size == this.list.length) {
            Node<?>[] newList = new Node[this.size * 2];
            System.arraycopy(this.list, 0, newList, 0, this.size);
            this.list = newList;
        }

        this.list[this.size] = node;
        node.setQueueIndex(this.size);
        this.size++;

        this.map.put(node.getPosition(), node);

        this.update(node);
    }

    @Override
    public void update(Node<P> node) {
        int index = node.getQueueIndex();
        int parentIndex = (index - 1) / 2;
        while (index != 0 && node.getFCost() < this.list[parentIndex].getFCost()) {
            this.swap(node, this.list[parentIndex]);
            index = parentIndex;
            parentIndex = (parentIndex - 1) / 2;
        }
    }

    @Override
    public Node<P> getAndRemoveFirst() {
        @SuppressWarnings("unchecked")
        Node<P> first = (Node<P>) this.list[0];
        first.setQueueIndex(-1);
        this.map.remove(first.getPosition());

        this.list[0] = this.list[this.size - 1];
        this.list[this.size - 1] = null;
        this.size--;

        if (this.size > 0) {
            this.list[0].setQueueIndex(0);
            this.updateDownwards(this.list[0]);
        }

        return first;
    }

    @Override
    public Node<P> get(P position) {
        return this.map.get(position);
    }

    @Override
    public boolean hasNodes() {
        return this.size != 0;
    }

    private void updateDownwards(Node<?> node) {
        while (true) {
            int index = node.getQueueIndex();
            int childLeftIndex = (index * 2) + 1;
            int childRightIndex = (index * 2) + 2;

            if (childLeftIndex >= this.size) {
                return;
            }

            int highestPriorityChildIndex = childLeftIndex;
            boolean rightChildHasHigherPriority = childRightIndex < this.size &&
                    this.list[childRightIndex].getFCost() < this.list[childLeftIndex].getFCost();
            if (rightChildHasHigherPriority) {
                highestPriorityChildIndex = childRightIndex;
            }

            if (this.list[highestPriorityChildIndex].getFCost() >= node.getFCost()) {
                return;
            }

            this.swap(this.list[highestPriorityChildIndex], node);
        }
    }

    private void swap(Node<?> nodeOne, Node<?> nodeTwo) {
        this.list[nodeOne.getQueueIndex()] = nodeTwo;
        this.list[nodeTwo.getQueueIndex()] = nodeOne;
        int temp = nodeOne.getQueueIndex();
        nodeOne.setQueueIndex(nodeTwo.getQueueIndex());
        nodeTwo.setQueueIndex(temp);
    }
}
