package use_case.HighlightSquare;

import java.util.ArrayList;
import java.util.HashMap;

import entity.Piece;
import entity.ChessButton;
import entity.Board;
import entity.Move;

public class HighlightInteractor implements HighlightInputBoundary {
    final HighlightOutputBoundary highlightPresenter;

    final Board board;

    public HighlightInteractor(HighlightOutputBoundary highlightOutputBoundary, Board board) {
        this.highlightPresenter = highlightOutputBoundary;
        this.board = board;

    }

    public void execute(HighlightInputData highlightInputData) {
        ChessButton clickedButton = highlightInputData.getClickedButton();
        HashMap<ArrayList<Integer>, ChessButton> buttonList = highlightInputData.getButtonList();
        ArrayList<Integer> position = new ArrayList<>();
        position.add(clickedButton.getRow());
        position.add(clickedButton.getCol());
        Piece piece = board.getBoardstate().get(position);
        System.out.println(position);
        System.out.println(piece.symbolToString());
        HashMap<ArrayList<Integer>, Piece> boardState = board.getBoardstate();
        Move lastMove = board.getLastmove();

        Move[] validMoves = piece.getValidMoves(position, boardState, lastMove);
        ArrayList<ArrayList<Integer>> moveCoordinates = new ArrayList<>();

        for (Move move : validMoves) {
            ArrayList<Integer> coordinate = move.getDestination();
            moveCoordinates.add(coordinate);
        }

        HighlightOutputData highlightOutputData = new HighlightOutputData(moveCoordinates,
                buttonList);
        highlightPresenter.prepareSuccessView(highlightOutputData);

    }

}
