package interface_adapter.Get_move;

import entity.Board;
import use_case.Get_move.GetMoveInputBoundary;

public class GetMoveController {

    final GetMoveInputBoundary getMoveInteractor;

    public GetMoveController(GetMoveInputBoundary getMoveInteractor) {
        this.getMoveInteractor = getMoveInteractor;
    }

    public void execute(){
        getMoveInteractor.execute();
    }
}
