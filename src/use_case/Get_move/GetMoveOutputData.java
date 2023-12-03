package use_case.Get_move;

import entity.Move;

public class GetMoveOutputData {
    final Move move;

    public GetMoveOutputData(Move move) {
        this.move = move;
    }

    public Move getMove() {return this.move;}
}
