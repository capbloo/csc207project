package interface_adapter;

import interface_adapter.challenge_ai.ChallengeAIPresenter;
import interface_adapter.challenge_ai.ChallengeAIController;
import interface_adapter.challenge_ai.ChallengeAIState;
import interface_adapter.challenge_ai.ChallengeAIViewModel;
import use_case.TestingAPIChallengeDAO;
import use_case.TestingAPIEventStreamDAO;
import use_case.challenge_ai.*;

import java.beans.PropertyChangeListener;

import static org.junit.Assert.assertEquals;

public class ChallengeAIInterfaceTest {
    @org.junit.Test
    public void challenge_aiTestwhite(){
        ChallengeAIViewModel challengeAIViewModel = new ChallengeAIViewModel();
        ChallengeAIOutputBoundary challengeAIpresenter = new ChallengeAIPresenter(challengeAIViewModel);
        ChallengeAIDataAccessInterface challengeAIDataAccessInterface = new TestingAPIChallengeDAO();
        ChallengeAIInputData challengeAIInputData = new ChallengeAIInputData(1,"white");
        ChallengeAIDataAccessInterface2 challengeAIDataAccessInterface2 = new TestingAPIEventStreamDAO(challengeAIInputData.getColor());

        ChallengeAIInteractor challengeAIInteractor = new ChallengeAIInteractor(challengeAIpresenter, challengeAIDataAccessInterface, challengeAIDataAccessInterface2);
        ChallengeAIController challengeAIController = new ChallengeAIController(challengeAIInteractor);
        challengeAIController.execute(0,1);

        assertEquals("white", challengeAIViewModel.getState().getColor());

    }
    @org.junit.Test
    public void challenge_aiTestblack(){
        ChallengeAIViewModel challengeAIViewModel = new ChallengeAIViewModel();
        ChallengeAIOutputBoundary challengeAIpresenter = new ChallengeAIPresenter(challengeAIViewModel);
        ChallengeAIDataAccessInterface challengeAIDataAccessInterface = new TestingAPIChallengeDAO();
        ChallengeAIInputData challengeAIInputData = new ChallengeAIInputData(1,"white");
        ChallengeAIDataAccessInterface2 challengeAIDataAccessInterface2 = new TestingAPIEventStreamDAO(challengeAIInputData.getColor());

        ChallengeAIInteractor challengeAIInteractor = new ChallengeAIInteractor(challengeAIpresenter, challengeAIDataAccessInterface, challengeAIDataAccessInterface2);
        ChallengeAIController challengeAIController = new ChallengeAIController(challengeAIInteractor);
        challengeAIController.execute(1,1);

        assertEquals("white", challengeAIViewModel.getState().getColor());

    }
    @org.junit.Test
    public void challenge_aiTestelse(){
        ChallengeAIViewModel challengeAIViewModel = new ChallengeAIViewModel();
        ChallengeAIOutputBoundary challengeAIpresenter = new ChallengeAIPresenter(challengeAIViewModel);
        ChallengeAIDataAccessInterface challengeAIDataAccessInterface = new TestingAPIChallengeDAO();
        ChallengeAIInputData challengeAIInputData = new ChallengeAIInputData(1,"white");
        ChallengeAIDataAccessInterface2 challengeAIDataAccessInterface2 = new TestingAPIEventStreamDAO(challengeAIInputData.getColor());

        ChallengeAIInteractor challengeAIInteractor = new ChallengeAIInteractor(challengeAIpresenter, challengeAIDataAccessInterface, challengeAIDataAccessInterface2);
        ChallengeAIController challengeAIController = new ChallengeAIController(challengeAIInteractor);
        challengeAIController.execute(40,1);

        assertEquals("white", challengeAIViewModel.getState().getColor());
        assertEquals("game ID", challengeAIViewModel.getState().getGameID());

    }

}
