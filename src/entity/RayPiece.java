package entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

abstract class RayPiece extends Piece {
    protected int[] rayDirectionsX;
    protected int[] rayDirectionsY;
    protected RayPiece(String color, int[] rayDirectionsX, int[] rayDirectionsY) {
        super(color);
        this.rayDirectionsX = rayDirectionsX;
        this.rayDirectionsY = rayDirectionsY;
    }

    @Override
    public Move[] getValidMoves(ArrayList<Integer> position, HashMap<ArrayList<Integer>, Piece> boardState, Move lastMove) {
        Set<Move> possibleMoves = new HashSet<>();

        // method: draw "rays" in all legal movement directions and add possible moves

        for (int i = 0; i < rayDirectionsX.length; i++) {
            // draw a ray until hitting a piece or edge of board, repeat for all possible directions
            boolean continueRay = true;

            int x = position.get(0);
            int y = position.get(1);

            while(continueRay) {
                // increment each ray by the appropriate amount
                x += rayDirectionsX[i];
                y += rayDirectionsY[i];

                String square = checkSquare(x, y, boardState);

                switch (square) {
                    case "stop" ->  // edge of board or friendly piece, stop
                            continueRay = false;
                    case "enemy" -> {  // capture but don't continue
                        continueRay = false;
                        Move capture = makeCapture(position, coordinateBuilder(x, y));
                        possibleMoves.add(capture);
                    }
                    case "empty" -> {  // make the square a valid move and continue casting ray
                        Move move = makeMove(position, coordinateBuilder(x, y));
                        possibleMoves.add(move);
                    }
                }
            }
        }

        Set<Move> checkedMoves = new HashSet<>(possibleMoves.size());

        for (Move move : possibleMoves) {
            if (validMove(boardState, move)) {
                checkedMoves.add(move);
            }
        }

        return checkedMoves.toArray(new Move[0]);
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
