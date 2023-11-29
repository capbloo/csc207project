package interface_adapter.CheckGameEnds;

import use_case.CheckGameEnds.CheckGameEndsOutputBoundary;
import use_case.CheckGameEnds.CheckGameEndsOutputData;

public class CheckGameEndsPresenter implements CheckGameEndsOutputBoundary {
    private final CheckGameEndsViewModel checkGameEndsViewModel;
    public  CheckGameEndsPresenter(CheckGameEndsViewModel checkGameEndsViewModel){
        this.checkGameEndsViewModel = checkGameEndsViewModel;
    }


    @Override
    public void prepareSuccessView(CheckGameEndsOutputData checkGameEndsOutputData) {
        CheckGameEndsState checkGameEndsState = checkGameEndsViewModel.getState();
        checkGameEndsState.set(checkGameEndsOutputData.getiswhite(),
                checkGameEndsOutputData.getiswin(),
                checkGameEndsOutputData.getisstale());
        this.checkGameEndsViewModel.setState(checkGameEndsState);

        checkGameEndsViewModel.firePropertyChanged();
    }
}
