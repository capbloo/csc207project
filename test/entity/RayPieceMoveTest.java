package entity;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;
public class RayPieceMoveTest {
    public HashMap<ArrayList<Integer>, Piece> defaultBoardState() {
        Board newBoard = new Board();

        return newBoard.getBoardstate();
    }

    public HashMap<ArrayList<Integer>, Piece> emptyBoard() {
        HashMap<ArrayList<Integer>, Piece> board = new HashMap<>(64);

        for (int x = 1; x <= 8; x++) {
            for (int y = 1; y <= 8; y++) {
                board.put(coords(x, y), null);
            }
        }

        return board;
    }

    private ArrayList<Integer> coords(int x, int y) {
        ArrayList<Integer> coordinate = new ArrayList<>(2);
        coordinate.add(x);
        coordinate.add(y);

        return coordinate;
    }

    @org.junit.Test
    public void testQueenMoves() {
        HashMap<ArrayList<Integer>, Piece> board = emptyBoard();

        board.put(coords(4, 4), new Queen("black"));

        board.put(coords(5, 4), new King("black"));

        board.put(coords(1, 2), new King("white"));

        board.put(coords(2, 2), new Pawn("white"));

        Move[] moves = board.get(coords(4, 4)).getValidMoves(coords(4, 4), board, new Move(null, null, null));

        assertEquals(22, moves.length);
    }

    @org.junit.Test
    public void testPin() {
        HashMap<ArrayList<Integer>, Piece> board = emptyBoard();

        board.put(coords(1, 1), new Rook("white"));

        board.put(coords(1, 5), new Queen("black"));

        board.put(coords(1, 7), new King("black"));

        board.put(coords(8, 8), new King("white"));

        Move[] moves = board.get(coords(1, 5)).getValidMoves(coords(1, 5), board, new Move(null, null, null));

        assertEquals(5, moves.length);
    }
}
