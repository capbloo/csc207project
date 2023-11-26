package use_case.challenge_ai;

public class ChallengeAIInputData {
    final int difficulty;
    final String color;

    public ChallengeAIInputData(int difficulty, String color) {
        this.difficulty = difficulty;
        this.color = color;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public String getColor() {
        return color;
    }
}
