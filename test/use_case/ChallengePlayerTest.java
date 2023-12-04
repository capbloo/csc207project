package use_case;

import data_access.APIChallengeDataAccessObject;
import data_access.APIEventStreamDataAccessObject;

import interface_adapter.challenge_player.ChallengePlayerViewModel;

import use_case.challenge_player.ChallengePlayerDataAccessInterface;
import use_case.challenge_player.ChallengePlayerDataAccessInterface2;
import use_case.challenge_player.ChallengePlayerInputData;
import use_case.challenge_player.ChallengePlayerInteractor;
import use_case.challenge_player.ChallengePlayerOutputBoundary;

import interface_adapter.challenge_player.ChallengePlayerPresenter;

import static org.junit.Assert.*;

public class ChallengePlayerTest {
    @org.junit.Test
    public void challenge_playerTest(){
        ChallengePlayerViewModel challengePlayerViewModel = new ChallengePlayerViewModel();
        ChallengePlayerOutputBoundary challengePlayerpresenter = new ChallengePlayerPresenter(challengePlayerViewModel);
        ChallengePlayerDataAccessInterface challengePlayerDataAccessInterface = new APIChallengeDataAccessObject();
        ChallengePlayerDataAccessInterface2 challengePlayerDataAccessInterface2 = new APIEventStreamDataAccessObject();

        ChallengePlayerInteractor challengePlayerInteractor = new ChallengePlayerInteractor(challengePlayerpresenter, challengePlayerDataAccessInterface, challengePlayerDataAccessInterface2);
        ChallengePlayerInputData challengePlayerInputData = new ChallengePlayerInputData("John","white");
        challengePlayerInteractor.execute(challengePlayerInputData);

        assertEquals("white", challengePlayerViewModel.getState().getColor());

    }
}
