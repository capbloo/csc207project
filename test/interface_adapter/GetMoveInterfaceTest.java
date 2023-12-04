package interface_adapter;

import entity.Board;
import entity.Move;
import entity.Pawn;
import interface_adapter.Get_move.GetMovePresenter;
import interface_adapter.Get_move.GetMoveViewModel;
import interface_adapter.Get_move.GetMoveController;
import interface_adapter.Get_move.GetMoveState;
import use_case.Get_move.GetMoveDataAccessInterface;
import use_case.Get_move.GetMoveInputData;
import use_case.Get_move.GetMoveInteractor;
import use_case.Get_move.GetMoveOutputBoundary;
import use_case.TestingGetMoveDAO;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class GetMoveInterfaceTest {
    private ArrayList<Integer> coords(int x, int y) {
        ArrayList<Integer> coordinate = new ArrayList<>(2);
        coordinate.add(x);
        coordinate.add(y);

        return coordinate;
    }
    @org.junit.Test
    public void testGetMove() {
        GetMoveInputData getMoveInputData = new GetMoveInputData();
        GetMoveViewModel getMoveViewModel = new GetMoveViewModel();
        GetMoveOutputBoundary getMoveOutputBoundary = new GetMovePresenter(getMoveViewModel);
        GetMoveDataAccessInterface getMoveDataAccessInterface = new TestingGetMoveDAO("a2a4");
        Board board = new Board();
        GetMoveInteractor getMoveInteractor = new GetMoveInteractor(getMoveOutputBoundary, getMoveDataAccessInterface, board);
        GetMoveController getMoveController = new GetMoveController(getMoveInteractor);
        getMoveController.execute();

        assertTrue(board.getBoardstate().get(coords(1,4)) instanceof Pawn);
        assertNull(board.getBoardstate().get(coords(1,2)));
        GetMoveState getmoveState = new GetMoveState();
        getMoveViewModel.setState(getmoveState);
        Move move = getMoveViewModel.getState().getMove();
        assertTrue(move == null);

    }
}
