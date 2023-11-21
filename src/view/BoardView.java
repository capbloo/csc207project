package view;

import entity.Board;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BoardView extends JPanel {

    public BoardView(JFrame application, Board board) {
        JPanel pn = new JPanel();
        boolean iswhite = true;
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {

                JButton block = new JButton();
                application.add(block);
                block.setBounds(x * 80, y * 80, 80, 80);
                ArrayList<Integer> pos1 = new ArrayList<Integer>();
                pos1.add(x + 1);
                pos1.add(8 - y);
                if (!(board.getBoardstate().get(pos1) == null)) {
                    block.setText(board.getBoardstate().get(pos1).toString());
                    Font f = new Font("serif", Font.PLAIN, 30);
                    block.setFont(f);
                }
                if (iswhite) {
                    block.setBackground(Color.lightGray);
                    block.setOpaque(true);
                    block.setBorderPainted(false);
                } else {
                    block.setBackground(new Color(107, 142, 35));
                    block.setOpaque(true);
                    block.setBorderPainted(false);
                }
                if (!(board.getHighLights().get(pos1) == null)) {
                    if (board.getHighLights().get(pos1)) {
                        block.setBackground(Color.orange);
                        block.setOpaque(true);
                        block.setBorderPainted(true);
                    }
                }
                    iswhite = !iswhite;
                }
                iswhite = !iswhite;
            }

            application.add(pn);
        }


    }
