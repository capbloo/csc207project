package interface_adapter.make_move;

import use_case.make_move.MakeMoveOutputBoundary;
import use_case.make_move.MakeMoveOutputData;

public class MakeMovePresenter implements MakeMoveOutputBoundary {
    private final MakeMoveViewModel makeMoveViewModel;

    public MakeMovePresenter(MakeMoveViewModel makeMoveViewModel) {
        this.makeMoveViewModel = makeMoveViewModel;
    }

    public void prepareView(MakeMoveOutputData makeMoveOutputData) {
        // Can't finish until view is made
    }
}
