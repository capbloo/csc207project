package use_case.make_move;

import entity.Board;
import entity.Move;
import entity.Piece;

import java.util.ArrayList;

public class MakeMoveInteractor implements MakeMoveInputBoundary {
    final MakeMoveDataAccessInterface makeMoveDataAccessObject;
    final MakeMoveOutputBoundary makeMovePresenter;

    final Board board;

    public MakeMoveInteractor(MakeMoveDataAccessInterface makeMoveDataAccessObject, MakeMoveOutputBoundary makeMovePresenter, Board board) {
        this.makeMoveDataAccessObject = makeMoveDataAccessObject;
        this.makeMovePresenter = makeMovePresenter;
        this.board = board;
    }

    public void execute(MakeMoveInputData makeMoveInputData) {
        Move move = makeMoveInputData.getMove();
        Piece piece = move.getPieceMoving();
        Move[] validMoves = piece.getValidMoves(move.getOrigin(), board.getBoardstate(), board.getLastmove());
        // iterating through valid moves to see if the move the user made is in there
        for (Move validMove : validMoves) {
            if (move.equals(validMove)) {
                board.makeMove(move);
                // push move to API if its valid
                makeMoveDataAccessObject.pushMove(move);
                MakeMoveOutputData makeMoveOutputData = new MakeMoveOutputData(move, makeMoveInputData.getClickedButton());
                makeMovePresenter.prepareSuccessView(makeMoveOutputData);
                return;
            }
        }
        makeMovePresenter.prepareFailView();
    }
}



