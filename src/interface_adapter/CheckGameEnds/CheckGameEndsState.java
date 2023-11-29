package interface_adapter.CheckGameEnds;

import entity.Piece;

import java.util.ArrayList;
import java.util.HashMap;

public class CheckGameEndsState {
    private boolean iswhite;

    private boolean win;

    private boolean stale;

    public CheckGameEndsState(CheckGameEndsState copy) {
        this.stale = copy.getstale();
        this.win = copy.getwin();
        this.iswhite = copy.getiswhite();
    }
    public CheckGameEndsState() {};

    public void set(boolean iswhite, boolean win, boolean stale){
        this.iswhite = iswhite;
        this.win = win;
        this.stale = stale;
    }

    public boolean getiswhite(){return iswhite;}

    public boolean getwin(){return win;}

    public boolean getstale(){return stale;}

}
