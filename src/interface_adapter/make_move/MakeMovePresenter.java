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

        ChessButton clickedButton = makeMoveOutputData.getClickedButton();
        Piece piece = makeMoveOutputData.getMove().getPieceMoving();
        Move move = makeMoveOutputData.getMove();
        Font f = new Font("serif", Font.PLAIN, 60);
        String pieceSymbol = piece.toString();

        if (move.getIsPromotion()) {
            // if promotion, change the piece to a queen permanently
            PieceBuilder builder = new PieceBuilder();
            piece = builder.create("Queen", piece.getColor());
            if (piece.getColor().equals("white")) {
                clickedButton.setText("♕");
            }
            else {
            clickedButton.setText("♛");
            }

 }
        else if (move.getIsCastle()) {
            ChessButton rookRemoved = move.getRookRemoved();
            rookRemoved.clear();
            clickedButton.setText(pieceSymbol);

            ChessButton rookAdded = move.getRookAdded();
            if (piece.getColor().equals("white")) {
                rookAdded.setText("♖");
            }
            else {
                rookAdded.setText("♜");
            }
            rookAdded.setFont(f);
            rookAdded.setPieceColour(piece.getColor());
            rookAdded.setPiece("Rook");
        }
        else {
            clickedButton.setText(pieceSymbol);
        }
        clickedButton.setFont(f);
        clickedButton.setPieceColour(piece.getColor());
        clickedButton.setPiece(piece.symbolToString());

        makeMoveViewModel.firePropertyChanged();
    }

}
