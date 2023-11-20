package app;
import entity.Board;

import java.awt.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.*;
import java.io.IOException;
import java.util.Scanner;
import java.io.*;
import javax.swing.*;
import view.BoardView;
import view.MainMenuView;

public class Main {
    public static void main(String[] args) {
        // Display the chess board
        JFrame application = new JFrame("Chess game");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        application.setVisible(true);
        application.setBounds(100,100,600,600);
        application.setLayout(null);
        CardLayout cardLayout = new CardLayout();
        Board board = new Board();

        BoardView boardView = new BoardView(application, board);

        // Display the main menu
        new MainMenuView().show();

//        String API_URL = "https://lichess.org/api";
//        String API_TOKEN = "";
//
//        try {
//            Scanner reader = new Scanner(new File("token.txt"));
//            API_TOKEN = reader.nextLine();
//        } catch (FileNotFoundException e) {
//            System.out.println("Couldn't read API Token.");
//            e.printStackTrace();
//        }
//
//        try {
//            HttpRequest request = HttpRequest.newBuilder()
//                    .uri(new URI(API_URL + "/account"))
//                    .header("Authorization", "Bearer " + API_TOKEN)
//                    .GET()
//                    .build();
//            HttpResponse<String> response = null;
//
//            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
//
//            System.out.println(response.body());
//
//        } catch (URISyntaxException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
    }
}
