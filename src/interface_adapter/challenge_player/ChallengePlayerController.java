package interface_adapter.challenge_player;

import use_case.challenge_player.ChallengePlayerInputBoundary;
import use_case.challenge_player.ChallengePlayerInputData;

public class ChallengePlayerController {

    final ChallengePlayerInputBoundary challengePlayerInteractor;

    public ChallengePlayerController(ChallengePlayerInputBoundary challengePlayerInputBoundary){
        this.challengePlayerInteractor = challengePlayerInputBoundary;
    }

    public void execute(int color, String name){
        String colorS = "";
        if (color == 0){
            colorS = "white";
        } else if (color == 1){
            colorS = "black";
        }else {
            colorS = "random";
        }
        ChallengePlayerInputData challengePlayerInputData = new ChallengePlayerInputData(name, colorS);
        challengePlayerInteractor.execute(challengePlayerInputData);
    }

}
