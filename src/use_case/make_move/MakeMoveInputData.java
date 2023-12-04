package use_case.make_move;
import entity.ChessButton;
import entity.Move;

public class MakeMoveInputData {
    final Move move;

    public MakeMoveInputData(Move move) {
        this.move = move;
    }

    public Move getMove() {return this.move;}

}
