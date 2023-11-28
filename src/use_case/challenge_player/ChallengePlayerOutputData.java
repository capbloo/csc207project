package use_case.challenge_player;

public class ChallengePlayerOutputData {
    final String gameID;
    final String color;

    public ChallengePlayerOutputData(String gameID, String color) {
        this.gameID = gameID;
        this.color = color;
    }

    public String getGameID() {
        return gameID;
    }

    public String getColor() {
        return color;
    }
}
