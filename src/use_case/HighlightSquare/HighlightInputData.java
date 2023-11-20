package use_case.HighlightSquare;

import java.util.ArrayList;

public class HighlightInputData {
    final ArrayList<Integer> coordinates;

    public HighlightInputData(ArrayList<Integer> coordinates) {
        this.coordinates = coordinates;
    }

    public ArrayList<Integer> getposition() {
        return this.coordinates;
    }

}
