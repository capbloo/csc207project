package interface_adapter.Get_move;

import entity.Move;
import entity.Piece;
import interface_adapter.make_move.MakeMoveState;

import java.util.ArrayList;
import java.util.HashMap;

public class GetMoveState {
    private HashMap<ArrayList<Integer>, Piece> boardState;

    private Move move;

    public GetMoveState(){}
    public void setMove(Move move) {this.move = move;}
    public Move getMove() {return move;}
    public HashMap<ArrayList<Integer>, Piece> getBoardstate() {
        return boardState;
    }
}
