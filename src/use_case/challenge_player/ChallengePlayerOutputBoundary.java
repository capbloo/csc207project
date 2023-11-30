package use_case.challenge_player;

public interface ChallengePlayerOutputBoundary {
    void prepareGame(ChallengePlayerOutputData challengePlayerOutputData);
    void cancelGame();
}
