package use_case.challenge_player;

public class ChallengePlayerInteractor implements ChallengePlayerInputBoundary {
    final ChallengePlayerOutputBoundary challengePlayerOutputBoundary;
    final ChallengePlayerDataAccessInterface challengePlayerDataAccessInterface;
    final ChallengePlayerDataAccessInterface2 challengePlayerDataAccessInterface2;

    public ChallengePlayerInteractor(ChallengePlayerOutputBoundary challengePlayerOutputBoundary, ChallengePlayerDataAccessInterface challengePlayerDataAccessInterface, ChallengePlayerDataAccessInterface2 challengePlayerDataAccessInterface2) {
        this.challengePlayerOutputBoundary = challengePlayerOutputBoundary;
        this.challengePlayerDataAccessInterface = challengePlayerDataAccessInterface;
        this.challengePlayerDataAccessInterface2 = challengePlayerDataAccessInterface2;
    }

    @Override
    public void execute(ChallengePlayerInputData challengePlayerInputData) {
        // unpack data
        String color = challengePlayerInputData.getColor();
        String name = challengePlayerInputData.getName();

        // send the challenge and get the ID of the challenge
        String gameID = challengePlayerDataAccessInterface.challengePlayer(color, name);

        // challengeInfo is "white", "black", or "declined"
        String challengeInfo = challengePlayerDataAccessInterface2.getChallengeInfo(gameID);

        if (challengeInfo.equals("declined")) { // challenge was declined, so cancel challenge
            challengePlayerOutputBoundary.cancelGame();
        } else { // otherwise challengeInfo was a color, so we start the game with the appropriate data
            ChallengePlayerOutputData challengePlayerOutputData = new ChallengePlayerOutputData(gameID, challengeInfo);

            challengePlayerOutputBoundary.prepareGame(challengePlayerOutputData);
        }
    }
}
