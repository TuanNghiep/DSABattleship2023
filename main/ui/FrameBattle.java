package battleship_main.ui;



import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.LinkedList;
import java.util.StringTokenizer;

//Battle screen
public class FrameBattle implements ActionListener, KeyListener, Serializable {
    private JLabel backLabel;
    private JLabel saveLabel;
    
    UIMapPanel playerPanel = new UIMapPanel("player");
    UIMapPanel cpuPanel = new UIMapPanel("cpu");
    JFrame frame = new JFrame("Battleship Map");
    JPanel comandPanel = new JPanel();
    Cursor cursorDefault = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
    UIJPanelBG panel = new UIJPanelBG(
            Toolkit.getDefaultToolkit().createImage(getClass().getResource("/res/images/ocean.png")));
    Report rep;
    Computer cpu;
    static Mappa cpuMap;
    static Mappa playerMap;
    int numOctPlayer = 10;
    int numOctCPU = 10;
    StringBuilder sb = new StringBuilder();
    boolean b = true;

    UIStatPanel statPlayer;
    UIStatPanel statCPU;
    JPanel targetPanel = new JPanel(null);
    UIJPanelBG target = new UIJPanelBG(
            Toolkit.getDefaultToolkit().createImage(getClass().getResource("/res/images/target.png")));
    ImageIcon wreck = new ImageIcon(getClass().getResource("/res/images/wreck.gif"));
    Cursor cursor = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
    Timer timer;
    boolean defeatCPU;

    int offsetX;
    static LinkedList<int[]> playerOctopus;// contains the inserted optopus, is for
