package use_case.challenge_ai;

public class ChallengeAIInteractor implements ChallengeAIInputBoundary {
    final ChallengeAIOutputBoundary challengeAIOutputBoundary;
    final ChallengeAIDataAccessInterface challengeAIDataAccessInterface;
    final ChallengeAIDataAccessInterface2 challengeAIDataAccessInterface2;

    public ChallengeAIInteractor(ChallengeAIOutputBoundary challengeAIOutputBoundary,
                                 ChallengeAIDataAccessInterface challengeAIDataAccessInterface,
                                 ChallengeAIDataAccessInterface2 challengeAIDataAccessInterface2) {
        this.challengeAIOutputBoundary = challengeAIOutputBoundary;
        this.challengeAIDataAccessInterface = challengeAIDataAccessInterface;
        this.challengeAIDataAccessInterface2 = challengeAIDataAccessInterface2;
    }

    @Override
    public void execute(ChallengeAIInputData challengeAIInputData) {
        // unpack data
        String color = challengeAIInputData.getColor();
        int difficulty = challengeAIInputData.getDifficulty();

        // send the challenge and get the ID of the challenge
        String gameID = challengeAIDataAccessInterface.challengeAI(color, difficulty);

        // find the color that was assigned (might differ from "color" above if color == "random")
        String gameColor = challengeAIDataAccessInterface2.getChallengeInfo(gameID);

        // package output data
        ChallengeAIOutputData challengeAIOutputData = new ChallengeAIOutputData(gameID, gameColor);

        // this should not fail, so we just prepare the game
        challengeAIOutputBoundary.prepareGame(challengeAIOutputData);
    }
}
