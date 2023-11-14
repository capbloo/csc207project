package entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public abstract class Piece {
    protected boolean hasMoved = false;
    final String color;

    public Piece(String color) {
        this.color = color;
    }
    public String getColor() {
        return color;
    }

    public void pieceMove() {
        hasMoved = true;
    }

    public abstract Move[] getValidMoves(ArrayList<Integer> position, HashMap<ArrayList<Integer>, Piece> boardState, Move lastMove);

    /**
     * Returns a string regarding information of a potential move to (x, y). Assumes that moving to the given square is
     * valid behavior for the given piece.
     *
     * @param x x-coordinate of potential move
     * @param y y-coordinate of potential move
     * @param boardState state of the board
     * @return "stop" if a square is out of bounds or a friendly piece.
     * "empty" if a square is in bounds and empty.
     * "enemy" if a square is in bounds and an opposing piece.
     */
    protected String checkSquare(int x, int y, HashMap<ArrayList<Integer>, Piece> boardState) {

        // return false if out of bounds
        if (x > 8 || x < 1 || y > 8 || y < 1) {
            return "stop";
        }

        // create arraylist pair
        ArrayList<Integer> coords = new ArrayList<>(2);
        coords.add(x);
        coords.add(y);

        // since square is in bounds, get piece at this square
        Piece targetSquare = boardState.get(coords);

        // return empty if square is empty (null)
        if (targetSquare == null) {
            return "empty";
        }

        // friendly pieces cannot be taken so ray should stop
        if (targetSquare.getColor().equals(color)) {
            return "stop";
        } else { // otherwise it is an opposing piece
            return "enemy";
        }
    }

    protected boolean validMove(HashMap<ArrayList<Integer>, Piece> boardState, Move move) {
        Set<ArrayList<Integer>> keys = boardState.keySet();

        for (ArrayList<Integer> key : keys) { // find friendly king and see if move would put/leave king in check
            Piece pieceAtSquare = boardState.get(key);
            if (pieceAtSquare instanceof King && pieceAtSquare.getColor().equals(color)) {
                return !((King) pieceAtSquare).checkHelper(key, boardState, move);
            }
        }

        // if the method gets here no friendly king was found, so throw exception.
        throw new RuntimeException("No "  + color + " king on board.");
    }
}
