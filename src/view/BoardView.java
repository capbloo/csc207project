package view;

import entity.Board;
import entity.Move;
import entity.Piece;
import entity.PieceBuilder;
import entity.ChessButton;
import interface_adapter.make_move.MakeMoveController;
import interface_adapter.make_move.MakeMoveState;
import interface_adapter.make_move.MakeMoveViewModel;
import use_case.make_move.MakeMoveInputData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class BoardView extends JFrame implements ActionListener, PropertyChangeListener {

    private MakeMoveController makeMoveController;
    private MakeMoveViewModel makeMoveViewModel;
    private ChessButton previousMove;
    private PieceBuilder pieceBuilder;

    public BoardView(Board board, MakeMoveController makeMoveController, MakeMoveViewModel makeMoveViewModel) {
        this.makeMoveController = makeMoveController;
        this.makeMoveViewModel = makeMoveViewModel;
        makeMoveViewModel.addPropertyChangeListener(this);
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
                } else {
                    block.setBackground(Color.lightGray);
                    block.setOpaque(true);
                    block.setBorderPainted(false);

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
        if (clickedButton.isEmpty() && previousMove == null) {
        } else if (this.previousMove == null) {
            this.previousMove = clickedButton;
            System.out.println("button clicked");
        } else if (this.previousMove.getRow().equals(y) && this.previousMove.getCol().equals(x)) {
            this.previousMove = null;
            System.out.println("selection reset");
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
}



