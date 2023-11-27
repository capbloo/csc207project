package use_case.challenge_ai;

public class ChallengeAIInteractor implements ChallengeAIInputBoundary{
    final ChallengeAIOutputBoundary challengeAIOutputBoundary;
    final ChallengeAIDataAccessInterface challengeAIDataAccessInterface;
    public ChallengeAIInteractor(ChallengeAIOutputBoundary challengeAIOutputBoundary, ChallengeAIDataAccessInterface challengeAIDataAccessInterface) {
        this.challengeAIOutputBoundary = challengeAIOutputBoundary;
        this.challengeAIDataAccessInterface = challengeAIDataAccessInterface;
    }
    @Override
    public void execute(ChallengeAIInputData challengeAIInputData) {

    }
}
