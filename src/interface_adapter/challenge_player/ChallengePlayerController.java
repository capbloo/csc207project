package interface_adapter.challenge_player;

import use_case.challenge_player.ChallengePlayerInputBoundary;
import use_case.challenge_player.ChallengePlayerInputData;
import use_case.challenge_player.ChallengePlayerInteractor;

public class ChallengePlayerController {

    final ChallengePlayerInputBoundary challengePlayerInteractor;

    public ChallengePlayerController(ChallengePlayerInputBoundary challengePlayerInputBoundary){
        this.challengePlayerInteractor = challengePlayerInputBoundary;
    }

    public void excute(int color, String name){
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
