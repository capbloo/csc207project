package use_case;

import entity.Move;
import use_case.make_move.MakeMoveDataAccessInterface;

public class TestingMakeMoveDAO implements MakeMoveDataAccessInterface {
    Move receivedmove = null;
    public TestingMakeMoveDAO(){
    }

    @Override
    public void pushMove(Move move) {
        this.receivedmove = move;
    }

    public Move getReceivedmove(){return this.receivedmove;}
}
