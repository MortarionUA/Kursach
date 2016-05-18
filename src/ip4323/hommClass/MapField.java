package ip4323.hommClass;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;

/**
 * Created by dima_ on 09.05.2016.
 */

class MapField extends Component implements Runnable, ActionListener, MouseListener, MouseMotionListener, Serializable {

    private Player player1;
    private Player player2;
    private Player neutral;

    private static final int tW = 64;
    private static final int tH = 64;

    private int[][] mapTerr = new int[20][20];
    private Hero[][] mapHero = new Hero[20][20];

    private int[][] convMap2;
    private Tile map2[][] = new Tile[20][10];

    private int[][] convMap3;
    private UnitImg map[][] = new UnitImg[20][10];

    private Image unitset;
    private Image tileset;
    private Image factionset;

    private Thread t;
    private Graphics grph;
    private BufferedImage grphImage;

    private int lastX = 0;
    private int lastY = 0;

    protected int w, h;

    protected Frame f;

    public MapField(Frame frame, int width, int height) {
        w = width;
        h = height;
        f = frame;

        enableEvents(AWTEvent.MOUSE_EVENT_MASK | AWTEvent.MOUSE_MOTION_EVENT_MASK);
        addMouseListener(this);
        addMouseMotionListener(this);

        unitset = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Resource/human units.png"));
        tileset = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Resource/tileset.png"));
        factionset = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Resource/fractions.png"));

        Map map = new Map();
        convMap2 = map.getMapTerr();

        mInit();
    }

    public void convertMap(int oldMap[][]) {
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 10; j++) {
                switch (oldMap[i][j]) {
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
                    case 10:
                        map2[i][j] = Tile.CASTLE_DOWN;
                        map2[i][j - 1] = Tile.CASTLE_DOWN;
                        break;
                    case 11:
                        map2[i][j] = Tile.HOUSE;
                        break;
                }
            }
        }
    }

    public void convertHMap(Hero oldMap[][]) {

    }

    public void mInit() {
        t = new Thread(this);
        t.start();
    }

    public void run() {
        while (true) {
            repaint();
        }
    }

    public void paint(Graphics g) {
        convertMap(convMap2);
        if (grphImage == null) {
            grphImage = (BufferedImage) createImage(1280, 640);
        }
        grph = grphImage.getGraphics();
        grph.setColor(Color.WHITE);
        grph.fillRect(0, 0, getWidth(), getHeight());
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 10; j++) {
                drawTile(grph, map2[i][j], i * tW, j * tH);
            }
        }
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                drawUnit(grph, map[j][i], i * tW, j * tH);
            }
        }
        g.drawImage(grphImage, 0, 0, this);
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

    @Override
    public void actionPerformed (ActionEvent e){

    }

    @Override
    public void mouseClicked (MouseEvent e){

    }

    @Override
    public void mousePressed (MouseEvent e){

    }

    @Override
    public void mouseReleased (MouseEvent e){

    }

    @Override
    public void mouseEntered (MouseEvent e){

    }

    @Override
    public void mouseExited (MouseEvent e){

    }

    @Override
    public void mouseDragged (MouseEvent e){

    }

    @Override
    public void mouseMoved (MouseEvent e){

    }

}

