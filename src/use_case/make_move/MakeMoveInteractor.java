package use_case.make_move;

import entity.Board;
import entity.Move;
import entity.Piece;

import java.util.ArrayList;
import java.util.HashMap;

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

        board.makeMove(move);

        // testing en passent
        if (move.getIsEnPassant()) {
            ArrayList<Integer> coordToRemove = new ArrayList<>();
            coordToRemove.add(move.getDestination().get(0));
            coordToRemove.add(move.getDestination().get(1)-1);

            HashMap<ArrayList<Integer>, Piece> bs = board.getBoardstate();
            System.out.println(bs.get(coordToRemove) == null);
        }

        if (move.getIsPromotion()) {

        }

        // testing promotion
//        if (move.getIsPromotion()) {
//
//        }
        // push move to API if its valid
        // makeMoveDataAccessObject.pushMove(move);

        MakeMoveOutputData makeMoveOutputData = new MakeMoveOutputData(move, makeMoveInputData.getClickedButton());
        makeMovePresenter.prepareSuccessView(makeMoveOutputData);

    }
}





