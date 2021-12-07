import java.util.Arrays;
import java.util.PriorityQueue;

public class Board {
    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    int[][] mat = new int[4][4];

    public Board(int[][] init) {
        mat = init.clone();
    }

    @Override
    public String toString() {
        return "Board{" +
                "mat=" + Arrays.toString(mat) +
                '}';
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int dis = 0;
        for (int i = 1; i < 8; i++) {
            int raw;
            if (i <= 3)
                raw = 0;
            else if (i <= 6) raw = 1;
            else
                raw = 2;
            int column;
            if (i % 3 == 0)
                column = 2;
            else
                column = (i - raw * 3) % 3 - 1;
            int[] pos = linearSearch(i);
            dis += Math.abs(pos[0] - raw) + Math.abs(pos[1] - column);
        }
        return dis;
    }


    public int[] linearSearch(int value) {
        int[] pos = new int[2];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (mat[i][j] == value) {
                    pos[0] = i;
                    pos[1] = j;
                    return pos;
                }
            }
        }
        return new int[]{-1, -1};

    }

    public Board exchange(int way) {
        int[][] clone = mat.clone();
        int pos[] = linearSearch(0);
        switch (way) {
            case 0:
                try {
                    clone[pos[0]][pos[1]] = clone[pos[0] - 1][pos[1]];
                    clone[pos[0] - 1][pos[1]] = 0;
                    return new Board(clone);
                } catch (ArrayIndexOutOfBoundsException ignored) {

                }
            case 1:
                try {
                    clone[pos[0]][pos[1]] = clone[pos[0] + 1][pos[1]];
                    clone[pos[0] + 1][pos[1]] = 0;
                    return new Board(clone);
                } catch (ArrayIndexOutOfBoundsException ignored) {
                }
            case 2:
                try {
                    clone[pos[0]][pos[1]] = clone[pos[0]][pos[1] - 1];
                    clone[pos[0]][pos[1] - 1] = 0;
                    return new Board(clone);
                } catch (ArrayIndexOutOfBoundsException ignored) {
                }
            case 3:
                try {
                    clone[pos[0]][pos[1]] = clone[pos[0]][pos[1] + 1];
                    clone[pos[0]][pos[1] + 1] = 0;
                    return new Board(clone);
                } catch (ArrayIndexOutOfBoundsException ignored) {
                }
        }
        return new Board(clone);
    }

    public void method() {
        PriorityQueue<Node> queue = new PriorityQueue<>();
        queue.add(new Node(this, 0, null));
        while (true) {
            Node node = queue.poll();
            int prevHeight = node.getLength();
            if (node.board.manhattan() == 0)
                return;
            else {
                queue.add(new Node(exchange(0), prevHeight + 1, node));
                queue.add(new Node(exchange(1), prevHeight + 1, node));
                queue.add(new Node(exchange(2), prevHeight + 1, node));
                queue.add(new Node(exchange(3), prevHeight + 1, node));
            }
        }
    }
}


class Node implements Comparable {
    int length;
    Node node;
    Board board;

    public Node(Board board, int heigth, Node node) {
        this.length = heigth;
        this.node = node;
        this.board = board;
    }

    public int getLength() {
        return length;
    }

    public Node getNode() {
        return node;
    }

    public Board getBoard() {
        return board;
    }

    @Override
    public int compareTo(Object o) {
        Node anotherNode = (Node) o;
        return Integer.compare(node.board.manhattan() + node.length, anotherNode.board.manhattan() + anotherNode.length);
    }
}


