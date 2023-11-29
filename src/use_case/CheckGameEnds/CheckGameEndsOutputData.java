package use_case.CheckGameEnds;

public class CheckGameEndsOutputData {
    final boolean iswhite;

    final boolean iswin;

    final boolean isstale;

    public CheckGameEndsOutputData(boolean white, boolean win, boolean stale){
        this.iswhite = white;
        this.isstale = stale;
        this.iswin = win;
    }

    public boolean getiswhite(){return iswhite;}
    public boolean getiswin(){return iswin;}
    public boolean getisstale(){return isstale;}
}
