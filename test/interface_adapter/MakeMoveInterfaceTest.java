package interface_adapter;

import entity.*;
import interface_adapter.make_move.MakeMovePresenter;
import interface_adapter.make_move.MakeMoveViewModel;
import interface_adapter.make_move.MakeMoveController;
import interface_adapter.make_move.MakeMoveState;
import use_case.TestingMakeMoveDAO;
import use_case.make_move.MakeMoveDataAccessInterface;
import use_case.make_move.MakeMoveInputData;
import use_case.make_move.MakeMoveInteractor;
import use_case.make_move.MakeMoveOutputBoundary;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class MakeMoveInterfaceTest {
    private ArrayList<Integer> coords(int x, int y) {
        ArrayList<Integer> coordinate = new ArrayList<>(2);
        coordinate.add(x);
        coordinate.add(y);

        return coordinate;
    }

    private HashMap<ArrayList<Integer>, ChessButton> buttonList(Board board) {
        HashMap<ArrayList<Integer>, ChessButton> buttonList = new HashMap<>();

        for (int y = 8; y > 0; y--) {
            for (int x = 0; x < 8; x++) {
                ChessButton chessButton = new ChessButton();
                ArrayList<Integer> coord = coords(x + 1, y);
                chessButton.setCoord(x + 1, y);

                if (!(board.getBoardstate().get(coord) == null)) {
                    chessButton.setPiece(board.getBoardstate().get(coord).symbolToString());
                    chessButton.setPieceColour(board.getBoardstate().get(coord).getColor());

                }
                buttonList.put(coord, chessButton);
            }
        }
        return buttonList;
    }

    @org.junit.Test
    public void testAllowedMove(){
        Board board = new Board();
        Piece piece = board.getBoardstate().get(coords(4,2));
        Move move = new Move(piece,coords(4,2),coords(4,3));

        HashMap<ArrayList<Integer>, ChessButton> buttonList = buttonList(board);
        ChessButton chessButton = buttonList.get(coords(4,3));

        MakeMoveDataAccessInterface makeMoveDataAccessInterface = new TestingMakeMoveDAO();
        MakeMoveViewModel makeMoveViewModel = new MakeMoveViewModel();
        PropertyChangeListener propertyChangeListener = null;
        makeMoveViewModel.addPropertyChangeListener(propertyChangeListener);
        MakeMoveOutputBoundary makeMovePresenter = new MakeMovePresenter(makeMoveViewModel);

        MakeMoveInteractor makeMoveInteractor = new MakeMoveInteractor(makeMoveDataAccessInterface, makeMovePresenter, board);
        MakeMoveController moveController = new MakeMoveController(makeMoveInteractor);
        moveController.execute(move, chessButton);
        makeMoveViewModel.getState().getBoardstate();


        assertTrue(board.getBoardstate().get(coords(4,3)) instanceof Pawn);
        assertNull(board.getBoardstate().get(coords(4,2)));
        TestingMakeMoveDAO testingMakeMoveDAO = (TestingMakeMoveDAO) makeMoveDataAccessInterface;
        assertEquals(move, testingMakeMoveDAO.getReceivedmove());
        assertEquals(move, makeMoveViewModel.getState().getMove());

    }
}
