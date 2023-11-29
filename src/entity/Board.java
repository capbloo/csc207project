package entity;

import javax.naming.PartialResultException;
import java.util.*;

public class Board {
    private HashMap<ArrayList<Integer>, Piece> boardstate;

    private HashMap<ArrayList<Integer>, Boolean> highlights;

    private Move lastmove;

//    private final String usersColour;

    public Board(){
        this.lastmove = null;
        this.boardstate = new HashMap<>();
        this.highlights = new HashMap<>();
        PieceBuilder builder = new PieceBuilder();
        ArrayList<String> pieces = new ArrayList<String>();
        pieces.add("Rook");
        pieces.add("Knight");
        pieces.add("Bishop");
        pieces.add("Queen");
        pieces.add("King");
        pieces.add("Bishop");
        pieces.add("Knight");
        pieces.add("Rook");
        int i = 0;
        while (i < 8){
            ArrayList<Integer> pos1 = new ArrayList<Integer>();
            pos1.add(i+1);
            pos1.add(1);
            ArrayList<Integer> pos2 = new ArrayList<Integer>();
            pos2.add(i+1);
            pos2.add(8);
            ArrayList<Integer> pos3 = new ArrayList<Integer>();
            pos3.add(i+1);
            pos3.add(2);
            ArrayList<Integer> pos4 = new ArrayList<Integer>();
            pos4.add(i+1);
            pos4.add(7);
            boardstate.put(pos1, builder.create(pieces.get(i),"white"));
            boardstate.put(pos2, builder.create(pieces.get(i),"black"));
            boardstate.put(pos3, builder.create("Pawn","white"));
            boardstate.put(pos4, builder.create("Pawn","black"));
            int j = 3;
            while (j <= 6){
                ArrayList<Integer> pos5 = new ArrayList<Integer>();
                pos5.add(i+1);
                pos5.add(j);
                boardstate.put(pos5, null);
                j++;
            }
            i++;
        }


    }
    public void setLastmove(Move lastmove) {
        this.lastmove = lastmove;
    }

    public void setBoardstate(HashMap<ArrayList<Integer>, Piece> boardstates){
        this.boardstate = boardstates;
    }

    public HashMap<ArrayList<Integer>, Piece> getBoardstate() {
        return boardstate;
    }
    public Move getLastmove() {
        return lastmove;
    }

    public HashMap<ArrayList<Integer>, Boolean> getHighLights() {
        return highlights;
    }

    public void setHighLights(ArrayList<Integer> pos, boolean ishigh) {
        this.highlights.put(pos, ishigh);
    }

    public void makeMove(Move move){
        PieceBuilder builder = new PieceBuilder();
        ArrayList<Integer> org = move.getOrigin();
        ArrayList<Integer> des = move.getDestination();
        Piece piece = move.getPieceMoving();
        // checking if we need to mark a piece as "moved"

        piece.pieceMove();

        // checking for en passant, promotion, or castle
        if (move.getIsPieceCaptured()){
            ArrayList<Integer> capture = move.getPieceCaptureLocation();
            boardstate.remove(capture);
            boardstate.put(des, piece);
        }
        else if (move.getIsEnPassant()) {
            // move the pawn
            boardstate.put(des, piece);
            // get the coordinates of the piece being captured by the en passant
            ArrayList<Integer> coordToRemove = new ArrayList<>();
            coordToRemove.add(des.get(0));
            if (piece.getColor().equals("white")) {
                coordToRemove.add(des.get(1) - 1);
            } else {
                coordToRemove.add(des.get(1) + 1);
            }
            // remove the piece captured by the en passant from the boardState
            boardstate.remove(coordToRemove);
        } else if (move.getIsPromotion()) {
            Piece queen = builder.create("Queen", piece.getColor());
            boardstate.put(des, queen);
        } else if (move.getIsCastle()){
            boardstate.put(des, piece);
            // adding rook to left/right of king
            ArrayList<Integer> coordOfRookAdded = new ArrayList<>();
            coordOfRookAdded.add(move.getRookAdded().getRow());
            coordOfRookAdded.add(move.getRookAdded().getCol());
            Piece rook = builder.create("Rook", piece.getColor());
            boardstate.put(coordOfRookAdded, rook);

            // removing rook from its current position
            ArrayList<Integer> coordOfRookRemoved = new ArrayList<>();
            coordOfRookRemoved.add(move.getRookRemoved().getRow());
            coordOfRookRemoved.add(move.getRookRemoved().getCol());
            boardstate.remove(coordOfRookRemoved);
        }
        else {
            boardstate.put(des, piece);
        }
        boardstate.remove(org);
        lastmove = move;
    }
        
  
  
  
    public ArrayList<Integer> coor(int x, int y) {
        ArrayList<Integer> co = new ArrayList<Integer>();
        co.add(x);
        co.add(y);
        return co;
    }

    private Board cloneBoard() {
        Board newb = new Board();
        newb.setLastmove(lastmove);
        newb.setBoardstate((HashMap<ArrayList<Integer>, Piece>)boardstate.clone());
        return newb;
    }
    public boolean isCheckMate(String colorToPlay){
        if (!isCheckHelper(colorToPlay)){
            return false;
        }
        ArrayList<Move> legalmoves = new ArrayList<>();
        for (ArrayList<Integer> kys : boardstate.keySet()){
            if (boardstate.get(kys) != null){
                if (Objects.equals(boardstate.get(kys).getColor(), colorToPlay)){
                    Move[] moves = boardstate.get(kys).getValidMoves(kys, boardstate, lastmove);
                    legalmoves.addAll(Arrays.asList(moves));
                }
            }
        }
        return legalmoves.isEmpty();
    }

    private boolean isCheckHelper(String colorToPlay){
        boolean ischeck = false;
        ArrayList<Integer> posofking = new ArrayList<>();
        ArrayList<Move> legalmoves = new ArrayList<>();
        for (ArrayList<Integer> kys : boardstate.keySet()){
            if (boardstate.get(kys) != null) {
                if (boardstate.get(kys).getColor().equals(colorToPlay)) {
                    if (boardstate.get(kys) instanceof King) {
                        posofking = kys;
                    }
                } else {
                    Move[] moves = boardstate.get(kys).getValidMoves(kys, boardstate, lastmove);
                    legalmoves.addAll(Arrays.asList(moves));
                }
            }
        }
        for (Move mo : legalmoves){
            if (mo.getDestination().equals(posofking)){
                ischeck = true;
            }
        }
        return ischeck;
    }

    public boolean isValidMove(Move move){
        ArrayList<Integer> org = move.getOrigin();
        Move[] legalmoves = boardstate.get(org).getValidMoves(org, boardstate, lastmove);
        for (Move m : legalmoves){
            if (m.equals(move)){
                return true;
            }
        }
        return false;
    }

    public boolean isStaleMate(String colorToPlay){
        boolean stalemate = false;
        ArrayList<Move> legalmoves = new ArrayList<>();
        for (ArrayList<Integer> kys : boardstate.keySet()) {
            if (boardstate.get(kys) != null){
                if (boardstate.get(kys).getColor().equals(colorToPlay)){
                    Move[] moves = boardstate.get(kys).getValidMoves(kys, boardstate, lastmove);
                    legalmoves.addAll(Arrays.asList(moves));
                }
            }
        }

        if (!isCheckHelper(colorToPlay) && legalmoves.isEmpty()) {
            stalemate = true;
        }
        return stalemate;
    }
}
