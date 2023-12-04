package use_case;

import data_access.APIChallengeDataAccessObject;
import data_access.APIEventStreamDataAccessObject;
import interface_adapter.challenge_ai.ChallengeAIViewModel;
import use_case.challenge_ai.ChallengeAIDataAccessInterface;
import use_case.challenge_ai.ChallengeAIDataAccessInterface2;
import use_case.challenge_ai.ChallengeAIInputData;
import use_case.challenge_ai.ChallengeAIInteractor;
import use_case.challenge_ai.ChallengeAIOutputBoundary;

import interface_adapter.challenge_ai.ChallengeAIPresenter;


import static org.junit.Assert.*;

public class ChallengeAITest {
    @org.junit.Test
    public void challenge_aiTest(){
        ChallengeAIViewModel challengeAIViewModel = new ChallengeAIViewModel();
        ChallengeAIOutputBoundary challengeAIpresenter = new ChallengeAIPresenter(challengeAIViewModel);
        ChallengeAIDataAccessInterface challengeAIDataAccessInterface = new APIChallengeDataAccessObject();
        ChallengeAIDataAccessInterface2 challengeAIDataAccessInterface2 = new APIEventStreamDataAccessObject();

        ChallengeAIInteractor challengeAIInteractor = new ChallengeAIInteractor(challengeAIpresenter, challengeAIDataAccessInterface, challengeAIDataAccessInterface2);
        ChallengeAIInputData challengeAIInputData = new ChallengeAIInputData(1,"white");
        challengeAIInteractor.execute(challengeAIInputData);

        assertEquals("white", challengeAIViewModel.getState().getColor());

    }

}
