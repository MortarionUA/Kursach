package ip4323.hommClass;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * Created by dima_ on 22.05.2016.
 */
public class GlobalMap extends Component implements Runnable, KeyListener, MouseListener {

    private static final int tW = 64;
    private static final int tH = 64;

    private Image unitset;
    private Image tileset;
    private Image highlightset;
    private Image factionset;

    private Thread t;
    private Graphics grph;
    private BufferedImage grphImage;

    private Map workingMap;
    private Tile map2[][] = new Tile[20][20];
    private Factions map3[][] = new Factions[20][20];

    int posX, posY;

    private int playerFlag;
    private int dayFlag;

    private Player player1;
    private Player player2;
    private Player player0;

    protected int w, h;

    protected Frame f;

    public GlobalMap(Frame frame, int width, int height) throws IOException, ClassNotFoundException {
        w = width;
        h = height;
        f = frame;

        enableEvents(AWTEvent.MOUSE_EVENT_MASK | AWTEvent.KEY_EVENT_MASK);
        addMouseListener(this);
        addKeyListener(this);
        setFocusable(true);

        tileset = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Resource/tileset.png"));
        factionset = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Resource/fractions.png"));
        highlightset = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Resource/highlight.png"));
        unitset = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Resource/human units.png"));

        mInit();
    }

    public void mInit() throws IOException, ClassNotFoundException {
        load();
        playerFlag = 1;
        dayFlag = 0;
        t = new Thread(this);
        t.start();
    }

    public void run() {
        repaint();
    }

    public void convertMap() {
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                map3[i][j] = Factions.T1;
                switch (workingMap.getMapTerr()[i][j]) {
                    case 0:
                        map2[i][j] = Tile.GRASS1;
                        break;
                    case 1:
                        map2[i][j] = Tile.GRASS2;
                        break;
                    case 2:
                        map2[i][j] = Tile.GRASS3;
                        break;
                    case 3:
                        map2[i][j] = Tile.GRASS4;
                        break;
                    case 4:
                        map2[i][j] = Tile.GRASS5;
                        break;
                    case 5:
                        map2[i][j] = Tile.GRASS6;
                        break;
                    case 6:
                        map2[i][j] = Tile.GRASS7;
                        break;
                    case 7:
                        map2[i][j] = Tile.HILL1;
                        break;
                    case 8:
                        map2[i][j] = Tile.HILL2;
                        break;
                    case 9:
                        map2[i][j] = Tile.MOUNT;
                        break;
                }
            }
        }
        if (workingMap.getMapTown() != null) {
            for(Town town : workingMap.getMapTown()) {
                int tempX = town.getPosX();
                int tempY = town.getPosY();
                map2[tempX][tempY] = Tile.CASTLE_DOWN;
                map2[tempX][tempY-1] = Tile.CASTLE_UP;
                switch (town.getFaction()) {
                    case 1: {
                        map3[tempX][tempY] = Factions.BL_FLAG;
                        break;
                    }
                    case 2: {
                        map3[tempX][tempY] = Factions.RD_FLAG;
                        break;
                    }
                    default: {
                        map3[tempX][tempY] = Factions.T1;
                        break;
                    }
                }
            }
        }
    }

    public void paint(Graphics g) {
        convertMap();
        if (grphImage == null) {
            grphImage = (BufferedImage) createImage(1280, 1280);
        }
        grph = grphImage.getGraphics();
        grph.setColor(Color.WHITE);
        grph.fillRect(0, 0, getWidth(), getHeight());
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                drawTile(grph, map2[i][j], i * tW, j * tH);
                drawFaction(grph, map3[i][j], i * tW, j * tH);
            }
        }
        g.drawImage(grphImage, 0, 0, this);
    }

    protected void drawFaction(Graphics g, Factions t, int x, int y) {
        int mx = t.ordinal() % 4;
        int my = t.ordinal() / 4;
        g.drawImage(factionset, x, y, x + tW, y + tH, mx * tW, my * tH, mx * tW + tW, my * tH + tH, this);
    }

    protected void drawTile(Graphics g, Tile t, int x, int y) {
        int mx = t.ordinal() % 8;
        int my = t.ordinal() / 8;
        g.drawImage(tileset, x, y, x + tW, y + tH, mx * tW, my * tH, mx * tW + tW, my * tH + tH, this);
    }

    protected void drawUnit(Graphics g, UnitImg t, int x, int y) {
        int mx = t.ordinal() % 16;
        int my = t.ordinal() / 16;
        g.drawImage(unitset, x, y, x + tW, y + tH, mx * tW, my * tH, mx * tW + tW, my * tH + tH, this);
    }

    public void save() throws IOException {
        FileOutputStream fos = new FileOutputStream("temp1.out");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(workingMap);
        oos.flush();
        oos.close();
    }

    public void load() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream("temp1.out");
        ObjectInputStream ois = new ObjectInputStream(fis);
        workingMap = (Map) ois.readObject();
        repaint();
    }

    public void turn() {
        playerFlag++;
        dayFlag++;
        if (playerFlag > 2) {
            playerFlag = 1;
            for(int i=0; i<workingMap.getMapTown().size(); i++) {
                workingMap.getMapTown().get(i).setBuildFlag(true);
            }
        }
        if (dayFlag >= 14) {
            dayFlag = 0;
            for(int i=0; i<workingMap.getMapTown().size(); i++) {
                for(int j=0; j<5; j++) {
                    workingMap.getMapTown().get(i).setBuyFlag(true, j);
                }
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int i = e.getKeyCode();
        if (i == KeyEvent.VK_F2) {
            try {
                save();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } else if (i == KeyEvent.VK_F3) {
            try {
                load();
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }
        } else if (i == KeyEvent.VK_ENTER) {
            turn();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        posX = e.getX()/64;
        posY = e.getY()/64;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if ((workingMap.findTown(posX, posY) != Integer.MAX_VALUE) && (playerFlag == workingMap.getMapTown().get(workingMap.findTown(posX, posY)).getFaction())) {
            TownFieldWindow tf = new TownFieldWindow("Town", workingMap.getMapTown().get(workingMap.findTown(posX, posY)));
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
