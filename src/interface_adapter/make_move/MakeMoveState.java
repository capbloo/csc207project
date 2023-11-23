package interface_adapter.make_move;

import entity.Board;
import entity.Piece;

import java.util.ArrayList;
import java.util.HashMap;

public class MakeMoveState {
    private HashMap<ArrayList<Integer>, Piece> boardState;
    public MakeMoveState(MakeMoveState copy) {
        this.boardState = copy.boardState;
    }
    public MakeMoveState() {};

    public HashMap<ArrayList<Integer>, Piece> getBoardstate() {
        return boardState;
    }

}
