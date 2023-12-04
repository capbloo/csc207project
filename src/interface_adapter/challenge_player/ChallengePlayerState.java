package interface_adapter.challenge_player;

import interface_adapter.challenge_ai.ChallengeAIState;

import java.sql.DataTruncation;

public class ChallengePlayerState {

    private String gameID;

    private String color;
    public ChallengePlayerState() {};
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
