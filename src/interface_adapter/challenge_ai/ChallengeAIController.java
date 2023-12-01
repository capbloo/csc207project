package interface_adapter.challenge_ai;

import interface_adapter.CheckGameEnds.CheckGameEndsController;
import use_case.CheckGameEnds.CheckGameEndsInputBoundary;
import use_case.challenge_ai.ChallengeAIInputBoundary;
import use_case.challenge_ai.ChallengeAIInputData;
import use_case.challenge_ai.ChallengeAIInteractor;

public class ChallengeAIController {
    final ChallengeAIInputBoundary challengeAIInteractor;

    public ChallengeAIController(ChallengeAIInputBoundary challengeAIInputBoundary){
        this.challengeAIInteractor = challengeAIInputBoundary;
    }

    public void execute(int color, int level){
        String colorS = "";
        if (color == 0){
            colorS = "white";
        } else {
            colorS = "black";
        }
        ChallengeAIInputData challengeAIInputData = new ChallengeAIInputData(level, colorS);
        challengeAIInteractor.execute(challengeAIInputData);
    }
}
