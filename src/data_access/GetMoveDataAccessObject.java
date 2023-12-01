package data_access;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

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

public class GetMoveDataAccessObject {
    private String API_TOKEN = null;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String gameID;
    private final ArrayList<Integer> moves = new ArrayList<>();

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

        System.out.println("this hopefully runs?");
    }

    public static void main(final String[] args) {
        APIChallengeDataAccessObject apiChallengeDataAccessObject = new APIChallengeDataAccessObject();

        String id = apiChallengeDataAccessObject.challengeAI("white", 1);

        GetMoveDataAccessObject getMoveDataAccessObject = new GetMoveDataAccessObject(id);
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
        final Future<Void> future = client.execute(new BasicRequestProducer(request, null), new AbstractCharResponseConsumer<Void>() {

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

                    System.out.print(c);
                    output.append(c);

                    if (c == '\n') { // end of a line
                        String line = output.toString();

                        if (!(line.isEmpty())) { // cull keepalive newlines
                            Map<String, Object> lineMap = jsonToMap(line);


                        }


                        output = new StringBuilder();
                    }
                }
                if (endOfStream) {
                    System.out.println("end of stream");
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
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
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
