package use_case.Get_move;

import data_access.GetMoveDataAccessObject;
import entity.Board;
import entity.Move;
import entity.Piece;
import entity.PieceBuilder;
import interface_adapter.Get_move.GetMovePresenter;

import java.util.ArrayList;

public class GetMoveInteractor implements GetMoveInputBoundary{
    final GetMoveDataAccessInterface getMoveDataAccessInterface;
    final GetMoveOutputBoundary getMovePresenter;
    final Board board;

    public GetMoveInteractor(GetMoveOutputBoundary getMoveOutputBoundary, GetMoveDataAccessInterface getMoveDataAccessInterface, Board board) {
        this.getMoveDataAccessInterface = getMoveDataAccessInterface;
        this.getMovePresenter = getMoveOutputBoundary;
        this.board = board;
    }

    @Override
    public void execute(GetMoveInputData getMoveInputData) {
        // Get move from API
        String apiMove = getMoveDataAccessInterface.getMoveAfter(board.getMovenumber());

        // translate api response - Origin
        ArrayList<Integer> origin = new ArrayList<Integer>();
        int originFirstCoordinate = apiMove.charAt(0) - 'a' + 1;
        origin.add(originFirstCoordinate);
        int originSecondCoordinate = Integer.parseInt(apiMove.substring(1, 2));
        origin.add(originSecondCoordinate);

        // get moving piece
        Piece movingPiece = board.getBoardstate().get(origin);

        // translate api response - Destination
        ArrayList<Integer> destination = new ArrayList<Integer>();
        int destinationFirstCoordinate = apiMove.charAt(3) - 'a' + 1;
        destination.add(destinationFirstCoordinate);
        int destinationSecondCoordinate = Integer.parseInt(apiMove.substring(3, 4));
        destination.add(destinationSecondCoordinate);

        // initialize a move
        Move move = new Move(movingPiece, origin, destination);

        Move[] moves = movingPiece.getValidMoves(origin, board.getBoardstate(), board.getLastmove());
        for (Move m : moves){
            if (m.getDestination() == destination){
                move = m;
            }
        }

        // check if is promotion
        if (apiMove.length() == 5) {
            move.setIsPromotion();

            // check promotion detail
            String piecePromotedTo = "";
            char promotion = apiMove.charAt(4);
            if (promotion == 'q'){
                piecePromotedTo = "Queen";
            } else if (promotion == 'r') {
                piecePromotedTo = "Rook";
            } else if (promotion == 'k') {
                piecePromotedTo = "Knight";
            } else if (promotion == 'b') {
                piecePromotedTo = "Bishop";
            }

            // finalizing promotion
            Piece piece = new PieceBuilder().create(piecePromotedTo, movingPiece.getColor());
            move.setPiecePromotedTo(piece);
        }

        // make move
        board.makeMove(move);

        // wrap output data
        GetMoveOutputData getMoveOutputData = new GetMoveOutputData(move);
        getMovePresenter.prepareSuccessView(getMoveOutputData);

    }
}