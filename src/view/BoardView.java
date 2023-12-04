package view;

import entity.Board;
import entity.Move;
import entity.Piece;
import entity.PieceBuilder;
import entity.ChessButton;
import interface_adapter.CheckGameEnds.CheckGameEndsController;
import interface_adapter.CheckGameEnds.CheckGameEndsState;
import interface_adapter.CheckGameEnds.CheckGameEndsViewModel;
import interface_adapter.Get_move.GetMoveController;
import interface_adapter.Get_move.GetMoveState;
import interface_adapter.Get_move.GetMoveViewModel;
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

    private CheckGameEndsViewModel checkGameEndsViewModel;

    private CheckGameEndsController checkGameEndsController;

    private GetMoveViewModel getMoveViewModel;

    private GetMoveController getMoveController;

    private ChessButton previousMove;
    private PieceBuilder pieceBuilder;
    private static HashMap<ArrayList<Integer>, ChessButton> buttonList = new HashMap<>();
    private final String usersColour;
    private Move apiMove;
    private volatile String turn = "white";

    public BoardView(Board board, MakeMoveController makeMoveController, MakeMoveViewModel makeMoveViewModel,
                     HighlightController highlightController, HighlightViewModel highlightViewModel,
                     CheckGameEndsController checkGameEndsController, CheckGameEndsViewModel checkGameEndsViewModel, String usersColour,
                     GetMoveViewModel getMoveViewModel, GetMoveController getMoveController) {
        this.usersColour = usersColour;
        this.makeMoveController = makeMoveController;
        this.makeMoveViewModel = makeMoveViewModel;
        this.highlightController = highlightController;
        this.highlightViewModel = highlightViewModel;
        this.checkGameEndsController = checkGameEndsController;
        this.checkGameEndsViewModel = checkGameEndsViewModel;
        this.getMoveViewModel = getMoveViewModel;
        this.getMoveController = getMoveController;
        makeMoveViewModel.addPropertyChangeListener(this);
        checkGameEndsViewModel.addPropertyChangeListener(this);
        getMoveViewModel.addPropertyChangeListener(this);
        //highlightViewModel.addPropertyChangeListener(this);
        pieceBuilder = new PieceBuilder();

        setBounds(1000, 1000, 1000, 1000);
        setLayout(new BorderLayout());
        JPanel pn = new JPanel(new GridLayout(8, 8));
        setBounds(800, 800, 800, 800);
        boolean iswhite = true;
        for (int y = 8; y > 0; y--) {
            for (int x = 0; x < 8; x++) {

                ChessButton block = new ChessButton();
                pn.add(block);
                block.setBounds(x * 64, y * 64, 64, 64);
                ArrayList<Integer> pos1 = new ArrayList<Integer>();
                pos1.add(x + 1);

              
                ArrayList<Integer> coord = new ArrayList<>();
                coord.add(x + 1);
                coord.add(y);
                buttonList.put(coord, block);

                pos1.add(y);
                block.setCoord(x + 1, y);

                block.addActionListener(this);
                if (!(board.getBoardstate().get(pos1) == null)) {
                    block.setText(board.getBoardstate().get(pos1).toString());
                    block.setHorizontalAlignment(SwingConstants.CENTER);
                    Font f = new Font("serif", Font.PLAIN, 60);
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
        bottomPanel.setBounds(1000, 800, 800, 800);
        leftPanel.setBounds(1000, 800, 800, 800);
        int[] numSideBar = {0, 1, 2, 3, 4, 5, 6, 7, 8};
        String[] letterSideBar = {"x", "h", "g", "f", "e", "d", "c", "b", "a"};
        int i = numSideBar.length - 1;
        Font labelFont = new Font("serif", Font.PLAIN, 20);
        while (i > 0) {
            JLabel bottomLabel = new JLabel(letterSideBar[i]);
            bottomLabel.setFont(labelFont);
            JLabel leftLabel = new JLabel(String.valueOf(numSideBar[i]));
            leftLabel.setFont(labelFont);
            bottomLabel.setHorizontalAlignment(JLabel.CENTER);
            bottomPanel.add(bottomLabel);
            leftPanel.add(leftLabel);
            i--;
        }
        add(leftPanel, BorderLayout.WEST);
        add(bottomPanel, BorderLayout.SOUTH);

        startGetMoveThread();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ChessButton clickedButton = (ChessButton) e.getSource();
        Integer x = clickedButton.getRow();
        Integer y = clickedButton.getCol();

        // first if condition ensures the player is only moving their own pieces
        if (clickedButton.getPieceColour() != null && (!clickedButton.getPieceColour().equals(usersColour)) && previousMove == null) {
        return;
        }
        else if (clickedButton.isEmpty() && previousMove == null) {
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

        } else if (this.previousMove.getRow().equals(x) && this.previousMove.getCol().equals(y)) {
            this.previousMove = null;
            System.out.println("selection reset");
            unhighlight(buttonList);
        } else {
            if (clickedButton.isHighlighted()) {
                Piece piece = pieceBuilder.create(previousMove.getPiece(), previousMove.getPieceColour());
                ArrayList<Integer> origin = new ArrayList<>();
                origin.add(previousMove.getRow());
                origin.add(previousMove.getCol());

                ArrayList<Integer> destination = new ArrayList<>();
                destination.add(x);
                destination.add(y);

                Move move = new Move(piece, origin, destination);

                if (isEnPassent(clickedButton)) {
                    move.setIsEnPassant();
                    ArrayList<Integer> sqaureToClear = new ArrayList<>();
                    sqaureToClear.add(destination.get(0));
                    // if we are white, remove the square BELOW the one we're moving to, if black, remove square ABOVE the one we're moving to
                    if (piece.getColor().equals("white")) {
                        sqaureToClear.add(destination.get(1) - 1);
                    }
                    else {
                        sqaureToClear.add(destination.get(1) + 1);
                    }
                    ChessButton pieceToClear = buttonList.get(sqaureToClear);
                    pieceToClear.clear();

                } else if (isPromotion(clickedButton)) {
                    move.setIsPromotion();

                } else if (isCastle(clickedButton)) {
                    move.setIsCastle();

                    // getting coordinates of where the rooks should be moved to and from
                    ArrayList<Integer> rookToClear = new ArrayList<>();
                    ArrayList<Integer> rookToAdd = new ArrayList<>();

                    // checking which direction castle is
                    if (destination.get(0) > origin.get(0)) {
                        // if it is a castle to the right, the rightmost rook is moved one space left of the king
                        rookToAdd.add(destination.get(0) - 1);
                        rookToClear.add(8);
                    } else {
                        // if it is a castle to the left, the leftmost rook is moved one space to the right of the king
                        rookToAdd.add(destination.get(0) + 1);
                        rookToClear.add(1);
                    }
                    // determining whether this is a white castle or black castle, depending on the y coordinate of the king
                    rookToClear.add(destination.get(1));
                    rookToAdd.add(destination.get(1));

                    ChessButton rookRemoved = buttonList.get(rookToClear);
                    ChessButton rookAdded = buttonList.get(rookToAdd);
                    // adding buttons to move class so the presenter can update them
                    move.setRookAdded(rookAdded);
                    move.setRookRemoved(rookRemoved);

                }
                try{
                    unhighlight(buttonList);
                    makeMoveController.execute(move, clickedButton);
                }finally {
                    checkGameEndsController.execute(move);
                    flipTurn();
                }
            } else {
                unhighlight(buttonList);
                this.previousMove = null;
            }
        }
    }

    private void startGetMoveThread() {
        Thread getMoves = new Thread(()-> {
            while (true) {
                if (!usersColour.equals(turn)) {
                    getMoveController.execute();
                    checkGameEndsController.execute(apiMove);
                    flipTurn();
                }
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        getMoves.start();
    }

    private void flipTurn() {
        if (turn.equals("white")) {
            turn = "black";
        } else {
            turn = "white";
        }
    }

    private boolean isEnPassent(ChessButton clickedButton) {
        System.out.println(previousMove.getPiece());
        if (previousMove.getPiece().equals("Pawn")) {
            // return true if the pawn is moving diagonally and not taking over a piece, ie en passent
            return clickedButton.getPiece() == null && !clickedButton.getRow().equals(previousMove.getRow());
        }
        return false;
    }

    private boolean isPromotion(ChessButton clickedButton) {
        if (previousMove.getPiece().equals("Pawn")) {
            return clickedButton.getCol().equals(8) || clickedButton.getCol().equals(1);
        }
        return false;
    }

    public boolean isCastle(ChessButton clickedButton) {
        if (previousMove.getPiece().equals("King")) {
            return Math.abs(previousMove.getRow() - clickedButton.getRow()) > 1;
        }
        return false;
    }

    public void propertyChange(PropertyChangeEvent e) {
            if (e.getPropertyName().equals("CheckGameEnds")) {
                CheckGameEndsState state = (CheckGameEndsState) e.getNewValue();
                String color = "";
                if (state.getiswhite()) {
                    color = "White";
                } else {
                    color = "Black";
                }
                if (state.getwin()) {
                    JOptionPane.showMessageDialog(this, color + " win!");
                } else if (state.getstale()) {
                    JOptionPane.showMessageDialog(this, "It's a tie");
                }
                System.out.println("not end");
            } else  if (e.getPropertyName().equals(("MakeMove"))){
                MakeMoveState state = (MakeMoveState) e.getNewValue();
                ChessButton clickedButton = state.getClickedButton();
                Piece pieceMoving = state.getMove().getPieceMoving();
                Move move = state.getMove();
                Font f = new Font("serif", Font.PLAIN, 60);
                String pieceSymbol = pieceMoving.toString();

                if (move.getIsPromotion()) {
                    Promotion(clickedButton, pieceMoving);
                } else if (move.getIsCastle()) {
                    Castle(clickedButton, move, f);
                } else {
                    clickedButton.setText(pieceSymbol);
                    clickedButton.setFont(f);
                    clickedButton.setPieceColour(pieceMoving.getColor());
                    clickedButton.setPiece(pieceMoving.symbolToString());
                }
                previousMove.clear();
                this.previousMove = null;
            } else {
                GetMoveState state = (GetMoveState) e.getNewValue();
                apiMove = state.getMove();
                Piece movingPiece = state.getMove().getPieceMoving();
                buttonList.get(apiMove.getDestination()).setText(apiMove.getPieceMoving().toString());
                Font f = new Font("serif", Font.PLAIN, 60);
                buttonList.get(apiMove.getDestination()).setFont(f);
                // check if is promotion
                if (apiMove.getIsPromotion()){
                    buttonList.get(apiMove.getDestination()).setText(apiMove.getPiecePromotedTo().toString());
                }
                // check if castle
                else if (apiMove.getIsCastle()) {
                    Castle(buttonList.get(apiMove.getDestination()), apiMove, f);
                }
                buttonList.get(apiMove.getOrigin()).clear();
            }

    }

    public void highlight(HashMap<ArrayList<Integer>, ChessButton> buttonsList) {
        for (ChessButton button : buttonsList.values()) {
            if (button.isHighlighted()) {
                button.setBackground(new Color(240,226,182));
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
    public void Castle(ChessButton clickedButton, Move move, Font f) {
        ChessButton rookRemoved = move.getRookRemoved();
        Piece pieceMoving = move.getPieceMoving();
        String pieceSymbol = pieceMoving.toString();
        rookRemoved.clear();
        clickedButton.setText(pieceSymbol);
        ChessButton rookAdded = move.getRookAdded();
        if (pieceMoving.getColor().equals("white")) {
            rookAdded.setText("♖");
        }
        else {
            rookAdded.setText("♜");
        }
        rookAdded.setFont(f);
        rookAdded.setPieceColour(pieceMoving.getColor());
        rookAdded.setPiece("Rook");
        clickedButton.setFont(f);
        clickedButton.setPieceColour(pieceMoving.getColor());
        clickedButton.setPiece(pieceMoving.symbolToString());
    }

    public void Promotion(ChessButton clickedButton, Piece pieceMoving) {
        PieceBuilder builder = new PieceBuilder();
        pieceMoving = builder.create("Queen", pieceMoving.getColor());
        if (pieceMoving.getColor().equals("white")) {
            clickedButton.setText("♕");
        }
        else {
            clickedButton.setText("♛");
        }
        clickedButton.setPieceColour(pieceMoving.getColor());
        clickedButton.setPiece(pieceMoving.symbolToString());
    }
}



