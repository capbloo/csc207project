package interface_adapter.HighlightSquare;

import entity.Piece;

import java.util.ArrayList;
import java.util.HashMap;

public class HighlightState {
    private HashMap<ArrayList<Integer>, Piece> boardState;
    public HighlightState() {}
    public HashMap<ArrayList<Integer>, Piece> getBoardstate() {
        return boardState;
    }
}
