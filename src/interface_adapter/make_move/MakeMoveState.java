package interface_adapter.make_move;

import entity.Board;
import entity.ChessButton;
import entity.Move;
import entity.Piece;

import java.util.ArrayList;
import java.util.HashMap;

public class MakeMoveState {

    private HashMap<ArrayList<Integer>, Piece> boardState;

    private Move move;

    public MakeMoveState(MakeMoveState copy) {
        this.boardState = copy.getBoardstate();
    }

    public MakeMoveState() {
    }




    public void setMove(Move move) {
        this.move = move;
    }

    public Move getMove() {
        return move;
    }

    public HashMap<ArrayList<Integer>, Piece> getBoardstate() {
        return boardState;
    }
}


