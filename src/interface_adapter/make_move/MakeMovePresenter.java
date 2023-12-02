package interface_adapter.make_move;

import entity.ChessButton;
import entity.Move;
import entity.Piece;
import entity.PieceBuilder;
import use_case.make_move.MakeMoveOutputBoundary;
import use_case.make_move.MakeMoveOutputData;

import java.awt.*;

public class MakeMovePresenter implements MakeMoveOutputBoundary {
    private final MakeMoveViewModel makeMoveViewModel;

    public MakeMovePresenter(MakeMoveViewModel makeMoveViewModel) {
        this.makeMoveViewModel = makeMoveViewModel;
    }

    public void prepareSuccessView(MakeMoveOutputData makeMoveOutputData) {
        MakeMoveState state = makeMoveViewModel.getState();

        ChessButton clickedButton = makeMoveOutputData.getClickedButton();
        state.setClickedButton(clickedButton);
        state.setMove(makeMoveOutputData.getMove());

        makeMoveViewModel.firePropertyChanged();
    }

}
