package interface_adapter.challenge_ai;

import use_case.challenge_ai.ChallengeAIOutputBoundary;
import use_case.challenge_ai.ChallengeAIOutputData;

public class ChallengeAIPresenter implements ChallengeAIOutputBoundary {

    private final ChallengeAIViewModel challengeAIViewModel;

    public ChallengeAIPresenter(ChallengeAIViewModel challengeAIViewModel){
        this.challengeAIViewModel = challengeAIViewModel;
    }
    @Override
    public void prepareGame(ChallengeAIOutputData challengeAIOutputData) {
        ChallengeAIState challengeAIState = challengeAIViewModel.getState();
        challengeAIState.setGameID(challengeAIOutputData.getGameID());
        challengeAIState.setColor(challengeAIOutputData.getColor());

        challengeAIViewModel.firePropertyChanged();

    }
}
