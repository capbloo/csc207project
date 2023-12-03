package interface_adapter.Get_move;

import entity.Board;
import use_case.Get_move.GetMoveInputBoundary;
import use_case.Get_move.GetMoveInputData;

public class GetMoveController {

    final GetMoveInputBoundary getMoveInteractor;

    public GetMoveController(GetMoveInputBoundary getMoveInteractor) {
        this.getMoveInteractor = getMoveInteractor;
    }

    public void execute(){
        GetMoveInputData getMoveInputData = new GetMoveInputData();
        getMoveInteractor.execute(getMoveInputData);
    }
}
