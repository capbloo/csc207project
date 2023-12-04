package view;

import data_access.GetMoveDataAccessObject;
import data_access.MakeMoveDataAccessObject;
import entity.Board;
import entity.ChessButton;
import interface_adapter.CheckGameEnds.CheckGameEndsController;
import interface_adapter.CheckGameEnds.CheckGameEndsPresenter;
import interface_adapter.CheckGameEnds.CheckGameEndsViewModel;
import interface_adapter.Get_move.GetMoveController;
import interface_adapter.Get_move.GetMovePresenter;
import interface_adapter.Get_move.GetMoveViewModel;
import interface_adapter.HighlightSquare.HighlightController;
import interface_adapter.HighlightSquare.HighlightPresenter;
import interface_adapter.HighlightSquare.HighlightViewModel;
import interface_adapter.make_move.MakeMoveController;
import interface_adapter.make_move.MakeMovePresenter;
import interface_adapter.make_move.MakeMoveViewModel;
import use_case.CheckGameEnds.CheckGameEndsInteractor;
import use_case.Get_move.GetMoveInteractor;
import use_case.HighlightSquare.HighlightInteractor;
import use_case.make_move.MakeMoveInteractor;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class BoardViewTest {
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
                chessButton.setSquareColour("green");

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
    public void TestInstantiateswhite() {
        Board board = new Board();
        MakeMoveViewModel makeMoveViewModel = new MakeMoveViewModel();
        MakeMovePresenter makeMovePresenter = new MakeMovePresenter(makeMoveViewModel);
        MakeMoveDataAccessObject makeMoveDataAccessObject = new MakeMoveDataAccessObject("gameID");
        MakeMoveInteractor makeMoveInteractor = new MakeMoveInteractor(makeMoveDataAccessObject, makeMovePresenter, board);
        MakeMoveController makeMoveController = new MakeMoveController(makeMoveInteractor);

        HighlightViewModel highlightViewModel = new HighlightViewModel();
        HighlightPresenter highlightPresenter = new HighlightPresenter(highlightViewModel);
        HighlightInteractor highlightInteractor = new HighlightInteractor(highlightPresenter, board);
        HighlightController highlightController = new HighlightController(highlightInteractor);

        CheckGameEndsViewModel checkGameEndsViewModel = new CheckGameEndsViewModel();
        CheckGameEndsPresenter checkGameEndsPresenter = new CheckGameEndsPresenter(checkGameEndsViewModel);
        CheckGameEndsInteractor checkGameEndsInteractor = new CheckGameEndsInteractor(checkGameEndsPresenter, board);
        CheckGameEndsController checkGameEndsController = new CheckGameEndsController(checkGameEndsInteractor);

        GetMoveViewModel getMoveViewModel = new GetMoveViewModel();
        GetMovePresenter getMovePresenter = new GetMovePresenter(getMoveViewModel);
        GetMoveDataAccessObject getMoveDataAccessObject = new GetMoveDataAccessObject("gameID");
        GetMoveInteractor getMoveInteractor = new GetMoveInteractor(getMovePresenter, getMoveDataAccessObject, board);
        GetMoveController getMoveController = new GetMoveController(getMoveInteractor);


        BoardView boardView = new BoardView(board, makeMoveController, makeMoveViewModel,
                highlightController, highlightViewModel,
                checkGameEndsController, checkGameEndsViewModel, "white", getMoveViewModel, getMoveController);

        assertFalse(boardView == null);
    }

    @org.junit.Test
    public void TestInstantiatesblack() {
        Board board = new Board();
        MakeMoveViewModel makeMoveViewModel = new MakeMoveViewModel();
        MakeMovePresenter makeMovePresenter = new MakeMovePresenter(makeMoveViewModel);
        MakeMoveDataAccessObject makeMoveDataAccessObject = new MakeMoveDataAccessObject("gameID");
        MakeMoveInteractor makeMoveInteractor = new MakeMoveInteractor(makeMoveDataAccessObject, makeMovePresenter, board);
        MakeMoveController makeMoveController = new MakeMoveController(makeMoveInteractor);

        HighlightViewModel highlightViewModel = new HighlightViewModel();
        HighlightPresenter highlightPresenter = new HighlightPresenter(highlightViewModel);
        HighlightInteractor highlightInteractor = new HighlightInteractor(highlightPresenter, board);
        HighlightController highlightController = new HighlightController(highlightInteractor);

        CheckGameEndsViewModel checkGameEndsViewModel = new CheckGameEndsViewModel();
        CheckGameEndsPresenter checkGameEndsPresenter = new CheckGameEndsPresenter(checkGameEndsViewModel);
        CheckGameEndsInteractor checkGameEndsInteractor = new CheckGameEndsInteractor(checkGameEndsPresenter, board);
        CheckGameEndsController checkGameEndsController = new CheckGameEndsController(checkGameEndsInteractor);

        GetMoveViewModel getMoveViewModel = new GetMoveViewModel();
        GetMovePresenter getMovePresenter = new GetMovePresenter(getMoveViewModel);
        GetMoveDataAccessObject getMoveDataAccessObject = new GetMoveDataAccessObject("gameID");
        GetMoveInteractor getMoveInteractor = new GetMoveInteractor(getMovePresenter, getMoveDataAccessObject, board);
        GetMoveController getMoveController = new GetMoveController(getMoveInteractor);


        BoardView boardView = new BoardView(board, makeMoveController, makeMoveViewModel,
                highlightController, highlightViewModel,
                checkGameEndsController, checkGameEndsViewModel, "black", getMoveViewModel, getMoveController);

        assertFalse(boardView == null);
    }

    @org.junit.Test
    public void TestHighlightFunctions() {
        Board board = new Board();
        MakeMoveViewModel makeMoveViewModel = new MakeMoveViewModel();
        MakeMovePresenter makeMovePresenter = new MakeMovePresenter(makeMoveViewModel);
        MakeMoveDataAccessObject makeMoveDataAccessObject = new MakeMoveDataAccessObject("gameID");
        MakeMoveInteractor makeMoveInteractor = new MakeMoveInteractor(makeMoveDataAccessObject, makeMovePresenter, board);
        MakeMoveController makeMoveController = new MakeMoveController(makeMoveInteractor);

        HighlightViewModel highlightViewModel = new HighlightViewModel();
        HighlightPresenter highlightPresenter = new HighlightPresenter(highlightViewModel);
        HighlightInteractor highlightInteractor = new HighlightInteractor(highlightPresenter, board);
        HighlightController highlightController = new HighlightController(highlightInteractor);

        CheckGameEndsViewModel checkGameEndsViewModel = new CheckGameEndsViewModel();
        CheckGameEndsPresenter checkGameEndsPresenter = new CheckGameEndsPresenter(checkGameEndsViewModel);
        CheckGameEndsInteractor checkGameEndsInteractor = new CheckGameEndsInteractor(checkGameEndsPresenter, board);
        CheckGameEndsController checkGameEndsController = new CheckGameEndsController(checkGameEndsInteractor);

        GetMoveViewModel getMoveViewModel = new GetMoveViewModel();
        GetMovePresenter getMovePresenter = new GetMovePresenter(getMoveViewModel);
        GetMoveDataAccessObject getMoveDataAccessObject = new GetMoveDataAccessObject("gameID");
        GetMoveInteractor getMoveInteractor = new GetMoveInteractor(getMovePresenter, getMoveDataAccessObject, board);
        GetMoveController getMoveController = new GetMoveController(getMoveInteractor);


        BoardView boardView = new BoardView(board, makeMoveController, makeMoveViewModel,
                highlightController, highlightViewModel,
                checkGameEndsController, checkGameEndsViewModel, "white", getMoveViewModel, getMoveController);
        HashMap<ArrayList<Integer>, ChessButton> buttonList = buttonList(board);
        buttonList.get(coords(1, 1)).setHighlight();
        boardView.highlight(buttonList);
        boardView.unhighlight(buttonList);

        assertFalse(boardView == null);
    }
}

