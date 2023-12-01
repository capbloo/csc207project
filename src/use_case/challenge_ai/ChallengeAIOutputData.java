package use_case.challenge_ai;

public class ChallengeAIOutputData {
    final String gameID;
    final String color;

    public ChallengeAIOutputData(String gameID, String color) {
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
