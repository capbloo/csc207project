package use_case.make_move;

import entity.Board;
import entity.Move;

import java.util.ArrayList;

public class MakeMoveInteractor implements MakeMoveInputBoundary {
        final MakeMoveDataAccessInterface makeMoveDataAccessObject;
        final MakeMoveOutputBoundary makeMovePresenter;

        final Board board;

        public MakeMoveInteractor(MakeMoveDataAccessInterface makeMoveDataAccessObject, MakeMoveOutputBoundary makeMovePresenter, Board board) {
            this.makeMoveDataAccessObject = makeMoveDataAccessObject;
            this.makeMovePresenter = makeMovePresenter;
            this.board = board;
        }

        public void execute(MakeMoveInputData makeMoveInputData) {
            Move move = makeMoveInputData.getMove();
            board.makeMove(move);
            MakeMoveOutputData makeMoveOutputData = new MakeMoveOutputData(move);
            makeMovePresenter.prepareView(makeMoveOutputData);


        }
}
