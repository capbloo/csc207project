package view;

import entity.Board;
import entity.Move;
import entity.Piece;
import entity.PieceBuilder;
import entity.ChessButton;
import interface_adapter.HighlightSquare.HighlightViewModel;
import interface_adapter.make_move.MakeMoveController;
import interface_adapter.make_move.MakeMoveState;
import interface_adapter.make_move.MakeMoveViewModel;
import interface_adapter.HighlightSquare.HighlightController;
import use_case.make_move.MakeMoveInputData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;

public class BoardView extends JFrame implements ActionListener, PropertyChangeListener {

    private MakeMoveController makeMoveController;
    private MakeMoveViewModel makeMoveViewModel;
    private HighlightController highlightController;
    private HighlightViewModel highlightViewModel;
    private ChessButton previousMove;
    private PieceBuilder pieceBuilder;
    private static HashMap<ArrayList<Integer>, ChessButton> buttonList = new HashMap<>();

    public BoardView(Board board, MakeMoveController makeMoveController, MakeMoveViewModel makeMoveViewModel,
                     HighlightController highlightController, HighlightViewModel highlightViewModel) {
        this.makeMoveController = makeMoveController;
        this.makeMoveViewModel = makeMoveViewModel;
        this.highlightController = highlightController;
        this.highlightViewModel = highlightViewModel;
        makeMoveViewModel.addPropertyChangeListener(this);
        //highlightViewModel.addPropertyChangeListener(this);
        pieceBuilder = new PieceBuilder();

        setBounds(1000, 1000, 1000, 1000);
        setLayout(new BorderLayout());
        JPanel pn = new JPanel(new GridLayout(8, 8));
        setBounds(800, 800, 800, 800);
        boolean iswhite = true;
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {

                ChessButton block = new ChessButton();
                pn.add(block);
                block.setBounds(x * 64, y * 64, 64, 64);
                ArrayList<Integer> pos1 = new ArrayList<Integer>();
                pos1.add(x + 1);
                pos1.add(y + 1);
                block.setCoord(x + 1, 8 - y);
                ArrayList<Integer> coord = new ArrayList<>();
                coord.add(x + 1);
                coord.add(8 - y);
                buttonList.put(coord, block);
                block.addActionListener(this);
                if (!(board.getBoardstate().get(pos1) == null)) {
                    block.setText(board.getBoardstate().get(pos1).toString());
                    Font f = new Font("serif", Font.PLAIN, 75);
                    block.setFont(f);
                    block.setPiece(board.getBoardstate().get(pos1).symbolToString());
                    block.setPieceColour(board.getBoardstate().get(pos1).getColor());
                }

                if (iswhite) {
                    block.setBackground(new Color(69, 75, 27));
                    block.setOpaque(true);
                    block.setBorderPainted(false);
                    block.setSquareColour("green");
                } else {
                    block.setBackground(Color.lightGray);
                    block.setOpaque(true);
                    block.setBorderPainted(false);
                    block.setSquareColour("white");
                }

                iswhite = !iswhite;
            }
            iswhite = !iswhite;
        }
        add(pn, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new GridLayout(1, 8));
        JPanel leftPanel = new JPanel(new GridLayout(8, 1));
        bottomPanel.setBounds(800, 800, 800, 800);
        leftPanel.setBounds(800, 800, 800, 800);
        int[] numSideBar = {0, 1, 2, 3, 4, 5, 6, 7, 8};
        String[] letterSideBar = {"x", "h", "g", "f", "e", "d", "c", "b", "a"};
        int i = numSideBar.length - 1;

        while (i > 0) {
            bottomPanel.add(new JLabel(letterSideBar[i]));
            leftPanel.add(new JLabel(String.valueOf(numSideBar[i])));
            i--;
        }
        add(bottomPanel, BorderLayout.SOUTH);
        add(leftPanel, BorderLayout.WEST);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ChessButton clickedButton = (ChessButton) e.getSource();
        Integer x = clickedButton.getCol();
        Integer y = clickedButton.getRow();
/*        System.out.println(buttonList.keySet());
        for (ChessButton button : buttonList.values()) {
            System.out.println(button.isHighlighted());
        }*/
        System.out.println(buttonList.keySet());


        if (clickedButton.isEmpty() && previousMove == null) {
            unhighlight(buttonList);
        } else if (this.previousMove == null) {
            if (!clickedButton.isEmpty()) {
                highlightController.execute(clickedButton, buttonList);
                highlight(buttonList);
            } else {
                unhighlight(buttonList);
            }
            this.previousMove = clickedButton;
            System.out.println("button clicked");


        } else if (this.previousMove.getRow().equals(y) && this.previousMove.getCol().equals(x)) {
            this.previousMove = null;
            System.out.println("selection reset");
            unhighlight(buttonList);
        } else {
            Piece piece = pieceBuilder.create(previousMove.getPiece(), previousMove.getPieceColour());
            ArrayList<Integer> origin = new ArrayList<>();
            origin.add(previousMove.getRow());
            origin.add(previousMove.getCol());

            ArrayList<Integer> destination = new ArrayList<>();
            destination.add(x);
            destination.add(y);
            Move move = new Move(piece, origin, destination);

            makeMoveController.execute(move, clickedButton);
            unhighlight(buttonList);
        }
    }

    public void propertyChange(PropertyChangeEvent e) {
        MakeMoveState state = (MakeMoveState) e.getNewValue();
        if (state.isMoveError()) {
            JOptionPane.showMessageDialog(this, "illegal move");
        } else {
            previousMove.clear();
            this.previousMove = null;
        }
    }

    public void highlight(HashMap<ArrayList<Integer>, ChessButton> buttonsList) {
        for (ChessButton button : buttonsList.values()) {
            if (button.isHighlighted()) {
                button.setBackground(Color.red);
                button.setOpaque(true);
                button.setBorderPainted(false);
            }
        }
    }

    public void unhighlight(HashMap<ArrayList<Integer>, ChessButton> buttonsList) {
        for (ChessButton button : buttonsList.values()) {
            button.cancelHighlight();

            if (button.getSquareColor().equals("green")) {
                button.setBackground(new Color(69, 75, 27));
                button.setOpaque(true);
                button.setBorderPainted(false);
            } else if (button.getSquareColor().equals("white")) {
                button.setBackground(Color.lightGray);
                button.setOpaque(true);
                button.setBorderPainted(false);
            }

        }

    }

}



