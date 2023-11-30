package use_case.challenge_player;

import java.util.concurrent.ExecutionException;

public interface ChallengePlayerDataAccessInterface2 {
    String getChallengeInfo() throws ExecutionException, InterruptedException;
}
