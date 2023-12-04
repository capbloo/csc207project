package use_case;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import entity.Board;
import entity.Piece;
import entity.Move;
import entity.ChessButton;

import use_case.HighlightSquare.HighlightInputData;
import use_case.HighlightSquare.HighlightInteractor;
import use_case.HighlightSquare.HighlightOutputBoundary;

import interface_adapter.HighlightSquare.HighlightPresenter;
import interface_adapter.HighlightSquare.HighlightViewModel;

import static org.junit.Assert.*;

public class HighlightSquareTest {
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
 /*   private HashMap<ArrayList<Integer>, ChessButton> copyButtonList(HashMap<ArrayList<Integer>, ChessButton> buttonList){
        HashMap<ArrayList<Integer>, ChessButton> deepCopy = new HashMap<>();

        for (Map.Entry<ArrayList<Integer>, ChessButton> entry : buttonList.entrySet()) {
            ArrayList<Integer> keyCopy = new ArrayList<>(entry.getKey());
            ChessButton valueCopy = new ChessButton();

            JButton originalButton = entry.getValue();
            valueCopy.setText(originalButton.getText());

            deepCopy.put(keyCopy,valueCopy);
        }
        return deepCopy;
    }*/
    @org.junit.Test
    public void testHighlight(){
        Board board = new Board();
        HashMap<ArrayList<Integer>, ChessButton> buttonList = buttonList(board);

        ChessButton clickedButton = buttonList.get(coords(1,2));
        HighlightInputData highlightInputData = new HighlightInputData(clickedButton,buttonList);

        HighlightViewModel highlightViewModel = new HighlightViewModel();
        HighlightOutputBoundary highlightPresenter = new HighlightPresenter(highlightViewModel);

        HighlightInteractor highlightInteractor = new HighlightInteractor(highlightPresenter, board);
        highlightInteractor.execute(highlightInputData);

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

    @org.junit.Test
    public void testNoHighlight(){
        Board board = new Board();
        HashMap<ArrayList<Integer>, ChessButton> buttonList = buttonList(board);

        ChessButton clickedButton = buttonList.get(coords(1,1));
        HighlightInputData highlightInputData = new HighlightInputData(clickedButton,buttonList);

        HighlightViewModel highlightViewModel = new HighlightViewModel();
        HighlightOutputBoundary highlightPresenter = new HighlightPresenter(highlightViewModel);

        HighlightInteractor highlightInteractor = new HighlightInteractor(highlightPresenter, board);
        highlightInteractor.execute(highlightInputData);

        Board defaultBoard = new Board();
        HashMap<ArrayList<Integer>, ChessButton> defaultButtonList = buttonList(defaultBoard);

        for (Map.Entry<ArrayList<Integer>, ChessButton> entry: defaultButtonList.entrySet()){
            ArrayList<Integer> coord = entry.getKey();
            ChessButton chessButton = entry.getValue();
            assertEquals(buttonList.get(coord).isHighlighted(),chessButton.isHighlighted());
        }
    }

    @org.junit.Test
    public void testHighlightAfterMove(){
        Board board = new Board();
        HashMap<ArrayList<Integer>, ChessButton> buttonList = buttonList(board);

        Piece piece = board.getBoardstate().get(coords(4,2));
        Move move = new Move(piece,coords(4,2),coords(4,3));
        board.makeMove(move);

        ChessButton clickedButton = buttonList.get(coords(3,1));
        HighlightInputData highlightInputData = new HighlightInputData(clickedButton,buttonList);

        HighlightViewModel highlightViewModel = new HighlightViewModel();
        HighlightOutputBoundary highlightPresenter = new HighlightPresenter(highlightViewModel);

        HighlightInteractor highlightInteractor = new HighlightInteractor(highlightPresenter, board);
        highlightInteractor.execute(highlightInputData);

        Board defaultBoard = new Board();
        HashMap<ArrayList<Integer>, ChessButton> defaultButtonList = buttonList(defaultBoard);
        defaultButtonList.get(coords(4,2)).setHighlight();
        defaultButtonList.get(coords(5,3)).setHighlight();
        defaultButtonList.get(coords(6,4)).setHighlight();
        defaultButtonList.get(coords(7,5)).setHighlight();
        defaultButtonList.get(coords(8,6)).setHighlight();

        for (Map.Entry<ArrayList<Integer>, ChessButton> entry: defaultButtonList.entrySet()){
            ArrayList<Integer> coord = entry.getKey();
            ChessButton chessButton = entry.getValue();
            assertEquals(buttonList.get(coord).isHighlighted(),chessButton.isHighlighted());
        }
    }
}