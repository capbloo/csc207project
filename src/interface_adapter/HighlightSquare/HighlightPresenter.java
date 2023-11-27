package interface_adapter.HighlightSquare;

import use_case.HighlightSquare.HighlightOutputBoundary;
import use_case.HighlightSquare.HighlightOutputData;
import entity.ChessButton;

import java.util.ArrayList;
import java.util.HashMap;

public class HighlightPresenter implements HighlightOutputBoundary {
    private final HighlightViewModel highlightViewModel;

    public HighlightPresenter(HighlightViewModel highlightViewModel) {
        this.highlightViewModel = highlightViewModel;
    }

    @Override
    public void prepareSuccessView(HighlightOutputData response) {
        HashMap<ArrayList<Integer>, ChessButton> buttonList = response.getButtonList();
        ArrayList<ArrayList<Integer>> coordinates = response.getValidMoves();

        for (ArrayList<Integer> coordinate : coordinates) {
            buttonList.get(coordinate).setHighlight();
        }
        //highlightViewModel.firePropertyChanged();
    }
}
