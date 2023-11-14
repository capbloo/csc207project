package data_access;

import entity.Move;
import use_case.make_move.MakeMoveDataAccessInterface;

import java.util.ArrayList;

public class MakeMoveDataAccessObject implements MakeMoveDataAccessInterface {
    private final String gameID;

    public MakeMoveDataAccessObject(String gameID) {
        this.gameID = gameID;
    }

    @Override
    public void pushMove(Move move) {
        ArrayList<Integer> origin = move.getOrigin();
        ArrayList<Integer> destination = move.getDestination();
        String moveString =
    }
}
