package app;

import data_access.MakeMoveDataAccessObject;
import entity.Board;

import java.awt.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
import javax.swing.*;

import interface_adapter.make_move.MakeMoveController;
import interface_adapter.make_move.MakeMovePresenter;
import interface_adapter.make_move.MakeMoveViewModel;
import use_case.make_move.MakeMoveInteractor;
import view.BoardView;
import view.MainMenuView;

import interface_adapter.HighlightSquare.HighlightController;
import interface_adapter.HighlightSquare.HighlightPresenter;
import interface_adapter.HighlightSquare.HighlightViewModel;
import use_case.HighlightSquare.HighlightInteractor;

public class Main {
    public static void main(String[] args) {
        // Display the chess board
//        Board board = new Board();
//
//        // creating the items needed for the make move use case, may want to turn this into a factory?
//        MakeMoveViewModel makeMoveViewModel = new MakeMoveViewModel();
//        MakeMovePresenter makeMovePresenter = new MakeMovePresenter(makeMoveViewModel);
//        MakeMoveDataAccessObject makeMoveDataAccessObject = new MakeMoveDataAccessObject("abcd");
//        MakeMoveInteractor makeMoveInteractor = new MakeMoveInteractor(makeMoveDataAccessObject, makeMovePresenter, board);
//        MakeMoveController makeMoveController = new MakeMoveController(makeMoveInteractor);
//
//        HighlightViewModel highlightViewModel = new HighlightViewModel();
//        HighlightPresenter highlightPresenter = new HighlightPresenter(highlightViewModel);
//        HighlightInteractor highlightInteractor = new HighlightInteractor(highlightPresenter, board);
//        HighlightController highlightController = new HighlightController(highlightInteractor);
//
//        BoardView boardView = new BoardView(board, makeMoveController, makeMoveViewModel, highlightController,
//                highlightViewModel);
//
//        boardView.setVisible(true);

//        // Display the chess board
//        JFrame application = new JFrame("Chess game");
//        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        application.setVisible(true);
//        application.setBounds(100,100,654,678);
//        application.setLayout(null);
//        CardLayout cardLayout = new CardLayout();
//        Board board = new Board();
//
//        BoardView boardView = new BoardView(application, board);


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
