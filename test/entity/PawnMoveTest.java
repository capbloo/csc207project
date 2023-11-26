package entity;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

public class PawnMoveTest {
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
    public void testPawnE2() {
        HashMap<ArrayList<Integer>, Piece> boardState = defaultBoardState();

        ArrayList<Integer> pos = coords(5, 2);

        Piece pawnE2 = boardState.get(pos); // white pawn at e2

        Move[] moves = pawnE2.getValidMoves(pos, boardState, new Move(null, null, null));

        assertEquals(2, moves.length);

        ArrayList<ArrayList<Integer>> moveDestinations = new ArrayList<>(2);

        for (Move move : moves) {
            moveDestinations.add(move.getDestination());
        }

        assertTrue(moveDestinations.contains(coords(5, 3))); // push by 1
        assertTrue(moveDestinations.contains(coords(5, 4))); // push by 2
    }

    @org.junit.Test
    public void testPawnD7() {
        HashMap<ArrayList<Integer>, Piece> boardState = defaultBoardState();

        ArrayList<Integer> pos = coords(4, 7);

        Piece pawnD7 = boardState.get(pos); // black pawn at d7

        Move[] moves = pawnD7.getValidMoves(pos, boardState, new Move(null, null, null));

        assertEquals(2, moves.length);

        ArrayList<ArrayList<Integer>> moveDestinations = new ArrayList<>(2);

        for (Move move : moves) {
            moveDestinations.add(move.getDestination());
        }

        assertTrue(moveDestinations.contains(coords(4, 6))); // push by 1
        assertTrue(moveDestinations.contains(coords(4, 5))); // push by 2
    }
    @org.junit.Test
    public void testCapture() {
        HashMap<ArrayList<Integer>, Piece> boardState = defaultBoardState();

        boardState.put(coords(5, 4), new Pawn("white"));
        boardState.put(coords(5, 2), null); // e4
        boardState.get(coords(5, 4)).pieceMove();

        boardState.put(coords(4, 5), new Pawn("black"));
        boardState.put(coords(4, 7), null); // d5
        boardState.get(coords(4, 5)).pieceMove();

        Piece pawnE4 = boardState.get(coords(5, 4));

        Move[] moves = pawnE4.getValidMoves(coords(5, 4), boardState,
                new Move(null, null, null));

        assertEquals(2, moves.length);

        ArrayList<ArrayList<Integer>> moveDestinations = new ArrayList<>(2);

        for (Move move : moves) {
            moveDestinations.add(move.getDestination());
        }

        assertTrue(moveDestinations.contains(coords(5, 5))); // push by 1
        assertTrue(moveDestinations.contains(coords(4, 5))); // capture d4

        // exactly one of the two is a capture
        assertTrue(moves[0].getIsPieceCaptured() ^ moves[1].getIsPieceCaptured());
    }

    @org.junit.Test
    public void testPromotion() {
        HashMap<ArrayList<Integer>, Piece> boardState = defaultBoardState();

        // put a pawn on e7 with no captures and e8 open
        boardState.put(coords(5, 7), new Pawn("white"));
        boardState.put(coords(6, 8), null);
        boardState.put(coords(5, 8), null);
        boardState.put(coords(4, 8), null);
        boardState.get(coords(5, 7)).pieceMove();

        Piece pawnE7 = boardState.get(coords(5, 7));

        Move[] moves = pawnE7.getValidMoves(coords(5, 7), boardState,
                new Move(null, null, null));

        assertEquals(1, moves.length);

        ArrayList<ArrayList<Integer>> moveDestinations = new ArrayList<>(2);

        for (Move move : moves) {
            moveDestinations.add(move.getDestination());
        }

        assertTrue(moveDestinations.contains(coords(5, 8))); // push to e8

        // should not be a capture
        assertFalse(moves[0].getIsPieceCaptured());

        // should be a promotion
        assertTrue(moves[0].getIsPromotion());
    }
    @org.junit.Test
    public void testPromotionCaptures() {
        HashMap<ArrayList<Integer>, Piece> boardState = defaultBoardState();

        // put a pawn on e7 with a piece blocking direct movement
        boardState.put(coords(5, 7), new Pawn("white"));
        boardState.put(coords(5, 8), new Pawn("black"));
        boardState.get(coords(5, 7)).pieceMove();

        Piece pawnE7 = boardState.get(coords(5, 7));

        Move[] moves = pawnE7.getValidMoves(coords(5, 7), boardState,
                new Move(null, null, null));

        assertEquals(2, moves.length);

        ArrayList<ArrayList<Integer>> moveDestinations = new ArrayList<>(2);

        for (Move move : moves) {
            moveDestinations.add(move.getDestination());
        }

        assertTrue(moveDestinations.contains(coords(6, 8))); // capture f8
        assertTrue(moveDestinations.contains(coords(4, 8))); // capture d8

        // both moves should be captures
        assertTrue(moves[0].getIsPieceCaptured() && moves[1].getIsPieceCaptured());

        // both moves should be promotions
        assertTrue(moves[0].getIsPromotion() && moves[1].getIsPromotion());
    }
    @org.junit.Test
    public void testPinnedPawn() {
        HashMap<ArrayList<Integer>, Piece> boardState = emptyBoard();

        // pawn should be pinned to king and have no valid moves
        boardState.put(coords(1, 1), new King("white"));
        boardState.put(coords(2, 2), new Pawn("white"));
        boardState.put(coords(8, 8), new Bishop("black"));
        boardState.put(coords(8, 7), new King("black"));

        Move[] moves = boardState.get(coords(2, 2)).getValidMoves(coords(2, 2),
                boardState, new Move(null, null, null));

        assertEquals(0, moves.length);
    }

    @org.junit.Test
    public void testBlockCheck() {
        HashMap<ArrayList<Integer>, Piece> boardState = emptyBoard();

        // pawn should be pinned to king and have no valid moves
        boardState.put(coords(1, 3), new King("white"));
        boardState.put(coords(2, 2), new Pawn("white"));
        boardState.put(coords(8, 3), new Rook("black"));
        boardState.put(coords(8, 7), new King("black"));

        Move[] moves = boardState.get(coords(2, 2)).getValidMoves(coords(2, 2),
                boardState, new Move(null, null, null));

        assertEquals(1, moves.length);

        // pawn's only valid move is to go to b3 to block the rook attacking the king
        assertEquals(moves[0].getDestination(), coords(2, 3));
    }

    @org.junit.Test
    public void testEnPassant() {
        HashMap<ArrayList<Integer>, Piece> boardState = defaultBoardState();

        Pawn whitePawn = new Pawn("white");
        whitePawn.pieceMove();

        boardState.put(coords(5, 2), null);
        boardState.put(coords(5, 5), whitePawn);

        Pawn blackPawn = new Pawn("black");
        blackPawn.pieceMove();

        boardState.put(coords(4, 7), null);
        boardState.put(coords(4, 5), blackPawn);

        Move lastMovePawnPush = new Move(blackPawn, coords(4, 7), coords(4, 5));

        Move[] moves = whitePawn.getValidMoves(coords(5, 5), boardState, lastMovePawnPush);

        assertEquals(2, moves.length);

        assertTrue(moves[0].getIsEnPassant() || moves[1].getIsEnPassant());
    }

    @org.junit.Test
    public void testInvalidBoard() {
        HashMap<ArrayList<Integer>, Piece> boardState = emptyBoard();

        Pawn pawn = new Pawn("white");

        boardState.put(coords(2, 5), pawn);

        assertThrows(RuntimeException.class, () -> {
            pawn.getValidMoves(coords(2, 5), boardState, null);
        });
    }
}
