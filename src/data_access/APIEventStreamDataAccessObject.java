package data_access;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.async.methods.AbstractCharResponseConsumer;
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;
import org.apache.hc.client5.http.impl.async.HttpAsyncClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.message.BasicHttpRequest;
import org.apache.hc.core5.http.message.StatusLine;
import org.apache.hc.core5.http.nio.support.BasicRequestProducer;
import org.apache.hc.core5.http.support.BasicRequestBuilder;
import org.apache.hc.core5.io.CloseMode;
import org.apache.hc.core5.reactor.IOReactorConfig;
import org.apache.hc.core5.util.Timeout;
import use_case.challenge_ai.ChallengeAIDataAccessInterface2;
import use_case.challenge_player.ChallengePlayerDataAccessInterface2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.nio.CharBuffer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static java.lang.Thread.sleep;

public class APIEventStreamDataAccessObject implements ChallengeAIDataAccessInterface2, ChallengePlayerDataAccessInterface2 {

    private String API_TOKEN;
    private final ObjectMapper objectMapper;
    private final List<Map<String, Object>> events = new ArrayList<>();

    public static void main(String[] args) {
        APIChallengeDataAccessObject apiChallengeDataAccessObject = new APIChallengeDataAccessObject();

        String gameID = apiChallengeDataAccessObject.challengeAI("white", 1);

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

        newThreadBoardStream();
    }

    private void startEventStream() {
        final IOReactorConfig ioReactorConfig = IOReactorConfig.custom().setSoTimeout(Timeout.ofSeconds(60)).build();
        final CloseableHttpAsyncClient client = HttpAsyncClients.custom().setIOReactorConfig(ioReactorConfig).build();

        client.start();

        final HttpHost target = new HttpHost("lichess.org");
        String requestUri = "/api/stream/event";

        final BasicHttpRequest request = BasicRequestBuilder
                .get()
                .setHttpHost(target)
                .setPath(requestUri)
                .setHeader("Authorization", "Bearer " + API_TOKEN)
                .build();

        System.out.println("Executing request " + request);
        final Future<Void> future = client.execute(new BasicRequestProducer(request, null), new AbstractCharResponseConsumer<>() {

            @Override
            protected void start(final org.apache.hc.core5.http.HttpResponse response, final ContentType contentType) {
                System.out.println(request + "->" + new StatusLine(response));
            }

            @Override
            protected int capacityIncrement() {
                return Integer.MAX_VALUE;
            }

            @Override
            protected void data(final CharBuffer data, final boolean endOfStream) {
                System.out.println("reached data");

                StringBuilder output = new StringBuilder();
                while (data.hasRemaining()) {
                    char c = data.get();

                    output.append(c);

                    if (c == '\n') { // end of a line
                        String line = output.toString();

                        System.out.println(line);

                        if (!line.equals("\n")) { // cull keepalive newlines
                            Map<String, Object> lineMap = jsonToMap(line);

                            if (lineMap != null) {

                                String type = (String) lineMap.get("type");

                                if (type.equals("challengeDeclined") || type.equals("gameStart")) {
                                    events.add(lineMap);
                                }
                            }
                        }
                        output = new StringBuilder();
                    }
                }
                if (endOfStream) {
                    System.out.println("End of event stream");
                }
            }

            @Override
            protected Void buildResult() {
                return null;
            }

            @Override
            public void failed(final Exception cause) {
                System.out.println(request + "->" + cause);
            }

            @Override
            public void releaseResources() {
            }

        }, null);

        try {
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Event stream closing.");

        client.close(CloseMode.GRACEFUL);
    }
    public String getChallengeInfo(String gameID) {
        while(true) {
            try {
                sleep(20);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            for (Map<String, Object> event : events) {
                if (event.get("type").equals("challengeDeclined")) {
                    if (((Map<?, ?>) (event.get("challenge"))).get("id").equals(gameID)) {
                        return "declined";
                    }
                } else if (event.get("type").equals("gameStart")) {
                    if (((Map<String, Object>) (event.get("game"))).get("id").equals(gameID)) {
                        Map<String, Object> gameMap = (Map<String, Object>) event.get("game");
                        return (String) gameMap.get("color");
                    }
                }
            }
        }
    }

    private void newThreadBoardStream() {
        Thread streamThread = new Thread(this::startEventStream);
        streamThread.start();
    }

    private Map<String, Object> jsonToMap(String json) {
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