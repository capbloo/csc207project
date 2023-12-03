package data_access;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.async.methods.AbstractCharResponseConsumer;
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;
import org.apache.hc.client5.http.impl.async.HttpAsyncClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.message.BasicHttpRequest;
import org.apache.hc.core5.http.message.StatusLine;
import org.apache.hc.core5.http.nio.support.BasicRequestProducer;
import org.apache.hc.core5.http.support.BasicRequestBuilder;
import org.apache.hc.core5.io.CloseMode;
import org.apache.hc.core5.reactor.IOReactorConfig;
import org.apache.hc.core5.util.Timeout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.CharBuffer;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static java.lang.Thread.sleep;

public class GetMoveDataAccessObject {
    private String API_TOKEN = null;
    private final String gameID;
    private volatile List<String> moves = new ArrayList<>();

    public GetMoveDataAccessObject(String gameID) {
        try {
            Scanner reader = new Scanner(new File("token.txt"));
            API_TOKEN = reader.nextLine();
        } catch (FileNotFoundException e) {
            System.out.println("Couldn't read API Token. (token.txt missing?)");
            e.printStackTrace();
        }

        this.gameID = gameID;
        newThreadBoardStream();
    }

    public static void main(final String[] args) {

    }

    /**
     * Returns true if the API has already returned the move that would follow.
     * Call this if you don't want to potentially get stuck in a loop after calling getMoveAfter().
     *
     * @param moveNo The number of the move just played. Will check for NEXT move after this.
     * @return true if a call to getMoveAfter would return instantly right now. false if not.
     */
    public boolean has(int moveNo) {
        return moves.size() > moveNo;
    }
    
    /**
     * Waits until the API sends back the move requested, then returns it.
     * NOTE: MAY WAIT FOR A WHILE BEFORE RETURNING A VALUE, DEPENDING ON GAME STATE
     *
     * @return A string representing a move in UCI format.
     * @param moveNo The number of the move just played. Will wait for the NEXT move after this.
     */
    public String getMoveAfter(int moveNo) {
        boolean done = false;

        while(!done) {
            if (moves.size() > moveNo) {
                return moves.get(moveNo);
            }
            try {
                sleep(20);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        return null; // this should not be possible to reach
    }

    private void newThreadBoardStream() {
        Thread streamThread = new Thread(this::makeBoardStream);
        streamThread.start();
    }

    private void makeBoardStream() {
        final IOReactorConfig ioReactorConfig = IOReactorConfig.custom().setSoTimeout(Timeout.ofSeconds(60)).build();
        final CloseableHttpAsyncClient client = HttpAsyncClients.custom().setIOReactorConfig(ioReactorConfig).build();

        client.start();

        final HttpHost target = new HttpHost("lichess.org");
        String requestUri = "/api/bot/game/stream/" + gameID;

        final BasicHttpRequest request = BasicRequestBuilder
                .get()
                .setHttpHost(target)
                .setPath(requestUri)
                .setHeader("Authorization", "Bearer " + API_TOKEN)
                .build();

        System.out.println("Executing request " + request);
        final Future<Void> future = client.execute(new BasicRequestProducer(request, null), new AbstractCharResponseConsumer<>() {

            @Override
            protected void start(final HttpResponse response, final ContentType contentType) {
                System.out.println(request + "->" + new StatusLine(response));
            }

            @Override
            protected int capacityIncrement() {
                return Integer.MAX_VALUE;
            }

            @Override
            protected void data(final CharBuffer data, final boolean endOfStream) {
                StringBuilder output = new StringBuilder();
                while (data.hasRemaining()) {
                    char c = data.get();

                    output.append(c);

                    if (c == '\n') { // end of a line
                        String line = output.toString();

                        if (!line.equals("\n")) { // cull keepalive newlines
                            Map<String, Object> lineMap = jsonToMap(line);

                            String moveString = "";

                            for (String key : lineMap.keySet()) {
                                if (key.equals("state")) { // process the initial block that gets sent
                                    Object stateMap = lineMap.get(key);

                                    if (stateMap instanceof Map) {
                                        moveString = (String) ((Map<?, ?>) stateMap).get("moves");
                                    } else {
                                        throw new RuntimeException("State wasn't a map...");
                                    }
                                    break;
                                } else if (key.equals("moves")) {
                                    moveString = (String) lineMap.get("moves");
                                }
                            }

                            // update list of moves
                            moves = Arrays.asList(moveString.split(" "));
                        }
                        output = new StringBuilder();
                    }
                }
                if (endOfStream) {
                    System.out.println("End of stream");
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

        System.out.println("Board stream closing.");

        client.close(CloseMode.GRACEFUL);
    }

    private static Map<String, Object> jsonToMap(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
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
