package use_case.make_move;

public interface MakeMoveOutputBoundary {
    void prepareSuccessView(MakeMoveOutputData makeMoveOutputData);

    void prepareFailView();
}
