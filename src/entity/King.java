package entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class King extends Piece {
    public King(String color) {
        super(color);
    }

    @Override
    public String toString() {
        if (color.equals("white")) {
            return "♔";
        }
        return "♚";
    }

    public String symbolToString() {
        return "King";
    }

    @Override
    public Move[] getValidMoves(ArrayList<Integer> position, HashMap<ArrayList<Integer>, Piece> boardState, Move lastMove) {
        Set<Move> validMoves = new HashSet<>();

        int[] xDisplacements = {-1, -1, -1, 0, 1, 1, 1, 0};
        int[] yDisplacements = {-1, 0, 1, 1, 1, 0, -1, -1};

        for (int i = 0; i < xDisplacements.length; i++) {
            int x = position.get(0) + xDisplacements[i];
            int y = position.get(1) + yDisplacements[i];

            ArrayList<Integer> target = coordinateBuilder(x, y);

            String square = checkSquare(x, y, boardState);

            if (square.equals("empty")) {
                Move potentialMove = makeMove(position, target);

                // check that king is not in check
                if (!wouldBeCheck(position, boardState, target)) {
                    validMoves.add(potentialMove);
                }

            } else if (square.equals("enemy")) {
                Move potentialMove = makeCapture(position, target);

                // check that king is not in check
                if (!wouldBeCheck(position, boardState, target)) {
                    validMoves.add(potentialMove);
                }
            }
        }

        // castling special case
        if (!hasMoved && !isInCheck(position, boardState)) { // king has not moved yet and is not in check
            int y = position.get(1);

            Piece potentialRightRook = boardState.get(coordinateBuilder(1, y));
            Piece potentialLeftRook = boardState.get(coordinateBuilder(8, y));

            if (potentialRightRook instanceof Rook && !potentialRightRook.hasMoved // rook at a1/a8 that has not moved
                    && boardState.get(coordinateBuilder(6, y)) == null // no pieces in between king and rook
                    && boardState.get(coordinateBuilder(7, y)) == null) {

                if (!wouldBeCheck(position, boardState, coordinateBuilder(6, y)) // does not castle through or into check
                        && !wouldBeCheck(position, boardState, coordinateBuilder(7, y))) {

                    Move castleRight = new Move(this, position, coordinateBuilder(7, y));
                    castleRight.setIsCastle();

                    validMoves.add(castleRight);
                }
            }

            if (potentialLeftRook instanceof Rook && !potentialLeftRook.hasMoved // rook at h1/h8 that has not moved
                    && boardState.get(coordinateBuilder(2, y)) == null // no pieces in between
                    && boardState.get(coordinateBuilder(3, y)) == null
                    && boardState.get(coordinateBuilder(4, y)) == null) {

                if (!wouldBeCheck(position, boardState, coordinateBuilder(3, y)) // does not castle through or into check
                        && !wouldBeCheck(position, boardState, coordinateBuilder(4, y))) {

                    Move castleLeft = new Move(this, position, coordinateBuilder(3, y));
                    castleLeft.setIsCastle();

                    validMoves.add(castleLeft);
                }
            }
        }
        return validMoves.toArray(new Move[0]);
    }

    private boolean wouldBeCheck(ArrayList<Integer> position, HashMap<ArrayList<Integer>, Piece> boardState,
                                 ArrayList<Integer> newPos) {
        // check if a hypothetical move by the king would result in check

        HashMap<ArrayList<Integer>, Piece> tempBoardState = new HashMap<>(boardState);

        tempBoardState.put(newPos, this);
        tempBoardState.put(position, null);

        return isInCheck(newPos, tempBoardState);
    }

    protected boolean checkHelper(ArrayList<Integer> kingPosition, HashMap<ArrayList<Integer>, Piece> boardState, Move move) {
        // make a hypothetical move, and then see if that move would result in a check

        HashMap<ArrayList<Integer>, Piece> tempBoardState = new HashMap<>(boardState);

        tempBoardState.put(move.getOrigin(), null);
        tempBoardState.put(move.getDestination(), move.getPieceMoving());

        if (move.getIsEnPassant()) { // add in the extra capture from en passant
            int targetX = move.getDestination().get(0);
            int targetY = move.getOrigin().get(1);
            tempBoardState.put(coordinateBuilder(targetX, targetY), null);
        }

        return isInCheck(kingPosition, tempBoardState);
    }

    public boolean isInCheck(ArrayList<Integer> position, HashMap<ArrayList<Integer>, Piece> boardState) {
        // check knight movement pattens around the king for enemy knights, then check rays for B/Q/R
        // then check pawn attackers

        int[] knightXDisplacements = {-2, -2, -1, -1, 1, 1, 2, 2};
        int[] knightYDisplacements = {-1, 1, -2, 2, -2, 2, -1, 1};
        int[] rookXDirections = {-1, 1, 0, 0};
        int[] rookYDirections = {0, 0, 1, -1};
        int[] bishopXDirections = {-1, 1, -1, 1};
        int[] bishopYDirections = {-1, -1, 1, 1};

        // check all possible knight moves
        for (int i = 0; i < knightXDisplacements.length; i++) {
            int targetX = position.get(0) + knightXDisplacements[i];
            int targetY = position.get(1) + knightYDisplacements[i];

            String targetSquareType = checkSquare(targetX, targetY, boardState);

            if (targetSquareType.equals("enemy") // enemy knight able to attack king
                    && boardState.get(coordinateBuilder(targetX, targetY)) instanceof Knight) {
                return true;
            }
        }

        // check bishop movement patterns (for bishops and queens)
        for (int i = 0; i < bishopXDirections.length; i++) {
            boolean continueRay = true;

            int x = position.get(0);
            int y = position.get(1);

            while (continueRay) {
                x += bishopXDirections[i];
                y += bishopYDirections[i];

                String squareType = checkSquare(x, y, boardState);

                switch(squareType) {
                    case "stop" -> continueRay = false;
                    case "enemy" -> { // if an enemy bishop or queen is the first piece on a diagonal, king in check
                        Piece enemyPiece = boardState.get(coordinateBuilder(x, y));
                        if (enemyPiece instanceof Bishop || enemyPiece instanceof Queen) {
                            return true;
                        }
                    }
                }
            }
        }

        // check rook movement patterns (for bishops and queens)
        for (int i = 0; i < rookXDirections.length; i++) {
            boolean continueRay = true;

            int x = position.get(0);
            int y = position.get(1);

            while (continueRay) {
                x += rookXDirections[i];
                y += rookYDirections[i];

                String squareType = checkSquare(x, y, boardState);

                switch(squareType) {
                    case "stop" -> continueRay = false;
                    case "enemy" -> { // if an enemy rook or queen is the first piece on a row/column, king in check
                        Piece enemyPiece = boardState.get(coordinateBuilder(x, y));
                        if (enemyPiece instanceof Rook || enemyPiece instanceof Queen) {
                            return true;
                        }
                    }
                }
            }
        }

        // finally check for pawns that might attack
        // set move direction as appropriate by color, throw exception if color isn't properly defined
        int moveDirection;
        if (color.equals("white")) {
            moveDirection = 1;
        } else if (color.equals("black")) {
            moveDirection = -1;
        } else {
            throw new RuntimeException("King at (" + position.get(0) + ',' + position.get(1) + ") wasn't black or white.");
        }

        ArrayList<Integer> leftPawnPos = coordinateBuilder(position.get(0) - 1, position.get(1) + moveDirection);
        ArrayList<Integer> rightPawnPos = coordinateBuilder(position.get(0) + 1, position.get(1) + moveDirection);

        // check that the squares in question are valid squares, if they contain enemies, and then, if those enemies are pawns
        // if all are true, king is in check, otherwise, no pawn is checking the king
        if (checkSquare(leftPawnPos.get(0), leftPawnPos.get(1), boardState).equals("enemy")
                && boardState.get(leftPawnPos) instanceof Pawn) {
            return true;
        }
        if (checkSquare(rightPawnPos.get(0), rightPawnPos.get(1), boardState).equals("enemy")
                && boardState.get(rightPawnPos) instanceof Pawn) {
            return true;
        }

        // not under attack by knights, bishops, rooks, queens, or pawns, so not in check at all
        return false;
    }

    private Move makeCapture(ArrayList<Integer> position, ArrayList<Integer> destination) {
        Move capture = new Move(this, position, destination);
        capture.setPieceCaptured();

        return capture;
    }

    private Move makeMove(ArrayList<Integer> position, ArrayList<Integer> destination) {
        Move move = new Move(this, position, destination);

        return move;
    }

    private ArrayList<Integer> coordinateBuilder(int x, int y) {
        ArrayList<Integer> coordinate = new ArrayList<>(2);
        coordinate.add(x);
        coordinate.add(y);

        return coordinate;
    }
}
