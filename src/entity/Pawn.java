package entity;

import java.util.*;

public class Pawn extends Piece {
    public Pawn(String color) {
        super(color);
    }

    @Override
    public String toString() {
        if (color.equals("white")) {
            return "♙";
        }
        return "♟";
    }

    @Override
    public Move[] getValidMoves(ArrayList<Integer> position, HashMap<ArrayList<Integer>, Piece> boardState, Move lastMove) {
        Set<Move> possibleMoves = new HashSet<>();

        int moveDirection;

        // set move direction as appropriate by color, throw exception if color isn't properly defined
        if (color.equals("white")) {
            moveDirection = 1;
        } else if (color.equals("black")) {
            moveDirection = -1;
        } else {
            throw new RuntimeException("Pawn at (" + position.get(0) + ',' + position.get(1) + ") wasn't black or white.");
        }

        // check conditions for en passant
        if ((lastMove.getPieceMoving() instanceof Pawn) // last move was made by a pawn
                && ((lastMove.getOrigin().get(1) - lastMove.getDestination().get(1)) == 2) // and it moved 2 squares
                && (lastMove.getDestination().get(1).equals(position.get(1))) // and it ended in this pawn's row
                && (Math.abs(lastMove.getDestination().get(0) - position.get(0)) == 1)) { // and it's right next to our pawn

            Move enPassant = makeEnPassant(position, lastMove, moveDirection);
            possibleMoves.add(enPassant);
        }

        // check if our pawn can move 1 square forward
        if (checkSquare(position.get(0), position.get(1) + moveDirection, boardState).equals("empty")) {
            Move pawnPush = makePawnPush(position, moveDirection);
            possibleMoves.add(pawnPush);

            // if 1 was possible and pawn hasn't moved yet, then check if moving 2 squares forward would be legal
            if (!hasMoved && checkSquare(position.get(0), position.get(1) + 2 * moveDirection, boardState).equals("empty")) {
                Move doublePawnPush = makeDoublePawnPush(position, moveDirection);
                possibleMoves.add(doublePawnPush);
            }
        }

        // finally, check the possible captures
        if (checkSquare(position.get(0) + 1, position.get(1) + moveDirection, boardState).equals("enemy")) {
            Move rightCapture = makeCapture(position, 1, moveDirection);
            possibleMoves.add(rightCapture);
        }

        if (checkSquare(position.get(0) - 1, position.get(1) + moveDirection, boardState).equals("enemy")) {
            Move leftCapture = makeCapture(position, -1, moveDirection);
            possibleMoves.add(leftCapture);
        }

        Set<Move> checkedMoves = new HashSet<>(possibleMoves.size());

        for (Move move : possibleMoves) {
            if (validMove(boardState, move)) {
                checkedMoves.add(move);
            }
        }

        return checkedMoves.toArray(new Move[0]); // turn our set into an array and return it
    }

    private Move makeCapture(ArrayList<Integer> position, int captureDirection, int moveDirection) {
        ArrayList<Integer> destination = coordinateBuilder(position.get(0) + captureDirection, position.get(1) + moveDirection);

        Move capture = new Move(this, position, destination);
        capture.setPieceCaptured();

        // if the move is a promotion, add relevant information
        if (destination.get(1) == 1 || destination.get(1) == 8) {
            capture.setIsPromotion();
        }
        return capture;
    }

    private Move makeDoublePawnPush(ArrayList<Integer> position, int moveDirection) {
        ArrayList<Integer> destination = coordinateBuilder(position.get(0), position.get(1) + 2 * moveDirection);

        Move doublePawnPush = new Move(this, position, destination);

        return doublePawnPush;
    }

    private Move makePawnPush(ArrayList<Integer> position, int moveDirection) {
        ArrayList<Integer> destination = coordinateBuilder(position.get(0), position.get(1) + moveDirection);

        Move pawnPush = new Move(this, position, destination);

        // if the move is a promotion, add relevant information
        if (destination.get(1) == 1 || destination.get(1) == 8) {
            pawnPush.setIsPromotion();
        }
        return pawnPush;
    }

    private Move makeEnPassant(ArrayList<Integer> position, Move lastMove, int moveDirection) {
        // en passant will land in the direction of captured piece, one square "behind" it (based on pawn color)
        ArrayList<Integer> destination = coordinateBuilder(lastMove.getDestination().get(0), lastMove.getDestination().get(1) + moveDirection);

        // create move and assign relevant information
        Move enPassant = new Move(this, position, destination);
        enPassant.setIsEnPassant();
        enPassant.setPieceCaptured();
        enPassant.setPieceCaptureLocation(lastMove.getDestination());
        return enPassant;
    }

    private ArrayList<Integer> coordinateBuilder(int x, int y) {
        ArrayList<Integer> coordinate = new ArrayList<>(2);
        coordinate.add(x);
        coordinate.add(y);

        return coordinate;
    }
}
