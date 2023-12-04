package use_case;

import use_case.challenge_ai.ChallengeAIDataAccessInterface;
import use_case.challenge_player.ChallengePlayerDataAccessInterface;

public class TestingAPIChallengeDAO implements ChallengeAIDataAccessInterface, ChallengePlayerDataAccessInterface {
    @Override
    public String challengeAI(String color, int difficulty) {
        return "game ID";
    }

    @Override
    public String challengePlayer(String color, String name) {
        return "game ID";
    }
}
