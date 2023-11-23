package app;

import data_access.MakeMoveDataAccessObject;
import entity.Board;

import java.awt.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.*;
import java.io.IOException;
import java.util.Scanner;
import java.io.*;
import javax.swing.*;

import interface_adapter.make_move.MakeMoveController;
import interface_adapter.make_move.MakeMovePresenter;
import interface_adapter.make_move.MakeMoveViewModel;
import use_case.make_move.MakeMoveInteractor;
import view.BoardView;
import view.MainMenuView;

public class Main {
    public static void main(String[] args) {
        // Display the chess board
        Board board = new Board();

        // creating the items needed for the make move use case, may want to turn this into a factory?
        MakeMoveViewModel makeMoveViewModel = new MakeMoveViewModel();
        MakeMovePresenter makeMovePresenter = new MakeMovePresenter(makeMoveViewModel);
        MakeMoveDataAccessObject makeMoveDataAccessObject = new MakeMoveDataAccessObject("abcd");
        MakeMoveInteractor makeMoveInteractor = new MakeMoveInteractor(makeMoveDataAccessObject, makeMovePresenter, board);
        MakeMoveController makeMoveController = new MakeMoveController(makeMoveInteractor);

        BoardView boardView = new BoardView(board, makeMoveController, makeMoveViewModel);

        boardView.setVisible(true);

        // Display the main menu
        //new MainMenuView().show();

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
