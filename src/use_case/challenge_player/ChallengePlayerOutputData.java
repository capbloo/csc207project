package use_case.challenge_player;

public class ChallengePlayerOutputData {
    final String gameID;

    public ChallengePlayerOutputData(String gameID) {
        this.gameID = gameID;
    }

    public String getGameID() {
        return gameID;
    }
}
