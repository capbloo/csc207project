package interface_adapter.CheckGameEnds;

import entity.Move;
import use_case.CheckGameEnds.CheckGameEndsInputBoundary;
import use_case.CheckGameEnds.CheckGameEndsInputData;

public class CheckGameEndsController {
    final CheckGameEndsInputBoundary checkGameEndsInteractor;

    public CheckGameEndsController(CheckGameEndsInputBoundary checkGameEndsInteractor){
        this.checkGameEndsInteractor = checkGameEndsInteractor;
    }

    public void execute(Move move){
        CheckGameEndsInputData checkGameEndsInputData = new CheckGameEndsInputData(move);
        checkGameEndsInteractor.execute(checkGameEndsInputData);
    }
}
