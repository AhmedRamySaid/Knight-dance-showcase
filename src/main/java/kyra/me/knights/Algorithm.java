package kyra.me.knights;
import java.util.*;

public class Algorithm {
    //returns a list of all the possible board positions that can appear from the current position
    private static List<Board> getNextStates(Board current) {
        List<Board> nextStates = new ArrayList<>();
        List<Move> possibleMoves = new ArrayList<>();
        GameManager.moveCreation(possibleMoves, current);

        for (Move move: possibleMoves) {
            move.doMoveTemporary();
            List<Move> newMoves = new ArrayList<>(current.getMoveHistory());
            newMoves.add(move);

            nextStates.add(new Board(current.toString(), newMoves));
            move.undoMove();
        }
        return nextStates;
    }

    public static List<Move> depthFirstAlgorithm(String start, String end){
        Stack<Board> stack = new Stack<>();
        Set<String> visited = new HashSet<>();

        Board startBoard = new Board(start, new ArrayList<Move>());
        stack.add(startBoard);
        visited.add(start);

        while (!stack.isEmpty()){
            Board board = stack.pop();

            if (visited.size() == 62){
                System.out.print("");
            }

            if (board.toString().equals(end)){
                System.out.println("found");
                return board.getMoveHistory();
            }

            //gets all possible next states and checks if they were checked before
            //if not, adds them to the stack
            for (Board nextBoard: getNextStates(board)){
                if (!visited.contains(nextBoard.toString())){
                    stack.add(nextBoard);
                    visited.add(nextBoard.toString());
                }
            }
        }
        System.out.println("positions checked: " + visited.size());
        return new ArrayList<>(); //answer not found
    }

    public static List<Move> breadthFirstAlgorithm(String start, String end){
        Queue<Board> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        Board startBoard = new Board(start, new ArrayList<Move>());
        queue.add(startBoard);
        visited.add(start);

        while (!queue.isEmpty()){
            Board board = queue.poll();

            if (board.toString().equals(end)){
                return board.getMoveHistory();
            }

            //gets all possible next states and checks if they were checked before
            //if not, adds them to the stack

            for (Board nextBoard: getNextStates(board)){
                if (!visited.contains(nextBoard.toString())){
                    queue.add(nextBoard);
                    visited.add(nextBoard.toString());
                }
            }
        }
        System.out.println("positions checked: " + visited.size());
        return new ArrayList<>(); //answer not found
    }
}

