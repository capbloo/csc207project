package use_case.make_move;
import entity.ChessButton;
import entity.Move;

public class MakeMoveInputData {
    final Move move;
    final ChessButton clickedButton;

    public MakeMoveInputData(Move move, ChessButton clickedButton) {
        this.move = move;
        this.clickedButton = clickedButton;
    }

    public Move getMove() {return this.move;}

    public ChessButton getClickedButton() {
        return clickedButton;
    }
}
