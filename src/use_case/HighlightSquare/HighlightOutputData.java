package use_case.HighlightSquare;

import entity.ChessButton;

import java.util.ArrayList;
import java.util.HashMap;

public class HighlightOutputData {

    ArrayList<ArrayList<Integer>> moveCoordinates;
    HashMap<ArrayList<Integer>, ChessButton> buttonList;

    public HighlightOutputData(ArrayList<ArrayList<Integer>> moveCoordinates, HashMap<ArrayList<Integer>, ChessButton>
            buttonList) {
        this.moveCoordinates = moveCoordinates;
        this.buttonList = buttonList;
    }

    public ArrayList<ArrayList<Integer>> getValidMoves() {
        return moveCoordinates;
    }

    public HashMap<ArrayList<Integer>, ChessButton> getButtonList() {
        return buttonList;
    }
}
