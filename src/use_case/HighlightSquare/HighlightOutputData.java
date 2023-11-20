package use_case.HighlightSquare;

import entity.Move;

public class HighlightOutputData {

    private Move[] validMoves = new Move[0];

    public HighlightOutputData(Move[] validMoves) {
        this.validMoves = validMoves;
    }
    public Move[] getValidMoves(){ return validMoves;}
}
