package entity;

public class PieceBuilder {
    public PieceBuilder(){}

    public Piece create(String type, String  color){
        if (type == "King"){
            return new King(color);
        }
        else if (type == "Knight") {
            return  new Knight(color);
        }
        else if (type == "Pawn") {
            return  new Pawn(color);
        }
        else if (type == "Queen") {
            return  new Queen(color);
        }
        else if (type == "Bishop") {
            return  new Bishop(color);
        }

        else {
            return new Rook(color);
        }
    }

}
