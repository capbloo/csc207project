package interface_adapter;

import entity.Board;
import entity.Move;
import entity.Piece;
import interface_adapter.CheckGameEnds.CheckGameEndsPresenter;
import interface_adapter.CheckGameEnds.CheckGameEndsState;
import interface_adapter.CheckGameEnds.CheckGameEndsController;
import interface_adapter.CheckGameEnds.CheckGameEndsViewModel;
import use_case.CheckGameEnds.CheckGameEndsInputData;
import use_case.CheckGameEnds.CheckGameEndsInteractor;
import use_case.CheckGameEnds.CheckGameEndsOutputBoundary;

import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CheckGameEndsInterfaceTest {
    private ArrayList<Integer> coords(int x, int y) {
        ArrayList<Integer> coordinate = new ArrayList<>(2);
        coordinate.add(x);
        coordinate.add(y);

        return coordinate;
    }

    @org.junit.Test
    public void testGameNoEnds(){
        Board board = new Board();
        Piece piece = board.getBoardstate().get(coords(6,2));
        Move move = new Move(piece,coords(6,2),coords(6,4));
        board.makeMove(move);

        CheckGameEndsViewModel checkGameEndsViewModel = new CheckGameEndsViewModel();
        CheckGameEndsOutputBoundary checkGameEndsPresenter = new CheckGameEndsPresenter(checkGameEndsViewModel);

        CheckGameEndsInteractor checkGameEndsInteractor = new CheckGameEndsInteractor(checkGameEndsPresenter, board);
        CheckGameEndsController checkGameEndsController = new CheckGameEndsController(checkGameEndsInteractor);
        checkGameEndsController.execute(move);

        assertFalse((checkGameEndsViewModel.getState().getwin() && checkGameEndsViewModel.getState().getiswhite()
                && checkGameEndsViewModel.getState().getstale()));

    }

    @org.junit.Test
    public void testGameEnds(){
        Board board = new Board();
        Piece piece1 = board.getBoardstate().get(coords(6,2));
        Piece piece2 = board.getBoardstate().get(coords(5,7));
        Piece piece3 = board.getBoardstate().get(coords(7,2));
        Piece piece = board.getBoardstate().get(coords(4,8));
        Move move1 = new Move(piece1,coords(6,2),coords(6,4));
        Move move2 = new Move(piece2,coords(5,7),coords(5,6));
        Move move3 = new Move(piece3,coords(7,2),coords(7,4));
        Move move = new Move(piece,coords(4,8),coords(8,4));
        board.makeMove(move1);
        board.makeMove(move2);
        board.makeMove(move3);
        board.makeMove(move);

        CheckGameEndsViewModel checkGameEndsViewModel = new CheckGameEndsViewModel();
        CheckGameEndsOutputBoundary checkGameEndsPresenter = new CheckGameEndsPresenter(checkGameEndsViewModel);

        CheckGameEndsInteractor checkGameEndsInteractor = new CheckGameEndsInteractor(checkGameEndsPresenter,board);
        CheckGameEndsController checkGameEndsController = new CheckGameEndsController(checkGameEndsInteractor);
        checkGameEndsController.execute(move);

        assertTrue(checkGameEndsViewModel.getState().getwin());
        assertFalse(checkGameEndsViewModel.getState().getiswhite());
        assertFalse(checkGameEndsViewModel.getState().getstale());

    }
}
