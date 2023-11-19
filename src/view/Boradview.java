package view;

import entity.Board;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Boradview extends JPanel {

    public Boradview(JFrame application, Board board){
        JPanel pn = new JPanel();
        boolean iswhite = true;
        for (int x = 0; x<8; x++){
            for(int y = 0; y<8; y++) {

                JButton block = new JButton();
                application.add(block);
                block.setBounds(x*64,y*64,64,64);
                ArrayList<Integer> pos1 = new ArrayList<Integer>();
                pos1.add(x+1);
                pos1.add(y+1);
                if (!(board.getBoardstate().get(pos1) == null)){
                    String color = board.getBoardstate().get(pos1).getColor().substring(0,1);
                    block.setText(color + board.getBoardstate().get(pos1).toString());
                }
                if (iswhite){
                    block.setBackground(Color.lightGray);
                }else {
                    block.setBackground(Color.darkGray);
                }
                iswhite = !iswhite;
            }
            iswhite = !iswhite;
        }

        application.add(pn);
    }


}
