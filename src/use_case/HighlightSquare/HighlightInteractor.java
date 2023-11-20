package use_case.HighlightSquare;

import java.util.ArrayList;
import java.util.HashMap;

import entity.Piece;
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
        ArrayList<Integer> position = highlightInputData.getposition();
        Piece piece = board.getBoardstate().get(position);
        HashMap<ArrayList<Integer>, Piece> boardState = board.getBoardstate();
        Move lastMove = board.getLastmove();

        Move[] validMoves = piece.getValidMoves(position, boardState, lastMove);

        HighlightOutputData highlightOutputData = new HighlightOutputData(validMoves);
        highlightPresenter.prepareSuccessView(highlightOutputData);

    }

}
