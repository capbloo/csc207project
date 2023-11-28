package entity;

import javax.swing.*;
import java.util.ArrayList;

public class ChessButton extends JButton {
    private Integer row;
    private Integer col;
    private String piece;
    private String pieceColour;
    private boolean isHighlighted;
    private String squareColor;

    public ChessButton() {
    }

    public void setCoord(int x, int y) {
        this.row = x;
        this.col = y;
    }

    public Integer getRow() {
        return this.row;
    }

    public Integer getCol() {
        return this.col;
    }

    public void clear() {
        this.pieceColour = null;
        this.piece = null;
        this.setText("");
    }

    public boolean isEmpty() {
        return piece == null;
    }

    public void setPiece(String piece) {
        this.piece = piece;
    }

    public String getPiece() {
        return this.piece;
    }

    public void setPieceColour(String colour) {
        this.pieceColour = colour;
    }

    public String getPieceColour() {
        return this.pieceColour;
    }

    public void setHighlight() {
        this.isHighlighted = true;
    }

    public void cancelHighlight() {
        this.isHighlighted = false;
    }

    public boolean isHighlighted() {
        return this.isHighlighted;
    }

    public void setSquareColour(String colour) {
        this.squareColor = colour;
    }

    public String getSquareColor() {
        return squareColor;
    }

}
