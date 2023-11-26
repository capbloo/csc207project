package entity;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class KingMoveTest {
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
    public void testUnrestrictedMoves() {
        HashMap<ArrayList<Integer>, Piece> board = emptyBoard();

        board.put(coords(7, 3), new King("black"));

        board.put(coords(1,1), new King("white"));

        Move[] moves = board.get(coords(7, 3)).getValidMoves(coords(7, 3), board, new Move(null, null, null));

        assertEquals(8, moves.length);
    }

    @org.junit.Test
    public void testEnemyKingBlocksMovement() {
        HashMap<ArrayList<Integer>, Piece> board = emptyBoard();

        board.put(coords(8, 3), new King("black"));

        board.put(coords(6,3), new King("white"));

        Move[] moves = board.get(coords(8, 3)).getValidMoves(coords(8, 3), board, new Move(null, null, null));

        assertEquals(2, moves.length);
    }

    @org.junit.Test
    public void testAllDirectionsInCheck() {
        HashMap<ArrayList<Integer>, Piece> board = emptyBoard();

        board.put(coords(8, 3), new King("black"));

        board.put(coords(1, 2), new Rook("white"));
        board.put(coords(1, 4), new Rook("white"));
        board.put(coords(7, 1), new Rook("white"));

        board.put(coords(1,3), new King("white"));

        Move[] moves = board.get(coords(8, 3)).getValidMoves(coords(8, 3), board, new Move(null, null, null));

        assertEquals(0, moves.length);
    }

    @org.junit.Test
    public void testKingCapture() {
        HashMap<ArrayList<Integer>, Piece> board = emptyBoard();

        board.put(coords(8, 3), new King("black"));

        board.put(coords(1, 2), new Rook("white"));
        board.put(coords(1, 4), new Rook("white"));
        board.put(coords(7, 3), new Rook("white"));

        board.put(coords(1,3), new King("white"));

        Move[] moves = board.get(coords(8, 3)).getValidMoves(coords(8, 3), board, new Move(null, null, null));

        assertEquals(1, moves.length);
        assertTrue(moves[0].getIsPieceCaptured());
    }

    @org.junit.Test
    public void testCannotCaptureDefendedPiece() {
        HashMap<ArrayList<Integer>, Piece> board = emptyBoard();

        board.put(coords(8, 3), new King("black"));

        board.put(coords(1, 2), new Rook("white"));
        board.put(coords(1, 4), new Rook("white"));
        board.put(coords(7, 1), new Rook("white"));
        board.put(coords(7, 3), new Pawn("white"));

        board.put(coords(1,3), new King("white"));

        Move[] moves = board.get(coords(8, 3)).getValidMoves(coords(8, 3), board, new Move(null, null, null));

        assertEquals(0, moves.length);
    }

    @org.junit.Test
    public void testWhiteKingSideCastle() {
        HashMap<ArrayList<Integer>, Piece> board = emptyBoard();

        board.put(coords(5, 8), new King("black"));

        board.put(coords(1, 2), new Rook("black"));

        board.put(coords(8, 1), new Rook("white"));
        board.put(coords(5, 1), new King("white"));

        Move[] moves = board.get(coords(5, 1)).getValidMoves(coords(5, 1), board, null);

        assertEquals(3, moves.length);

        assertTrue(moves[0].getDestination().equals(coords(7, 1)) ||
                moves[1].getDestination().equals(coords(7, 1)) ||
                moves[2].getDestination().equals(coords(7, 1)));

        assertTrue(moves[0].getIsCastle() || moves[1].getIsCastle() || moves[2].getIsCastle());
    }

    @org.junit.Test
    public void testWhiteQueenSideCastle() {
        HashMap<ArrayList<Integer>, Piece> board = emptyBoard();

        board.put(coords(5, 8), new King("black"));

        board.put(coords(1, 2), new Rook("black"));

        board.put(coords(1, 1), new Rook("white"));
        board.put(coords(5, 1), new King("white"));

        Move[] moves = board.get(coords(5, 1)).getValidMoves(coords(5, 1), board, null);

        assertEquals(3, moves.length);

        assertTrue(moves[0].getDestination().equals(coords(3, 1)) ||
                moves[1].getDestination().equals(coords(3, 1)) ||
                moves[2].getDestination().equals(coords(3, 1)));

        assertTrue(moves[0].getIsCastle() || moves[1].getIsCastle() || moves[2].getIsCastle());
    }

    @org.junit.Test
    public void testBlackKingSideCastle() {
        HashMap<ArrayList<Integer>, Piece> board = emptyBoard();

        board.put(coords(5, 8), new King("black"));
        board.put(coords(1, 8), new Rook("black"));

        board.put(coords(1, 7), new Rook("white"));

        board.put(coords(5, 1), new King("white"));

        Move[] moves = board.get(coords(5, 8)).getValidMoves(coords(5, 8), board, null);

        assertEquals(3, moves.length);

        assertTrue(moves[0].getDestination().equals(coords(3, 8)) ||
                moves[1].getDestination().equals(coords(3, 8)) ||
                moves[2].getDestination().equals(coords(3, 8)));

        assertTrue(moves[0].getIsCastle() || moves[1].getIsCastle() || moves[2].getIsCastle());
    }

    @org.junit.Test
    public void testIllegalKingColor() {
        HashMap<ArrayList<Integer>, Piece> board = emptyBoard();

        board.put(coords(5, 8), new King("purple"));
        board.put(coords(5, 1), new King("white"));

        assertThrows(RuntimeException.class, () -> board.get(coords(5, 8)).getValidMoves(coords(5, 8), board, null));
    }

    @org.junit.Test
    public void testPawnsBlockMovement() {
        HashMap<ArrayList<Integer>, Piece> board = defaultBoardState();

        board.put(coords(5, 1), null);
        board.put(coords(5, 5), new King("white"));

        Move[] moves = board.get(coords(5, 5)).getValidMoves(coords(5, 5), board, null);

        assertEquals(5, moves.length);
    }
}
