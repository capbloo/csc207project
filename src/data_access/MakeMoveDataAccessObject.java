package data_access;

import entity.Move;
import use_case.make_move.MakeMoveDataAccessInterface;

import java.util.ArrayList;
import java.util.HashMap;

public class MakeMoveDataAccessObject implements MakeMoveDataAccessInterface {
    private final String gameID;
    private final String gameURL;

    public MakeMoveDataAccessObject(String gameID, String gameURL) {

        this.gameID = gameID;
        this.gameURL = gameURL;
    }

    private static String coordsToString(ArrayList<Integer> origin, ArrayList<Integer> destination) {
        HashMap<Integer, Character> letterMap = new HashMap<>();
        letterMap.put(1, 'a');
        letterMap.put(2, 'b');
        letterMap.put(3, 'c');
        letterMap.put(4, 'd');
        letterMap.put(5, 'e');
        letterMap.put(6, 'f');
        letterMap.put(7, 'g');
        letterMap.put(8, 'h');

        String letter1 = letterMap.get(origin.get(0)).toString();
        String letter2 =  letterMap.get(destination.get(0)).toString();

        String coordString = letter1 + origin.get(1).toString() + letter2 + destination.get(1).toString();
        return coordString;

    }

    public void pushMove(Move move) {
        ArrayList<Integer> origin = move.getOrigin();
        ArrayList<Integer> destination = move.getDestination();
        String moveString = coordsToString(origin, destination);
        // this isn't actually the right object, but api push should look something like this according to LiChess documentation
        gameURL.makeMove(moveString, this.gameID);

    }
}
