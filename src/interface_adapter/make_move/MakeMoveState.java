package interface_adapter.make_move;

import entity.Board;
import entity.ChessButton;
import entity.Piece;

import java.util.ArrayList;
import java.util.HashMap;

public class MakeMoveState {
    private HashMap<ArrayList<Integer>, Piece> boardState;
    private boolean moveError;
    public MakeMoveState(MakeMoveState copy) {
        this.boardState = copy.getBoardstate();
    }
    public MakeMoveState() {};

    public HashMap<ArrayList<Integer>, Piece> getBoardstate() {
        return boardState;
    }

    public boolean isMoveError() {
        return moveError;
    }

    public void setMoveError() {this.moveError = true;}
}
