package use_case.challenge_player;

public class ChallengePlayerInputData {
    final String name;
    final String color;

    public ChallengePlayerInputData(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }
}
