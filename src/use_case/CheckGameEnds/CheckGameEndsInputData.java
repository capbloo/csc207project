package use_case.CheckGameEnds;

import entity.ChessButton;
import entity.Move;

public class CheckGameEndsInputData {
    final Move move;

    public CheckGameEndsInputData(Move move1){
        this.move = move1;
    }

    public Move getmove(){return move;}


}
