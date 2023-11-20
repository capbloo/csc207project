package entity;

public class Rook extends RayPiece {
    public Rook(String color) {
        super(color, new int[]{-1, 1, 0, 0}, new int[]{0, 0, -1, 1});
    }

    @Override
    public String toString() {
        if (color.equals("white")) {
            return "♖";
        }
        return "♜";
    }
}
