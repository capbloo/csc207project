package make_move;

import entity.Board;
import entity.Piece;

import java.util.ArrayList;
import java.util.HashMap;

public class MakeMoveTest {
    public HashMap<ArrayList<Integer>, Piece> boardState() {
        Board newBoard = new Board();

        return newBoard.getBoardstate();
    }

}
