package entity;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;


public class BoardTest {
    public ArrayList<Integer> coor(int x, int y){
        ArrayList<Integer> co = new ArrayList<Integer>();
        co.add(x);
        co.add(y);
        return co;
    }
    public Board setEmptyBoard(){
        Board board = new Board();
        for (int x = 1; x <= 8; x++) {
            for (int y = 1; y <= 8; y++) {
                board.getBoardstate().put(coor(x, y), null);
            }
        }
        return board;
    }
    @org.junit.Test
    public void testMakeMove(){
        Board board = new Board();
        Piece org = board.getBoardstate().get(coor(2,2));
        Move move1 = new Move(board.getBoardstate().get(coor(2,2)), coor(2,2), coor(2,4));
        board.makeMove(move1);
        assertEquals(org, board.getBoardstate().get(coor(2,4)));
        assertNull(board.getBoardstate().get(coor(2, 2)));
    }

    @org.junit.Test
    public void testMakeMovePromosion(){
        Board board = setEmptyBoard();
        board.getBoardstate().put(coor(3,7), new PieceBuilder().create("Pawn", "white"));
        Move move = new Move(board.getBoardstate().get(coor(3,7)), coor(3,7), coor(3,8));
        move.setIsPromotion();
        move.setPiecePromotedTo(new PieceBuilder().create("Queen", "white"));
        board.makeMove(move);
        assertTrue(board.getBoardstate().get(coor(3,8)) instanceof Queen);
        assertNull(board.getBoardstate().get(coor(3, 7)));
    }

    @org.junit.Test
    public void testBoard(){
        Board board = new Board();
        assertTrue(board.getBoardstate().get(coor(7,1)) instanceof Knight);
    }


    @org.junit.Test
    public void testMakeMoveCastle(){
        Board board = new Board();
        board.getBoardstate().remove(coor(6,1));
        board.getBoardstate().remove(coor(7,1));
        Move move = new Move(board.getBoardstate().get(coor(5,1)), coor(5,1), coor(7,1));
        move.setIsCastle();
        ChessButton rook = new ChessButton();
        ChessButton rook2 = new ChessButton();
        rook.setCoord(6, 1);
        rook2.setCoord(8, 1);
        move.setRookRemoved(rook2);
        move.setRookAdded(rook);
        board.makeMove(move);
        assertTrue(board.getBoardstate().get(coor(7,1)) instanceof King);
        assertTrue(board.getBoardstate().get(coor(6,1)) instanceof Rook);
        assertNull(board.getBoardstate().get(coor(5, 1)));
        assertNull(board.getBoardstate().get(coor(8, 1)));
    }

    @org.junit.Test
    public void testMakeMoveEnPassant(){
        Board board = new Board();
        Move move1 = new Move(board.getBoardstate().get(coor(5,2)), coor(5,2), coor(5,5));
        board.makeMove(move1);
        Move move2 = new Move(board.getBoardstate().get(coor(6,7)), coor(6,7), coor(6,5));
        board.makeMove(move2);
        Move move3 = new Move(board.getBoardstate().get(coor(5,5)), coor(5,5), coor(6,6));
        move3.setIsEnPassant();
        move3.setPieceCaptured();
        move3.setPieceCaptureLocation(coor(6,5));
        board.makeMove(move3);
        assertTrue(board.getBoardstate().get(coor(6,6)) instanceof Pawn);
        assertNull(board.getBoardstate().get(coor(5, 2)));
        assertNull(board.getBoardstate().get(coor(5, 5)));
        assertNull(board.getBoardstate().get(coor(6, 7)));
        assertNull(board.getBoardstate().get(coor(6, 5)));
    }

    @org.junit.Test
    public void testIsCheckMate1(){
        Board board = setEmptyBoard();
        Board board2 = new Board();
        Piece org = board2.getBoardstate().get(coor(2,2));
        Move move1 = new Move(org, coor(2,2), coor(2,4));
        board2.makeMove(move1);
        assertFalse(board.isCheckMate("white"));
        assertFalse(board.isCheckMate("black"));
        assertFalse(board2.isCheckMate("white"));
        assertFalse(board2.isCheckMate("black"));
    }
    @org.junit.Test
    public void testIsCheckMate2(){
        Board board = setEmptyBoard();
        Piece wking = new PieceBuilder().create("King", "white");
        Piece bking = new PieceBuilder().create("King", "black");
        Piece wqueen = new PieceBuilder().create("Queen", "white");
        Piece wknight = new PieceBuilder().create("Knight", "white");
        board.getBoardstate().put(coor(1,8),bking);
        board.getBoardstate().put(coor(5,1),wking);
        Move move = new Move(wking,coor(5,1),coor(5,2));
        board.makeMove(move);
        board.getBoardstate().put(coor(3,7),wqueen);
        board.getBoardstate().put(coor(2,6),wknight);
        assertFalse(board.isCheckMate("white"));
        assertTrue(board.isCheckMate("black"));
    }

    @org.junit.Test
    public void testIsCheckMate3(){
        Board board2 = new Board();
        assertFalse(board2.isCheckMate("white"));
        assertFalse(board2.isCheckMate("black"));
    }

    @org.junit.Test
    public void testisStaleMate(){
        Board board1 = setEmptyBoard();
        Board board = new Board();
        Piece org = board.getBoardstate().get(coor(2,2));
        Move move1 = new Move(org, coor(2,2), coor(2,4));
        board.makeMove(move1);
        assertFalse(board.isCheckMate("white"));
        assertFalse(board.isCheckMate("black"));
        assertFalse(board1.isCheckMate("white"));
        assertFalse(board1.isCheckMate("black"));
    }

    @org.junit.Test
    public void testisStaleMate2(){
        Board board = setEmptyBoard();
        Piece wking = new PieceBuilder().create("King", "white");
        Piece bking = new PieceBuilder().create("King", "black");
        Piece wqueen = new PieceBuilder().create("Queen", "white");
        board.getBoardstate().put(coor(7,8),bking);
        board.getBoardstate().put(coor(8,5),wking);
        Move move = new Move(wking,coor(8,5),coor(8,6));
        board.makeMove(move);
        board.getBoardstate().put(coor(6,6),wqueen);
        assertFalse(board.isStaleMate("white"));
        assertTrue(board.isStaleMate("black"));
    }

    @org.junit.Test
    public void testIsValidMove(){
        Board board2 = new Board();
        Move move = new Move(board2.getBoardstate().get(coor(2, 2)), coor(2,2), coor(2,3) );
        assertTrue(board2.isValidMove(move));
    }















}
