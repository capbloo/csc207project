package use_case.CheckGameEnds;

import entity.Board;
import entity.Move;
import entity.Piece;

import javax.swing.text.StyledEditorKit;

public class CheckGameEndsInteractor implements CheckGameEndsInputBoundary{

    final CheckGameEndsOutputBoundary checkGameEndsOutputBoundary;

    final Board board;

    public CheckGameEndsInteractor(CheckGameEndsOutputBoundary checkGameEndsOutputBoundary1, Board board1){
        this.checkGameEndsOutputBoundary = checkGameEndsOutputBoundary1;
        this.board = board1;
    }
    @Override
    public void execute(CheckGameEndsInputData checkGameEndsInputData) {
        Move move = checkGameEndsInputData.getmove();
        Piece piece = move.getPieceMoving();
        String color = piece.getColor();
        String color2 = "";
        if (color.equals("white")){
            color2 = "black";
        }else {
            color2 = "white";
        }
        boolean win = board.isCheckMate(color2);
        boolean iswhite = color.equals("white");
        boolean stale = board.isStaleMate(color2);

        CheckGameEndsOutputData checkGameEndsOutputData= new CheckGameEndsOutputData(iswhite, win, stale);
        checkGameEndsOutputBoundary.prepareSuccessView(checkGameEndsOutputData);
    }
}
