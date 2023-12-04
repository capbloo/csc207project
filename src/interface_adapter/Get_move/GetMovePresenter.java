package interface_adapter.Get_move;
import entity.Move;
import entity.Piece;
import use_case.Get_move.GetMoveOutputBoundary;
import use_case.Get_move.GetMoveOutputData;

import java.awt.*;

public class GetMovePresenter implements GetMoveOutputBoundary {
    private final GetMoveViewModel getMoveViewModel;

    public GetMovePresenter(GetMoveViewModel getMoveViewModel) {this.getMoveViewModel = getMoveViewModel;}

    public void prepareSuccessView(GetMoveOutputData getMoveOutputData) {
        GetMoveState state = getMoveViewModel.getState();
        state.setMove(getMoveOutputData.getMove());
        getMoveViewModel.firePropertyChanged();
    }

}

