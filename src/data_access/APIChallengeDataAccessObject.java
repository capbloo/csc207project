package data_access;

import use_case.challenge_ai.ChallengeAIDataAccessInterface;
import use_case.challenge_player.ChallengePlayerDataAccessInterface;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class APIChallengeDataAccessObject implements ChallengeAIDataAccessInterface, ChallengePlayerDataAccessInterface {
    private String API_TOKEN = null;
    private final String LICHESS = "https://lichess.org";

    public APIChallengeDataAccessObject() {
        try {
            Scanner reader = new Scanner(new File("token.txt"));
            API_TOKEN = reader.nextLine();
        } catch (FileNotFoundException e) {
            System.out.println("Couldn't read API Token. (token.txt missing?)");
            e.printStackTrace();
        }
    }

    public String challengeAI(String color, int difficulty) {
        HttpResponse<String> response = null;

        try {
            String jsonBody = "{\"level\":" + Integer.toString(difficulty) + ",\"color\":" + color + "}";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(LICHESS + "/api/challenge/ai"))
                    .header("Authorization", "Bearer " + API_TOKEN)
                    .POST(HttpRequest.BodyPublishers.noBody())
                    .build();
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        }
        catch (URISyntaxException | InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public String challengePlayer(String color, String name) {
        HttpResponse<String> response = null;

        try {
            String jsonBody = "{\"color\":" + color + "}";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(LICHESS + "/api/challenge/" + name))
                    .header("Authorization", "Bearer " + API_TOKEN)
                    .POST(HttpRequest.BodyPublishers.noBody())
                    .build();
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        }
        catch (URISyntaxException | InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }
}
