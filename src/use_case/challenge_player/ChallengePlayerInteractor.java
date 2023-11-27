package use_case.challenge_player;

public class ChallengePlayerInteractor implements ChallengePlayerInputBoundary {
    final ChallengePlayerOutputBoundary challengePlayerOutputBoundary;
    final ChallengePlayerDataAccessInterface challengePlayerDataAccessInterface;

    public ChallengePlayerInteractor(ChallengePlayerOutputBoundary challengePlayerOutputBoundary, ChallengePlayerDataAccessInterface challengePlayerDataAccessInterface) {
        this.challengePlayerOutputBoundary = challengePlayerOutputBoundary;
        this.challengePlayerDataAccessInterface = challengePlayerDataAccessInterface;
    }

    @Override
    public void execute(ChallengePlayerInputData challengePlayerInputData) {
        String color = challengePlayerInputData.getColor();
        String name = challengePlayerInputData.getName();

        String gameID = challengePlayerDataAccessInterface.challengePlayer(color, name);

        ChallengePlayerOutputData challengePlayerOutputData = new ChallengePlayerOutputData(gameID);

        challengePlayerOutputBoundary.prepareGame(challengePlayerOutputData);
    }
}
