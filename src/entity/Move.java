package entity;

import java.util.ArrayList;

public class Move {
    private boolean isEnPassant;
    private boolean isCastle;
    private boolean isPromotion;
    private Piece piecePromotedTo;
    private boolean pieceCaptured;
    private ArrayList<Integer> pieceCaptureLocation;
    private Piece pieceMoving;
    private ArrayList<Integer> origin;
    private ArrayList<Integer> destination;

    public Move(Piece pieceMoving, ArrayList<Integer> origin, ArrayList<Integer> destination) {
        this.pieceMoving = pieceMoving;
        this.origin = origin;
        this.destination = destination;

    }

    // getters and setters
    public void setIsEnPassant() {
        isEnPassant = true;
    }

    public void setIsCastle() {
        isCastle = true;
    }

    public void setIsPromotion() {
        isPromotion = true;
    }

    public void setPiecePromotedTo(Piece promotion) {
        piecePromotedTo = promotion;
    }

    public void setPieceCaptured() {
        pieceCaptured = true;
    }

    public void setPieceCaptureLocation(ArrayList<Integer> captureLocation) {
        pieceCaptureLocation = captureLocation;
    }

    public boolean getIsEnPassant() {
        return isEnPassant;
    }

    public boolean getIsCastle() {
        return isCastle;
    }

    public boolean getIsPromotion() {
        return isPromotion;
    }

    public Piece getPiecePromotedTo() {
        return piecePromotedTo;
    }

    public boolean getIsPieceCaptured() {
        return pieceCaptured;
    }

    public ArrayList<Integer> getPieceCaptureLocation() {
        return pieceCaptureLocation;
    }

    public Piece getPieceMoving() {
        return pieceMoving;
    }

    public ArrayList<Integer> getOrigin() {
        return origin;
    }

    public ArrayList<Integer> getDestination() {
        return destination;
    }

    @Override
    public String toString() {
        return algebraicNotation();
    }

    public String algebraicNotation() {
        String startingSquare = (char)(96 + origin.get(0)) + origin.get(1).toString();
        String endingSquare = (char)(96 + destination.get(0)) + destination.get(1).toString();

        String move = startingSquare + endingSquare;

        if (isPromotion) {
            return move + "q";
        }

        return move;
    }
}