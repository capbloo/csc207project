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
import java.util.Map;
import java.util.Scanner;

import com.fasterxml.jackson.databind.*;

public class APIChallengeDataAccessObject implements ChallengeAIDataAccessInterface, ChallengePlayerDataAccessInterface {
    private String API_TOKEN = null;
    private final String LICHESS = "https://lichess.org";

    public APIChallengeDataAccessObject() { // when instantiated, read in API token from LOCAL text file
        try {
            Scanner reader = new Scanner(new File("token.txt"));
            API_TOKEN = reader.nextLine();
        } catch (FileNotFoundException e) {
            System.out.println("Couldn't read API Token. (token.txt missing?)");
            e.printStackTrace();
        }
    }

    /** Send a challenge to the Lichess AI.
     * @param color Starting color for our side: either "black", "white", or "random".
     * @param difficulty Desired difficulty of the AI, as an integer from 1 to 8.
     * @return The game ID if the challenge was valid.
     * @throws RuntimeException if an exception occurs while making the HttpRequest or if response code is not 201 Created
     */
    public String challengeAI(String color, int difficulty) {
        HttpResponse<String> response;

        try {
            // create the challenge, send it, and save the response
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(LICHESS + "/api/challenge/ai"))
                    .header("Authorization", "Bearer " + API_TOKEN)
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString("level=" + difficulty + "&color=" + color))
                    .build();

            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        } catch (URISyntaxException | InterruptedException | IOException e) { // Shouldn't happen, so we just throw the error and debug if needed.
            throw new RuntimeException(e);
        }

        if (response.statusCode() != 201) { // Expected status code is 201 for success or 400 for fail. We'll treat anything other than 200 as a fail.
            throw new RuntimeException(response.statusCode() + " Sending AI challenge to Lichess API failed. HTTP response body:\n" + response.body());
        }

        Map<String, Object> responseMap = HttpBodyToString(response.body()); // convert the JSON response to a Map

        return (String) responseMap.get("id"); // pull the challenge id out of the Map and return
    }

    /** Send an unrated correspondence challenge to another player on Lichess.
     * @param color Starting color for our side: either "black", "white", or "random".
     * @param name Name of the player to challenge. Must be a registered player on Lichess.
     * @return The game ID if the challenge was valid.
     * @throws RuntimeException if an exception occurs while making the HttpRequest or if response code is not 200 OK
     */
    public String challengePlayer(String color, String name) {
        HttpResponse<String> response;

        System.out.println(LICHESS + "/api/challenge/" + name);

        try {
            // create the challenge, send it, and save the response
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(LICHESS + "/api/challenge/" + name))
                    .header("Authorization", "Bearer " + API_TOKEN)
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString("&color=" + color))
                    .build();

            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        } catch (URISyntaxException | InterruptedException | IOException e) { // Shouldn't happen, so we just throw the error and debug if needed.
            throw new RuntimeException(e);
        }

        if (response.statusCode() != 200) { // Expected status code is 200 for success or 400 for fail. We'll treat anything other than 200 as a fail.
            throw new RuntimeException(response.statusCode() + " Sending AI challenge to Lichess API failed. HTTP response body:\n" + response.body());
        }

        Map<String, Object> responseMap = HttpBodyToString(response.body()); // convert the JSON response to a Map

        return (String) ((Map<?, ?>) responseMap.get("challenge")).get("id"); // pull the challenge id out of the Map and return
    }

    /**
     * Convert a JSON string to a Map.
     * @param httpResponse A string, formatted as JSON
     * @return A corresponding Map
     */
    private static Map<String, Object> HttpBodyToString(String httpResponse) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> responseMap = null;

        try {
            // Convert JSON string to Map
            responseMap = objectMapper.readValue(httpResponse, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return responseMap;
    }
}
