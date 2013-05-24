package be.kuleuven.swop.objectron.domain;

import be.kuleuven.swop.objectron.domain.util.Position;

/**
 * @author : Nik Torfs
 *         Date: 27/02/13
 *         Time: 21:48
 */
public enum Direction {
    UP_LEFT(-1, -1), UP(0, -1), UP_RIGHT(+1, -1), LEFT(-1, 0), RIGHT(+1, 0), DOWN_LEFT(-1, +1), DOWN(0, +1),
    DOWN_RIGHT(+1, +1);

    private int hOperation;
    private int vOperation;

    private static Node directionCycle;

    static {
        Node node1 = new Node();
        node1.d = Direction.UP;
        Node node2 = new Node();
        node2.d = Direction.UP_RIGHT;
        Node node3 = new Node();
        node3.d = Direction.RIGHT;
        Node node4 = new Node();
        node4.d = Direction.DOWN_RIGHT;
        Node node5 = new Node();
        node5.d = Direction.DOWN;
        Node node6 = new Node();
        node6.d = Direction.DOWN_LEFT;
        Node node7 = new Node();
        node7.d = Direction.LEFT;
        Node node8 = new Node();
        node8.d = Direction.UP_LEFT;

        node1.prev = node8;
        node1.next = node2;
        node2.prev = node1;
        node2.next = node3;
        node3.prev = node2;
        node3.next = node4;
        node4.prev = node3;
        node4.next = node5;
        node5.prev = node4;
        node5.next = node6;
        node6.prev = node5;
        node6.next = node7;
        node7.prev = node6;
        node7.next = node8;
        node8.prev = node7;
        node8.next = node1;

        directionCycle = node1;
    }

    private Direction(int hOperation, int vOperation) {
        this.hOperation = hOperation;
        this.vOperation = vOperation;
    }

    public Position applyPositionChange(Position position) {
        return new Position(position.getHIndex() + hOperation,
                position.getVIndex() + vOperation);
    }

    static class Node {
        Node next;
        Node prev;
        Direction d;
    }

    public Direction next() {
        Node currentNode = directionCycle;
        boolean found = false;

        while (!found) {
            if (currentNode.d == this) {
                found = true;
            } else {
                currentNode = currentNode.next;
            }
        }
        return currentNode.next.d;
    }

    public Direction previous() {
        Node currentNode = directionCycle;
        boolean found = false;

        while (!found) {
            if (currentNode.d == this) {
                found = true;
            } else {
                currentNode = currentNode.next;
            }
        }
        return currentNode.prev.d;
    }

}
