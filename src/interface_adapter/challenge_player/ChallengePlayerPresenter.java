package interface_adapter.challenge_player;

import use_case.challenge_player.ChallengePlayerOutputBoundary;
import use_case.challenge_player.ChallengePlayerOutputData;

public class ChallengePlayerPresenter implements ChallengePlayerOutputBoundary {

    private final ChallengePlayerViewModel challengePlayerViewModel;

    public ChallengePlayerPresenter(ChallengePlayerViewModel challengePlayerViewModel) {
        this.challengePlayerViewModel = challengePlayerViewModel;
    }

    @Override
    public void prepareGame(ChallengePlayerOutputData challengePlayerOutputData) {
        ChallengePlayerState challengePlayerState = challengePlayerViewModel.getState();
        challengePlayerState.setGameID(challengePlayerOutputData.getGameID());
        challengePlayerState.setColor(challengePlayerOutputData.getColor());

        challengePlayerViewModel.firePropertyChanged();

    }

    @Override
    public void cancelGame() {
    }
}
