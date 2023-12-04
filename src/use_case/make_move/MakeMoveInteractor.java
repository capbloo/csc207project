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

//        HashMap<ArrayList<Integer>, Piece> bs = board.getBoardstate();

        // testing en passant
//        if (move.getIsEnPassant()) {
//            ArrayList<Integer> coordToRemove = new ArrayList<>();
//            coordToRemove.add(move.getDestination().get(0));
//            coordToRemove.add(move.getDestination().get(1)-1);
//            System.out.println(bs.get(coordToRemove) == null);
//        }

//         testing promotion
//        if (move.getIsPromotion()) {
//            System.out.println(bs.get(move.getDestination()).symbolToString());
//        }

        // testing castle
//        if (move.getIsCastle()) {
//            ArrayList<Integer> coordOfRook =  new ArrayList<>();
//            coordOfRook.add(move.getRookAdded().getRow());
//            coordOfRook.add(move.getRookAdded().getCol());
//            System.out.println(bs.get(move.getDestination()).symbolToString());
//            System.out.println(bs.get(coordOfRook).symbolToString());
//        }

        // push move to API if its valid
        makeMoveDataAccessObject.pushMove(move);

        MakeMoveOutputData makeMoveOutputData = new MakeMoveOutputData(move);
        makeMovePresenter.prepareSuccessView(makeMoveOutputData);




    }
}





