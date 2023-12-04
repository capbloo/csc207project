package interface_adapter;
import entity.Board;
import entity.ChessButton;
import interface_adapter.HighlightSquare.HighlightController;
import interface_adapter.HighlightSquare.HighlightPresenter;
import interface_adapter.HighlightSquare.HighlightViewModel;
import use_case.HighlightSquare.HighlightInputBoundary;
import use_case.HighlightSquare.HighlightInteractor;
import use_case.HighlightSquare.HighlightOutputBoundary;


import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class HighlighSquareInterfaceTest {
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
    public void highlightInterfaceTest(){
        Board board = new Board();
        HashMap<ArrayList<Integer>, ChessButton> buttonList = buttonList(board);
        ChessButton clickedButton = buttonList.get(coords(1,2));

        HighlightViewModel highlightViewModel = new HighlightViewModel();
        PropertyChangeListener propertyChangeListener = null;
        highlightViewModel.addPropertyChangeListener(propertyChangeListener);
        HighlightOutputBoundary highlightPresenter = new HighlightPresenter(highlightViewModel);

        HighlightInputBoundary highlightInteractor = new HighlightInteractor(highlightPresenter, board);
        HighlightController highlightController = new HighlightController(highlightInteractor);
        highlightController.execute(clickedButton, buttonList);

        Board defaultBoard = new Board();
        HashMap<ArrayList<Integer>, ChessButton> defaultButtonList = buttonList(defaultBoard);
        defaultButtonList.get(coords(1,3)).setHighlight();
        defaultButtonList.get(coords(1,4)).setHighlight();


        for (Map.Entry<ArrayList<Integer>, ChessButton> entry: defaultButtonList.entrySet()){
            ArrayList<Integer> coord = entry.getKey();
            ChessButton chessButton = entry.getValue();
            assertEquals(buttonList.get(coord).isHighlighted(),chessButton.isHighlighted());
        }
    }




}
