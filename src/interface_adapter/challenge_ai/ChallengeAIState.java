package interface_adapter.challenge_ai;

import interface_adapter.CheckGameEnds.CheckGameEndsState;

public class ChallengeAIState {
    private String gameID;

    private String color;

    public ChallengeAIState(ChallengeAIState copy){
        this.gameID = copy.gameID;
        this.color = copy.color;
    }

    public ChallengeAIState() {}

    public void setColor(String color) {
        this.color = color;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public String getColor() {
        return color;
    }

    public String getGameID() {
        return gameID;
    }
}

