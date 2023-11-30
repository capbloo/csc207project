package data_access;
import com.fasterxml.jackson.databind.ObjectMapper;
import use_case.challenge_ai.ChallengeAIDataAccessInterface2;
import use_case.challenge_player.ChallengePlayerDataAccessInterface2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.time.Duration;

import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

public class APIEventStreamDataAccessObject implements ChallengeAIDataAccessInterface2, ChallengePlayerDataAccessInterface2 {

    private String API_TOKEN;
    private final ObjectMapper objectMapper;
    private final String LICHESS = "https://lichess.org";

    public static void main(String[] args) throws Exception {
        APIChallengeDataAccessObject apiChallengeDataAccessObject = new APIChallengeDataAccessObject();

        System.out.println(apiChallengeDataAccessObject.challengePlayer("white", "capbloo"));

        APIEventStreamDataAccessObject dao = new APIEventStreamDataAccessObject();

        System.out.println(dao.getChallengeInfo(gameID));
    }

    public APIEventStreamDataAccessObject() {
        objectMapper = new ObjectMapper();
        try {
            Scanner reader = new Scanner(new File("token.txt"));
            API_TOKEN = reader.nextLine();
        } catch (FileNotFoundException e) {
            System.out.println("Couldn't read API Token. (token.txt missing?)");
            e.printStackTrace();
        }
    }
    public String getChallengeInfo(String gameID) {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .timeout(Duration.ofSeconds(30))
                .uri(URI.create(LICHESS + "/api/stream/event"))
                .header("Authorization", "Bearer " + API_TOKEN)
                .GET()
                .build();


        // ONLY WORKS IF NO OTHER CHALLENGES OR GAMES ARE ACTIVE
        CompletableFuture<HttpResponse<Stream<String>>> response = client.sendAsync(request, HttpResponse.BodyHandlers.ofLines());

        String responseBody;

        try {
            responseBody = response.get().body().findFirst().orElseThrow();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

        Map<String, Object> gameData = jsonToString(responseBody);

        System.out.println(gameData);

        if (gameData.get("type").equals("challengeDeclined")) {
            return "declined";
        } else if (gameData.get("type").equals("gameStart")) {
            return (String) ((Map<String, Object>) (gameData.get("game"))).get("color");
        }

        throw new RuntimeException("Some other response happened. We're not handling it. Cancel all challenges and try again.");
    }

    private Map<String, Object> jsonToString(String json) {
        Map<String, Object> responseMap = null;

        try {
            // Convert JSON string to Map
            responseMap = objectMapper.readValue(json, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return responseMap;
    }
}