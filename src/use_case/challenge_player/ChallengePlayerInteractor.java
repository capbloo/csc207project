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

        // TODO figure out how to get the correct color in if "random" chosen

        ChallengePlayerOutputData challengePlayerOutputData = new ChallengePlayerOutputData(gameID, color);

        challengePlayerOutputBoundary.prepareGame(challengePlayerOutputData);
    }
}
