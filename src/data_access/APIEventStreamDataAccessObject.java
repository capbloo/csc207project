package data_access;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.WebSocket;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.lang.Thread.sleep;

public class APIEventStreamDataAccessObject {

    private String API_TOKEN;
    private String gameInfo;
    private final String LICHESS = "https://lichess.org";

    public static void main(String[] args) throws Exception {
        APIEventStreamDataAccessObject dao = new APIEventStreamDataAccessObject();

        System.out.println(dao.getChallengeInfo("bogus"));
    }

    public APIEventStreamDataAccessObject() {
        try {
            Scanner reader = new Scanner(new File("token.txt"));
            API_TOKEN = reader.nextLine();
        } catch (FileNotFoundException e) {
            System.out.println("Couldn't read API Token. (token.txt missing?)");
            e.printStackTrace();
        }
    }
    public String getChallengeInfo(String gameID) {

        System.out.println("1");

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(LICHESS + "/api/stream/event"))
                .header("Authorization", "Bearer " + API_TOKEN)
                .GET()
                .build();

        System.out.println("2");

        CompletableFuture<WebSocket> wsFuture = client.newWebSocketBuilder()
                .buildAsync(request.uri(), new WebSocketListener());

        System.out.println("3");

        AtomicBoolean webSocketOpen = new AtomicBoolean(true);

        // Attach a completion stage to the WebSocket future for handling termination
        wsFuture.thenAccept(webSocket -> {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("4");

            webSocket.sendClose(WebSocket.NORMAL_CLOSURE, "test");
            webSocketOpen.set(false); // Set the flag to indicate that the WebSocket is closed
        });

        // Keep the main thread alive while the WebSocket is open
        while (webSocketOpen.get()) {
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Access the stored game start events after the WebSocket has closed
        return gameInfo;
    }

    private class WebSocketListener implements WebSocket.Listener {

        private final ObjectMapper objectMapper = new ObjectMapper();

        @Override
        public void onOpen(WebSocket webSocket) {
            System.out.println("WebSocket opened");
        }

        @Override
        public CompletionStage<?> onClose(WebSocket webSocket, int statusCode, String reason) {
            System.out.println("WebSocket closed with status code " + statusCode + ": " + reason);
            return null;
        }

        @Override
        public CompletableFuture<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
            String json = data.toString();
            System.out.println(json);
            if (!json.isEmpty()) {
                try {
                    JsonNode jsonNode = objectMapper.readTree(json);
                    String eventType = jsonNode.get("type").asText();

                    // Handle different event types
                    switch (eventType) {
                        case "gameStart" -> {
                            gameInfo = json;
                        }
                        case "challengeDeclined" -> {
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return CompletableFuture.completedFuture(json);
        }
    }
}