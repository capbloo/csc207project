package data_access;

import entity.Move;
import use_case.make_move.MakeMoveDataAccessInterface;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class MakeMoveDataAccessObject implements MakeMoveDataAccessInterface {
    private String gameID;
    private String gameURL;

    public MakeMoveDataAccessObject(String gameID) {
        this.gameID = gameID;
        this.gameURL = "https://lichess.org/api/board/game/" + gameID;
    }

    public void pushMove(Move move) {
        String moveString = move.algebraicNotation();

        String API_TOKEN = "";
        try {
            Scanner reader = new Scanner(new File("token.txt"));
            API_TOKEN = reader.nextLine();
        } catch (FileNotFoundException e) {
            System.out.println("Couldn't read API Token.");
            e.printStackTrace();
        }

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    // url must be of the format: https://lichess.org/api/board/game/{gameId}/move/{move}
                    .uri(new URI(gameURL + "/move/" + moveString))
                    .header("Authorization", "Bearer " + API_TOKEN)
                    .POST(HttpRequest.BodyPublishers.noBody())
                    .build();
            HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        }
        catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
