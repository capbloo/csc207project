package interface_adapter.make_move;

import entity.Board;
import entity.Piece;

import java.util.ArrayList;
import java.util.HashMap;

public class MakeMoveState {
    private Board board;
    public MakeMoveState(MakeMoveState copy) {
        this.board = copy.board;
    }
    public MakeMoveState() {};

    public HashMap<ArrayList<Integer>, Piece> getBoardstate() {
        return board.getBoardstate();
    }

}
