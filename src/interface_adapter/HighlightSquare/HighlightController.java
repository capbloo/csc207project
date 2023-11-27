package interface_adapter.HighlightSquare;

import entity.ChessButton;
import use_case.HighlightSquare.HighlightInputBoundary;
import use_case.HighlightSquare.HighlightInputData;

import java.util.ArrayList;
import java.util.HashMap;

public class HighlightController {
    final HighlightInputBoundary highlightInteractor;

    public HighlightController(HighlightInputBoundary highlightInteractor) {
        this.highlightInteractor = highlightInteractor;
    }

    public void execute(ChessButton clickedButton, HashMap<ArrayList<Integer>, ChessButton> buttonList) {
        HighlightInputData highlightInputData = new HighlightInputData(clickedButton, buttonList);
        highlightInteractor.execute(highlightInputData);
    }
}
