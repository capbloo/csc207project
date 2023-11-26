package interface_adapter.make_move;
import entity.Move;
import use_case.make_move.MakeMoveInputBoundary;
import use_case.make_move.MakeMoveInputData;

public class MakeMoveController {
    final MakeMoveInputBoundary makeMoveInteractor;


    public MakeMoveController(MakeMoveInputBoundary makeMoveInteractor) {
        this.makeMoveInteractor = makeMoveInteractor;
    }

    public void execute(Move move) {
        MakeMoveInputData makeMoveInputData = new MakeMoveInputData(move);
        makeMoveInteractor.execute(makeMoveInputData);
    }


}
