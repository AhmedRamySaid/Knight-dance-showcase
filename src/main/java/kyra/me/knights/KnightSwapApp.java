package kyra.me.knights;

import javafx.util.Pair;

import java.util.*;

public class KnightSwapApp {
    static final int ROWS = 4, COLS = 3;
    static final int[][] KNIGHT_MOVES = {
            {-2, -1}, {-2, 1}, {2, -1}, {2, 1},
            {-1, -2}, {-1, 2}, {1, -2}, {1, 2}
    };

    static class Board {
        String board;
        List<String> moveHistory;

        Board(String board, List<String> history) {
            this.board = board;
            this.moveHistory = history;
        }
        boolean isGoal(String goal){
            return board.equals(goal);
        }
        @Override
        public String toString() {
            return board;
        }
    }

    //Calculates all the possible boards that can arise from the current board
    static List<Board> getNextStates(Board current) {
        List<Board> nextStates = new ArrayList<>();

        for (int row = 0; row < ROWS; row++) {
            for (int column = 0; column < COLS; column++) {
                //The board is counted from the top left going 0,1,2...
                int oldPos = COLS * row + column;
                //No piece at this position
                if (current.board.toCharArray()[oldPos] == '.') continue;

                //Calculates the ending square of the knight
                for (int[] move : KNIGHT_MOVES) {
                    int newRow = row + move[0], newColumn = column + move[1];
                    if (newRow < 0 || newRow >= ROWS || newColumn < 0 || newColumn >= COLS) continue;
                    int newPos = COLS * newRow + newColumn;

                    //Ignore moves where the piece goes back to its home row
                    //Despite the name, row 3 is the bottom row and row 0 is the top row
                    //Slightly increases speed in the case of knight switching
                    if (current.board.toCharArray()[oldPos] == 'w' && newRow == 3) continue;
                    if (current.board.toCharArray()[oldPos] == 'b' && newRow == 0) continue;

                    //There is already a piece there
                    if (current.board.toCharArray()[newPos] != '.') continue;

                    //Gets the new board key
                    StringBuilder stringBuilder = new StringBuilder(current.board);
                    stringBuilder.setCharAt(oldPos,'.');
                    stringBuilder.setCharAt(newPos,current.board.charAt(oldPos));

                    //Adds the move to the history
                    List<String> newHistory = new ArrayList<>(current.moveHistory);
                    newHistory.add((column+1) + "" + (row+1) + (newColumn+1) + (newRow+1));
                    Board newBoard = new Board(stringBuilder.toString(), newHistory);

                    nextStates.add(newBoard);
                }
            }
        }
        return nextStates;
    }

    static List<String> breadthFirstAlgorithm(String start, String end) {
        Queue<Board> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        Board initialState = new Board(start, new ArrayList<>());
        queue.add(initialState);
        visited.add(initialState.toString());

        while (!queue.isEmpty()) {
            Board current = queue.poll();

            if (current.isGoal(end)) {
                return current.moveHistory; //Returns the move sequence
            }

            for (Board next : getNextStates(current)) {
                if (!visited.contains(next.toString())) {
                    queue.add(next);
                    visited.add(next.toString());
                }
            }
        }
        System.out.println("positions evaluated: " + visited.size());
        return Collections.singletonList("No solution found"); //End position is not possible from this start position
    }

    static List<String> depthFirstAlgorithm(String start, String end) {
        Stack<Board> stack = new Stack<>();
        Set<String> visited = new HashSet<>();

        Board initialState = new Board(start, new ArrayList<>());
        stack.add(initialState);
        visited.add(initialState.toString());

        while (!stack.isEmpty()) {
            Board current = stack.pop();

            if (current.isGoal(end)) {
                return current.moveHistory; //Returns the move sequence
            }

            for (Board next : getNextStates(current)) {
                if (!visited.contains(next.toString())) {
                    stack.add(next);
                    visited.add(next.toString());
                }
            }
        }
        System.out.println("positions evaluated: " + visited.size());
        return Collections.singletonList("No solution found"); //End position is not possible from this start position
    }

    static List<String> adversarialSearchAlgorithm(String start, String end) {
        // Pair stores: Board, f(n) = g(n) + h(n)
        PriorityQueue<Pair<Board, Integer>> queue = new PriorityQueue<>(
                Comparator.comparing(Pair::getValue)
        );
        Set<String> visited = new HashSet<>();

        Board initialState = new Board(start, new ArrayList<>());
        Pair<Board, Integer> initialPair = new Pair<>(
                initialState,
                compareStates(start, end)  // Just heuristic for start node
        );
        queue.add(initialPair);
        visited.add(start);

        while (!queue.isEmpty()) {
            Pair<Board, Integer> currentPair = queue.poll();
            Board current = currentPair.getKey();

            if (current.isGoal(end)) {
                return current.moveHistory;
            }

            for (Board next : getNextStates(current)) {
                String nextState = next.toString();
                if (!visited.contains(nextState)) {
                    int g_n = current.moveHistory.size() + 1; // Path cost so far
                    int h_n = compareStates(nextState, end);    // Heuristic
                    int f_n = g_n + h_n;                       // Total cost

                    queue.add(new Pair<>(next, f_n));
                    visited.add(nextState);
                }
            }
        }

        System.out.println("Positions evaluated: " + visited.size());
        return Collections.singletonList("No solution found");
    }

    static int compareStates(String start, String end) {
        int differences = 0;
        for (int i = 0; i < start.length(); i++) {
            if (start.charAt(i) != end.charAt(i)) {
                differences++;
            }
        }
        return differences;
    }
}
