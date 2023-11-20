package entity;

public class Bishop extends RayPiece {
    public Bishop(String color) {
        super(color, new int[]{-1, -1, 1, 1}, new int[]{-1, 1, -1, 1});
    }

    @Override
    public String toString() {
        if (color.equals("white")) {
            return "♗";
        }
        return "♝";
    }
}
