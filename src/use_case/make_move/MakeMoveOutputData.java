package use_case.make_move;

import entity.ChessButton;
import entity.Move;

public class MakeMoveOutputData {
    final Move move;
    final ChessButton clickedButton;

    public MakeMoveOutputData(Move move, ChessButton clickedButton) {
        this.move = move;
        this.clickedButton = clickedButton;
    }

    public ChessButton getClickedButton() {
        return clickedButton;
    }

    public Move getMove() {return this.move;}
}

