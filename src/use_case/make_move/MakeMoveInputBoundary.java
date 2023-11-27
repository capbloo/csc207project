package use_case.make_move;

import entity.ChessButton;
import entity.Move;

public interface MakeMoveInputBoundary {
    void execute(MakeMoveInputData makeMoveInputData);
}
