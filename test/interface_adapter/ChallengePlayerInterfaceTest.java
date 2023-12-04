package interface_adapter;

import interface_adapter.challenge_player.ChallengePlayerPresenter;
import interface_adapter.challenge_player.ChallengePlayerViewModel;
import interface_adapter.challenge_player.ChallengePlayerController;
import interface_adapter.challenge_player.ChallengePlayerState;
import use_case.TestingAPIChallengeDAO;
import use_case.TestingAPIEventStreamDAO;
import use_case.challenge_player.*;

import java.beans.PropertyChangeListener;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ChallengePlayerInterfaceTest {
    @org.junit.Test
    public void challenge_playerTest(){
        ChallengePlayerViewModel challengePlayerViewModel = new ChallengePlayerViewModel();
        PropertyChangeListener propertyChangeListener = null;
        challengePlayerViewModel.addPropertyChangeListener(propertyChangeListener);
        ChallengePlayerOutputBoundary challengePlayerPresenter = new ChallengePlayerPresenter(challengePlayerViewModel);
        ChallengePlayerDataAccessInterface challengePlayerDataAccessInterface = new TestingAPIChallengeDAO();
        ChallengePlayerDataAccessInterface2 challengePlayerDataAccessInterface2 = new TestingAPIEventStreamDAO("white");

        ChallengePlayerInteractor challengePlayerInteractor = new ChallengePlayerInteractor(challengePlayerPresenter, challengePlayerDataAccessInterface, challengePlayerDataAccessInterface2);
        ChallengePlayerController challengePlayerController = new ChallengePlayerController(challengePlayerInteractor);
        challengePlayerController.execute(0,"bobby");

        assertEquals("white", challengePlayerViewModel.getState().getColor());
        assertEquals("game ID", challengePlayerViewModel.getState().getGameID());

    }
    @org.junit.Test
    public void challenge_playerTest_decline(){
        ChallengePlayerViewModel challengePlayerViewModel = new ChallengePlayerViewModel();
        PropertyChangeListener propertyChangeListener = null;
        challengePlayerViewModel.addPropertyChangeListener(propertyChangeListener);
        ChallengePlayerOutputBoundary challengePlayerPresenter = new ChallengePlayerPresenter(challengePlayerViewModel);
        ChallengePlayerDataAccessInterface challengePlayerDataAccessInterface = new TestingAPIChallengeDAO();
        ChallengePlayerDataAccessInterface2 challengePlayerDataAccessInterface2 = new TestingAPIEventStreamDAO("declined");

        ChallengePlayerInteractor challengePlayerInteractor = new ChallengePlayerInteractor(challengePlayerPresenter, challengePlayerDataAccessInterface, challengePlayerDataAccessInterface2);
        ChallengePlayerController challengePlayerController = new ChallengePlayerController(challengePlayerInteractor);
        challengePlayerController.execute(1,"bobby");

        assertNull(challengePlayerViewModel.getState().getColor());

    }
    @org.junit.Test
    public void challenge_playerTestrandom(){
        ChallengePlayerViewModel challengePlayerViewModel = new ChallengePlayerViewModel();
        PropertyChangeListener propertyChangeListener = null;
        challengePlayerViewModel.addPropertyChangeListener(propertyChangeListener);
        ChallengePlayerOutputBoundary challengePlayerPresenter = new ChallengePlayerPresenter(challengePlayerViewModel);
        ChallengePlayerDataAccessInterface challengePlayerDataAccessInterface = new TestingAPIChallengeDAO();
        ChallengePlayerDataAccessInterface2 challengePlayerDataAccessInterface2 = new TestingAPIEventStreamDAO("white");

        ChallengePlayerInteractor challengePlayerInteractor = new ChallengePlayerInteractor(challengePlayerPresenter, challengePlayerDataAccessInterface, challengePlayerDataAccessInterface2);
        ChallengePlayerController challengePlayerController = new ChallengePlayerController(challengePlayerInteractor);
        challengePlayerController.execute(5,"bobby");

        assertEquals("white", challengePlayerViewModel.getState().getColor());
        assertEquals("game ID", challengePlayerViewModel.getState().getGameID());

    }
}
