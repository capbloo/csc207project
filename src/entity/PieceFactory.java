package entity;

public class PieceFactory {
    public PieceFactory(){}

    public Piece create(String type, String  color){
        return switch (type) {
            case "King" -> new King(color);
            case "Knight" -> new Knight(color);
            case "Pawn" -> new Pawn(color);
            case "Queen" -> new Queen(color);
            case "Bishop" -> new Bishop(color);
            default -> new Rook(color);
        };
    }

}
