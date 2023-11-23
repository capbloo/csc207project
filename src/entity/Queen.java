package entity;

public class Queen extends RayPiece {
    public Queen(String color) {
        super(color, new int[]{-1, 1, 0, 0, -1, -1, 1, 1}, new int[]{0, 0, -1, 1, -1, 1, -1, 1});
    }

    @Override
    public String toString() {
        if (color.equals("white")) {
            return "♕";
        }
        return "♛";
    }

    public String symbolToString() {
        return "Queen";
    }
}
