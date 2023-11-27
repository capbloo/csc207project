package use_case.HighlightSquare;

import entity.ChessButton;

import java.util.ArrayList;
import java.util.HashMap;

public class HighlightInputData {
    final ChessButton clickedButton;
    final HashMap<ArrayList<Integer>, ChessButton> buttonList;

    public HighlightInputData(ChessButton clickedButton, HashMap<ArrayList<Integer>, ChessButton> buttonList) {
        this.buttonList = buttonList;
        this.clickedButton = clickedButton;
    }

    public ChessButton getClickedButton() {
        return this.clickedButton;
    }

    public HashMap<ArrayList<Integer>, ChessButton> getButtonList() {
        return this.buttonList;
    }
}
