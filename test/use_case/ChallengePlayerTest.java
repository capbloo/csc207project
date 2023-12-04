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
        ChallengePlayerOutputBoundary challengePlayerPresenter = new ChallengePlayerPresenter(challengePlayerViewModel);
        ChallengePlayerDataAccessInterface challengePlayerDataAccessInterface = new TestingAPIChallengeDAO();
        ChallengePlayerInputData challengePlayerInputData = new ChallengePlayerInputData("John","white");
        ChallengePlayerDataAccessInterface2 challengePlayerDataAccessInterface2 = new TestingAPIEventStreamDAO("white");

        ChallengePlayerInteractor challengePlayerInteractor = new ChallengePlayerInteractor(challengePlayerPresenter, challengePlayerDataAccessInterface, challengePlayerDataAccessInterface2);
        challengePlayerInteractor.execute(challengePlayerInputData);

        assertEquals("white", challengePlayerViewModel.getState().getColor());

    }
    @org.junit.Test
    public void challenge_playerTest_decline(){
        ChallengePlayerViewModel challengePlayerViewModel = new ChallengePlayerViewModel();
        ChallengePlayerOutputBoundary challengePlayerPresenter = new ChallengePlayerPresenter(challengePlayerViewModel);
        ChallengePlayerDataAccessInterface challengePlayerDataAccessInterface = new TestingAPIChallengeDAO();
        ChallengePlayerInputData challengePlayerInputData = new ChallengePlayerInputData("John","white");
        ChallengePlayerDataAccessInterface2 challengePlayerDataAccessInterface2 = new TestingAPIEventStreamDAO("decline");

        ChallengePlayerInteractor challengePlayerInteractor = new ChallengePlayerInteractor(challengePlayerPresenter, challengePlayerDataAccessInterface, challengePlayerDataAccessInterface2);
        challengePlayerInteractor.execute(challengePlayerInputData);

        assertEquals("decline",challengePlayerViewModel.getState().getColor());

    }
}
