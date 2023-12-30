package main.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.Random;
import java.util.StringTokenizer;


public class FrameManageOctopus extends JFrame implements ActionListener, KeyListener{
    private static final long serialVersionUID = 2923975805665801740L;
    private static final int NUM_OCT = 10;
    LinkedList<int[]> playerOctopus;// contains the inserted optopus, is for
    // building the frameBattle
    boolean finish = false;
    int insertOct = 0;
    int[] counterOct = { 1, 2, 3, 4 };
    Map map;
    UIManagePanel choosePan;
    UIMapPanel mapPanel;

    public FrameManageOctopus() {
        super("Octopus Battleship");
        map = new Map();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(screenSize.width, screenSize.height);
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.addKeyListener(this);
        //Icon of the game
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/res/images/icon.png")));
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
        this.setLocation(x, y);
        //Background of the game
        UIJPanelBG container = new UIJPanelBG(
                Toolkit.getDefaultToolkit().createImage(getClass().getResource("/res/images/ocean.png")));
        mapPanel = new UIMapPanel("manage");
        container.add(mapPanel);
        choosePan = new UIManagePanel();
        container.add(choosePan);
        mapPanel.setBounds((screenSize.width - 600) / 2, (screenSize.height - 700) / 2, 535, 592);
        choosePan.setBounds(mapPanel.getX() + mapPanel.getWidth() + 50, 0, 350, 820);
        // Internal panel containing the octopus to be placed.
        this.add(container);
        for (int i = 0; i < mapPanel.button.length; i++) {
            for (int j = 0; j < mapPanel.button[i].length; j++) {
                mapPanel.button[i][j].addActionListener(this);
                mapPanel.button[i][j].setActionCommand("" + i + " " + j);
            }
        }
        choosePan.random.addActionListener(this);
        choosePan.reset.addActionListener(this);
        choosePan.fight.addActionListener(this);
        playerOctopus = new LinkedList<int[]>();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource();
        String test = source.getText();
        // RESET
        if (test.equals("reset")) {
            reset();
        }

        // RANDOM
        else if (test.equals("random")) {
            random();
        }

        // fight
        else if (test.equals("fight")) {
            fight();

        } else {
            if (finish) {
                return;
            }
            StringTokenizer st = new StringTokenizer(source.getActionCommand(), " ");
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int ship = -1;
            int dim = 0;
            int dir;
            for (int i = 0; i < choosePan.octopus.length; i++) {
                if (choosePan.octopus[i].isSelected())
                    ship = i;
            }
            switch (ship) {
                case 0:
                    dim = 4;
                    break;
                case 1:
                    dim = 3;
                    break;
                case 2:
                    dim = 2;
                    break;
                case 3:
                    dim = 1;
                    break;
            }
            if (choosePan.direction[0].isSelected())// 0=horizontal 1=vertical
                dir = 0;
            else
                dir = 1;
            boolean insert = map.insert(x, y, dim, dir);
            if (insert) {
                
                // increment the number of inserted octopus
                insertOct++;
                // decrease the number of inserted octopus
                counterOct[ship]--;
                choosePan.counterLabel[ship].setText("" + counterOct[ship]);
                
                // disable octopus if all are entered
                if (choosePan.counterLabel[ship].getText().equals("0")) {
                    choosePan.octopus[ship].setEnabled(false);
                    for (int i = 0; i < choosePan.octopus.length; i++) {
                        if (choosePan.octopus[i].isEnabled() && !choosePan.octopus[i].isSelected()) {
                            choosePan.octopus[i].setSelected(true);
                            break;
                        }
                    }
                }
                // check if we have entered all optopus (10)
                if (insertOct == NUM_OCT) {
                    finish = true;
                    choosePan.direction[0].setEnabled(false);
                    choosePan.direction[1].setEnabled(false);
                    choosePan.fight.setEnabled(true);
                }
                int[] dati = { x, y, dim, dir };
                playerOctopus.add(dati);
                mapPanel.drawOct(dati);
            }
        }
        this.requestFocusInWindow();
    }



    private void random() {
        if (insertOct == NUM_OCT) {
            reset();
        }
        Random r = new Random();
        int[] dati = new int[4];
        for (int i = 0; i < counterOct.length; i++) {
            for (int j = 0; j < counterOct[i]; j++) {
                dati = map.insertRandom(r, counterOct.length - i);
                playerOctopus.add(dati);
                mapPanel.drawOct(dati);
            }
        }
        insertOct = NUM_OCT;
        finish = true;
        choosePan.fight.setEnabled(true);
        for (int i = 0; i < choosePan.octopus.length; i++) {
            choosePan.octopus[i].setEnabled(false);
        }
        choosePan.direction[0].setEnabled(false);
        choosePan.direction[1].setEnabled(false);
        for (int i = 0; i < counterOct.length; i++) {
            counterOct[i] = 0;
            choosePan.counterLabel[i].setText("0");
        }
        choosePan.octopus[0].setSelected(true);

    }

    private void reset() {
        map = new Map();
        playerOctopus = new LinkedList<int[]>();
        for (int i = 0; i < Map.DIM_MAP; i++) {
            for (int j = 0; j < Map.DIM_MAP; j++) {
                mapPanel.button[i][j].setEnabled(true);
            }
        }
        finish = false;
        choosePan.fight.setEnabled(false);
        for (int i = 0; i < choosePan.octopus.length; i++) {
            choosePan.octopus[i].setEnabled(true);
        }
        choosePan.direction[0].setEnabled(true);
        choosePan.direction[1].setEnabled(true);
        for (int i = 0; i < counterOct.length; i++) {
            counterOct[i] = i + 1;
            choosePan.counterLabel[i].setText("" + (i + 1));
        }
        choosePan.octopus[0].setSelected(true);
        insertOct = 0;
    }

    private void fight() {
        FrameBattle battle = new FrameBattle(playerOctopus, map);
        battle.frame.setVisible(true);
        this.setVisible(false);
        

    }

    @Override
    public void keyPressed(KeyEvent arg0) {
        char s = Character.toLowerCase(arg0.getKeyChar());
        int tasto = arg0.getKeyCode();
        if (s == 'g') {

            random();
            fight();
        } else {
            if (s == 'r') {
                random();
            } else {
                if (tasto == KeyEvent.VK_DELETE || tasto == KeyEvent.VK_BACK_SPACE) {
                    reset();
                } else {
                    if (tasto == KeyEvent.VK_ESCAPE) {
                        System.exit(0);
                    }
                }
                if (tasto == KeyEvent.VK_ENTER) {
                    if (finish) {
                        fight();
                    }
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent arg0) {

    }

    @Override
    public void keyTyped(KeyEvent arg0) {

    }
}
