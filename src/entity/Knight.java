package entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Knight extends Piece {
    public Knight(String color) {
        super(color);
    }

    @Override
    public String toString() {
        if (color.equals("white")) {
            return "♘";
        }
        return "♞";
    }

    public String symbolToString() {
        return "Knight";
    }

    @Override
    public Move[] getValidMoves(ArrayList<Integer> position, HashMap<ArrayList<Integer>, Piece> boardState, Move lastMove) {
        Set<Move> possibleMoves = new HashSet<>();

        int[] xDisplacements = {-2, -2, -1, -1, 1, 1, 2, 2};
        int[] yDisplacements = {-1, 1, -2, 2, -2, 2, -1, 1};

        // check all possible knight moves
        for (int i = 0; i < xDisplacements.length; i++) {
            int targetX = position.get(0) + xDisplacements[i];
            int targetY = position.get(1) + yDisplacements[i];
            String targetSquareType = checkSquare(targetX, targetY, boardState);

            if (targetSquareType.equals("enemy")) { // if enemy, add valid capture
                ArrayList<Integer> destination = coordinateBuilder(targetX, targetY);
                possibleMoves.add(makeCapture(position, destination));
            } else if (targetSquareType.equals("empty")) { // if empty square, add valid move
                ArrayList<Integer> destination = coordinateBuilder(targetX, targetY);
                possibleMoves.add(makeMove(position, destination));
            } // otherwise square is friendly or out of bounds, so invalid move
        }

        Set<Move> checkedMoves = new HashSet<>(possibleMoves.size());

        for (Move move : possibleMoves) {
            if (validMove(boardState, move)) {
                checkedMoves.add(move);
            }
        }

        return checkedMoves.toArray(new Move[0]);
    }

    private Move makeCapture(ArrayList<Integer> position, ArrayList<Integer> destination) {
        Move capture = new Move(this, position, destination);
        capture.setPieceCaptured();

        return capture;
    }

    private Move makeMove(ArrayList<Integer> position, ArrayList<Integer> destination) {
        Move move = new Move(this, position, destination);

        return move;
    }

    private ArrayList<Integer> coordinateBuilder(int x, int y) {
        ArrayList<Integer> coordinate = new ArrayList<>(2);
        coordinate.add(x);
        coordinate.add(y);

        return coordinate;
    }
}
