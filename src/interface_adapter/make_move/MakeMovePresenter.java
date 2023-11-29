package interface_adapter.make_move;

import entity.ChessButton;
import entity.Piece;
import use_case.make_move.MakeMoveOutputBoundary;
import use_case.make_move.MakeMoveOutputData;

import java.awt.*;

public class MakeMovePresenter implements MakeMoveOutputBoundary {
    private final MakeMoveViewModel makeMoveViewModel;

    public MakeMovePresenter(MakeMoveViewModel makeMoveViewModel) {
        this.makeMoveViewModel = makeMoveViewModel;
    }

    public void prepareSuccessView(MakeMoveOutputData makeMoveOutputData) {

        ChessButton clickedButton = makeMoveOutputData.getClickedButton();
        Piece piece = makeMoveOutputData.getMove().getPieceMoving();
        Font f = new Font("serif", Font.PLAIN, 60);
        String pieceSymbol = piece.toString();

        clickedButton.setText(pieceSymbol);
        clickedButton.setFont(f);
        clickedButton.setPieceColour(piece.getColor());
        clickedButton.setPiece(piece.symbolToString());

        makeMoveViewModel.firePropertyChanged();
    }

}
