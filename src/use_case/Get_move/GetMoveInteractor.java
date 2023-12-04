//package use_case.Get_move;
//
//import entity.Board;
//import entity.Move;
//import entity.Piece;
//import entity.PieceBuilder;
//
//import java.util.ArrayList;
//
//import static java.lang.Math.abs;
//
//public class GetMoveInteractor implements GetMoveInputBoundary{
//
//    final GetMoveOutputBoundary getMoveOutputBoundary;
//    final GetMoveDataAccessInterface getMoveDataAccessInterface;
//    final Board board;
//
//    public GetMoveInteractor(GetMoveOutputBoundary getMoveOutputBoundary, GetMoveDataAccessInterface getMoveDataAccessInterface, Board board) {
//        this.getMoveOutputBoundary = getMoveOutputBoundary;
//        this.getMoveDataAccessInterface = getMoveDataAccessInterface;
//        this.board = board;
//    }
//
//    @Override
//    public void execute(GetMoveInputData getMoveInputData) {
//        // Get move from API
//        String apiMove = getMoveDataAccessInterface.getMove();
//
//        // translate api response - Origin
//        ArrayList<Integer> origin = new ArrayList<Integer>();
//        int originFirstCoordinate = apiMove.charAt(0) - 'a' + 1;
//        origin.add(originFirstCoordinate);
//        int originSecondCoordinate = Integer.parseInt(apiMove.substring(1, 2));
//        origin.add(originSecondCoordinate);
//
//        // get moving piece
//        Piece movingPiece = board.getBoardstate().get(origin);
//
//        // translate api response - Destination
//        ArrayList<Integer> destination = new ArrayList<Integer>();
//        int destinationFirstCoordinate = apiMove.charAt(3) - 'a' + 1;
//        destination.add(destinationFirstCoordinate);
//        int destinationSecondCoordinate = Integer.parseInt(apiMove.substring(3, 4));
//        destination.add(destinationSecondCoordinate);
//
//        // make move
//        Move move = new Move(movingPiece, origin, destination);
//
//        // check if is promotion
//        if (apiMove.length() == 5) {
//            move.setIsPromotion();
//
//            // check promotion detail
//            String piecePromotedTo = "";
//            char promotion = apiMove.charAt(4);
//            if (promotion == 'q'){
//                piecePromotedTo = "Queen";
//            } else if (promotion == 'r') {
//                piecePromotedTo = "Rook";
//            } else if (promotion == 'k') {
//                piecePromotedTo = "Knight";
//            } else if (promotion == 'b') {
//                piecePromotedTo = "Bishop";
//            }
//
//            // finalizing promotion
//            Piece piece = new PieceBuilder().create(piecePromotedTo, movingPiece.getColor());
//            move.setPiecePromotedTo(piece);
//        }
//
//        // check if is EnPassant
//        if (movingPiece.symbolToString().equals("Pawn")){
//            Piece lastMovingPiece = board.getLastmove().getPieceMoving();
//            // check if moving piece is white
//            if (movingPiece.getColor().equals("white") && originSecondCoordinate == 5){
//                if (lastMovingPiece.symbolToString().equals("Pawn") && lastMovingPiece.getColor().equals("black")){
//                    if (board.getLastmove().getOrigin().get(1).equals(7) && board.getLastmove().getDestination().get(1).equals(5)){
//                        if (Math.abs(board.getLastmove().getDestination().get(0) - originFirstCoordinate) == 1){
//                            if ((board.getLastmove().getDestination().get(0) - originFirstCoordinate) == 1){
//                                if (destinationFirstCoordinate == originFirstCoordinate + 1 && destinationSecondCoordinate == originSecondCoordinate + 1){
//                                    board.getBoardstate().remove(board.getLastmove().getDestination());
//                                }
//                            } else if ((board.getLastmove().getDestination().get(0) - originFirstCoordinate) == -1) {
//                                if (destinationFirstCoordinate == originFirstCoordinate - 1 && destinationSecondCoordinate == originSecondCoordinate + 1){
//                                    board.getBoardstate().remove(board.getLastmove().getDestination());
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//            // check if moving piece is black
//            if (movingPiece.getColor().equals("black") && originSecondCoordinate == 4){
//                if (lastMovingPiece.symbolToString().equals("Pawn") && lastMovingPiece.getColor().equals("white")){
//                    if (board.getLastmove().getOrigin().get(1).equals(2) && board.getLastmove().getDestination().get(1).equals(4)){
//                        if (Math.abs(board.getLastmove().getDestination().get(0) - originFirstCoordinate) == 1){
//                            if ((board.getLastmove().getDestination().get(0) - originFirstCoordinate) == 1){
//                                if (destinationFirstCoordinate == originFirstCoordinate + 1 && destinationSecondCoordinate == originSecondCoordinate - 1){
//                                    board.getBoardstate().remove(board.getLastmove().getDestination());
//                                }
//                            } else if ((board.getLastmove().getDestination().get(0) - originFirstCoordinate) == -1) {
//                                if (destinationFirstCoordinate == originFirstCoordinate - 1 && destinationSecondCoordinate == originSecondCoordinate - 1){
//                                    board.getBoardstate().remove(board.getLastmove().getDestination());
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//
//        }
//        // check if is Castle
//        if (movingPiece.)
//        // check if an opponent's piece is captured
//
//    }
//}

package use_case.Get_move;

import data_access.GetMoveDataAccessObject;
import entity.*;
import interface_adapter.Get_move.GetMovePresenter;

import java.util.ArrayList;

public class GetMoveInteractor implements GetMoveInputBoundary{
    final GetMoveDataAccessInterface getMoveDataAccessObeject;
    final GetMoveOutputBoundary getMovePresenter;
    final Board board;

    public GetMoveInteractor(GetMoveOutputBoundary getMoveOutputBoundary, GetMoveDataAccessInterface getMoveDataAccessObeject, Board board) {
        this.getMoveDataAccessObeject = getMoveDataAccessObeject;
        this.getMovePresenter = getMoveOutputBoundary;
        this.board = board;
    }

    @Override
    public void execute() {
        // Get move from API
        String apiMove = getMoveDataAccessObeject.getMoveAfter(board.getMovenumber());

        // translate api response - Origin
        ArrayList<Integer> origin = new ArrayList<>();
        Integer originFirstCoordinate = apiMove.charAt(0) - 'a' + 1;
        origin.add(originFirstCoordinate);
        Integer originSecondCoordinate = Integer.parseInt(apiMove.substring(1, 2));
        origin.add(originSecondCoordinate);
        // get moving piece
        Piece movingPiece = board.getBoardstate().get(origin);

        // translate api response - Destination
        ArrayList<Integer> destination = new ArrayList<>();
        Integer destinationFirstCoordinate = apiMove.charAt(2) - 'a' + 1;
        destination.add(destinationFirstCoordinate);
        Integer destinationSecondCoordinate = Integer.parseInt(apiMove.substring(3, 4));
        destination.add(destinationSecondCoordinate);

        // initialize a move
        Move move = new Move(movingPiece, origin, destination);

        Move[] moves = movingPiece.getValidMoves(origin, board.getBoardstate(), board.getLastmove());
        for (Move m : moves){
            if (m.getDestination().equals(destination)){
                move = m;
            }
        }
        // check if is castle
        if (move.getIsCastle()){
            Piece movingKing = movingPiece;
            ArrayList<Integer> rookToAdd = new ArrayList<>();
            ArrayList <Integer> rookToRemove = new ArrayList<>();
            if (movingKing.getColor().equals("white")){
                if (destinationFirstCoordinate == 7){
                    rookToAdd.add(6);
                    rookToAdd.add(1);
                    rookToRemove.add(8);
                    rookToRemove.add(1);
                } else {
                    rookToAdd.add(4);
                    rookToAdd.add(1);
                    rookToRemove.add(1);
                    rookToRemove.add(1);
                }
            } else {
                if (destinationFirstCoordinate == 7){
                    rookToAdd.add(6);
                    rookToAdd.add(8);
                    rookToRemove.add(8);
                    rookToRemove.add(8);
                } else {
                    rookToAdd.add(4);
                    rookToAdd.add(8);
                    rookToRemove.add(1);
                    rookToRemove.add(8);
                }
            }
            move.setRookAdded(rookToAdd);
            move.setRookRemoved(rookToRemove);
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
            } else if (promotion == 'n') {
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