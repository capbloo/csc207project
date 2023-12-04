package use_case;

import java.util.ArrayList;
import java.util.HashMap;

import entity.*;

import use_case.Get_move.GetMoveDataAccessInterface;
import use_case.Get_move.GetMoveInputData;
import use_case.Get_move.GetMoveInteractor;
import use_case.Get_move.GetMoveOutputBoundary;


import interface_adapter.Get_move.GetMovePresenter;
import interface_adapter.Get_move.GetMoveViewModel;

import static org.junit.Assert.*;
public class GetMoveTest {
    private ArrayList<Integer> coords(int x, int y) {
        ArrayList<Integer> coordinate = new ArrayList<>(2);
        coordinate.add(x);
        coordinate.add(y);

        return coordinate;
    }
    public Board setEmptyBoard(){
        Board board = new Board();
        for (int x = 1; x <= 8; x++) {
            for (int y = 1; y <= 8; y++) {
                board.getBoardstate().put(coords(x, y), null);
            }
        }
        return board;
    }
    @org.junit.Test
    public void testGetMove() {
        GetMoveInputData getMoveInputData = new GetMoveInputData();
        GetMoveViewModel getMoveViewModel = new GetMoveViewModel();
        GetMoveOutputBoundary getMoveOutputBoundary = new GetMovePresenter(getMoveViewModel);
        GetMoveDataAccessInterface getMoveDataAccessInterface = new TestingGetMoveDAO("a2a4");
        Board board = new Board();
        Move move = new Move(board.getBoardstate().get(coords(1,2)), coords(1,2),coords(1,4));
        GetMoveInteractor getMoveInteractor = new GetMoveInteractor(getMoveOutputBoundary, getMoveDataAccessInterface, board);
        getMoveInteractor.execute();

        assertTrue(board.getBoardstate().get(coords(1,4)) instanceof Pawn);
        assertNull(board.getBoardstate().get(coords(1,2)));

    }

    @org.junit.Test
    public void testCastle1() {
        GetMoveInputData getMoveInputData = new GetMoveInputData();
        GetMoveViewModel getMoveViewModel = new GetMoveViewModel();
        GetMoveOutputBoundary getMoveOutputBoundary = new GetMovePresenter(getMoveViewModel);

        Board board = new Board();
        Move move1 = new Move(board.getBoardstate().get(coords(6,2)), coords(6,2), coords(6,4));
        Move move2 = new Move(board.getBoardstate().get(coords(7,2)), coords(7,2), coords(7,4));
        Move move3 = new Move(board.getBoardstate().get(coords(7,1)), coords(7,1), coords(6,3));
        Move move4 = new Move(board.getBoardstate().get(coords(6,1)), coords(6,1), coords(8,3));
        board.makeMove(move1);
        board.makeMove(move2);
        board.makeMove(move3);
        board.makeMove(move4);

        GetMoveDataAccessInterface getMoveDataAccessInterface = new TestingGetMoveDAO("e1g1");
        GetMoveInteractor getMoveInteractor = new GetMoveInteractor(getMoveOutputBoundary, getMoveDataAccessInterface, board);
        getMoveInteractor.execute();

        assertTrue(board.getBoardstate().get(coords(7,1)) instanceof King);
        assertTrue(board.getBoardstate().get(coords(6,1)) instanceof Rook);
        assertNull(board.getBoardstate().get(coords(5, 1)));
        assertNull(board.getBoardstate().get(coords(8, 1)));

    }

    @org.junit.Test
    public void testCastle2() {
        GetMoveInputData getMoveInputData = new GetMoveInputData();
        GetMoveViewModel getMoveViewModel = new GetMoveViewModel();
        GetMoveOutputBoundary getMoveOutputBoundary = new GetMovePresenter(getMoveViewModel);

        Board board = setEmptyBoard();
        Piece Rook = new Rook("black");
        board.getBoardstate().put(coords(8,8),Rook);
        Piece King = new King("black");
        board.getBoardstate().put(coords(5,8),King);

        GetMoveDataAccessInterface getMoveDataAccessInterface = new TestingGetMoveDAO("e8g8");
        GetMoveInteractor getMoveInteractor = new GetMoveInteractor(getMoveOutputBoundary, getMoveDataAccessInterface, board);
        getMoveInteractor.execute();

        assertTrue(board.getBoardstate().get(coords(7,8)) instanceof King);
        assertTrue(board.getBoardstate().get(coords(6,8)) instanceof Rook);
        assertNull(board.getBoardstate().get(coords(5, 8)));
        assertNull(board.getBoardstate().get(coords(8, 8)));

    }

    @org.junit.Test
    public void testCastle3() {
        GetMoveInputData getMoveInputData = new GetMoveInputData();
        GetMoveViewModel getMoveViewModel = new GetMoveViewModel();
        GetMoveOutputBoundary getMoveOutputBoundary = new GetMovePresenter(getMoveViewModel);

        Board board = setEmptyBoard();
        Piece Rook = new Rook("white");
        board.getBoardstate().put(coords(1,1),Rook);
        Piece King = new King("white");
        board.getBoardstate().put(coords(5,1),King);

        GetMoveDataAccessInterface getMoveDataAccessInterface = new TestingGetMoveDAO("e1c1");
        GetMoveInteractor getMoveInteractor = new GetMoveInteractor(getMoveOutputBoundary, getMoveDataAccessInterface, board);
        getMoveInteractor.execute();

        assertTrue(board.getBoardstate().get(coords(3,1)) instanceof King);
        assertTrue(board.getBoardstate().get(coords(4,1)) instanceof Rook);
        assertNull(board.getBoardstate().get(coords(5, 1)));
        assertNull(board.getBoardstate().get(coords(1, 1)));

    }
    @org.junit.Test
    public void testCastle4() {
        GetMoveInputData getMoveInputData = new GetMoveInputData();
        GetMoveViewModel getMoveViewModel = new GetMoveViewModel();
        GetMoveOutputBoundary getMoveOutputBoundary = new GetMovePresenter(getMoveViewModel);

        Board board = setEmptyBoard();
        Piece Rook = new Rook("black");
        board.getBoardstate().put(coords(1,8),Rook);
        Piece King = new King("black");
        board.getBoardstate().put(coords(5,8),King);

        GetMoveDataAccessInterface getMoveDataAccessInterface = new TestingGetMoveDAO("e8c8");
        GetMoveInteractor getMoveInteractor = new GetMoveInteractor(getMoveOutputBoundary, getMoveDataAccessInterface, board);
        getMoveInteractor.execute();

        assertTrue(board.getBoardstate().get(coords(3,8)) instanceof King);
        assertTrue(board.getBoardstate().get(coords(4,8)) instanceof Rook);
        assertNull(board.getBoardstate().get(coords(8, 8)));
        assertNull(board.getBoardstate().get(coords(5, 8)));

    }
    @org.junit.Test
    public void testPromotion() {
        GetMoveInputData getMoveInputData = new GetMoveInputData();
        GetMoveViewModel getMoveViewModel = new GetMoveViewModel();
        GetMoveOutputBoundary getMoveOutputBoundary = new GetMovePresenter(getMoveViewModel);

        Board board = setEmptyBoard();
        Pawn pawn = new Pawn("black");
        King king = new King("black");
        board.getBoardstate().put(coords(5,8),king);
        board.getBoardstate().put(coords(1,2),pawn);

        GetMoveDataAccessInterface getMoveDataAccessInterface = new TestingGetMoveDAO("a2a1q");
        GetMoveInteractor getMoveInteractor = new GetMoveInteractor(getMoveOutputBoundary, getMoveDataAccessInterface, board);
        getMoveInteractor.execute();

        assertTrue(board.getBoardstate().get(coords(1,1)) instanceof Queen);
        assertNull(board.getBoardstate().get(coords(1, 2)));


    }

}
