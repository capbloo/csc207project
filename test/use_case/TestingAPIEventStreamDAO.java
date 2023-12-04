package use_case;

import use_case.challenge_ai.ChallengeAIDataAccessInterface2;
import use_case.challenge_player.ChallengePlayerDataAccessInterface2;

public class TestingAPIEventStreamDAO implements ChallengeAIDataAccessInterface2, ChallengePlayerDataAccessInterface2 {
   String color;
    public TestingAPIEventStreamDAO(String color){
       this.color = color;
   }
    @Override
    public String getChallengeInfo(String gameID) {
        return this.color;
    }
}
