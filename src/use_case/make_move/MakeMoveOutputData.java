package use_case.make_move;

import entity.Move;

public class MakeMoveOutputData {
    final Move move;

    public MakeMoveOutputData(Move move) {this.move = move;}

    public Move getMove() {return this.move;}
}

