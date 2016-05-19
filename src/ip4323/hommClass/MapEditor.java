package ip4323.hommClass;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.*;

/**
 * Created by dima_ on 10.05.2016.
 */
public class MapEditor extends Component implements Runnable, ActionListener, MouseListener, MouseMotionListener, KeyListener {

    private static final int tW = 64;
    private static final int tH = 64;

    private Image tileset;

    private Thread t;
    private Graphics grph;
    private BufferedImage grphImage;

    private Map workingMap;

    private int flag = 0;

    private Tile map2[][] = new Tile[20][20];

    protected int w, h;

    protected Frame f;

    public MapEditor(Frame frame, int width, int height) {
        w = width;
        h = height;
        f = frame;

        enableEvents(AWTEvent.MOUSE_EVENT_MASK | AWTEvent.MOUSE_MOTION_EVENT_MASK | AWTEvent.KEY_EVENT_MASK);
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
        setFocusable(true);

        tileset = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Resource/tileset.png"));

        newMap();

        mInit();
    }

    public void mInit() {
        t = new Thread(this);
        t.start();
    }

    public void run() {
        repaint();
    }

    public void convertMap() {
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
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
            }
        }
        g.drawImage(grphImage, 0, 0, this);
    }

    protected void drawTile(Graphics g, Tile t, int x, int y) {
        int mx = t.ordinal() % 8;
        int my = t.ordinal() / 8;
        g.drawImage(tileset, x, y, x + tW, y + tH, mx * tW, my * tH, mx * tW + tW, my * tH + tH, this);
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

    public void newMap() {
        workingMap = new Map();
        workingMap.newMap();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int tempX = e.getX()/64;
        int tempY = e.getY()/64;
        if (flag == 10) {
            if(e.getModifiers()==InputEvent.BUTTON3_MASK) {
                workingMap.delTown(tempX, tempY);
            } else {
                workingMap.addTown(tempX, tempY);
            }
        }
        else {
            workingMap.setMapTerr(tempX, tempY, flag);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        repaint();

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int tempX = e.getX()/64;
        int tempY = e.getY()/64;
        workingMap.setMapTerr(tempX, tempY, flag);
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int i = e.getKeyCode();
        if (i == KeyEvent.VK_0) {
            flag = 0;
        } else if (i == KeyEvent.VK_1) {
            flag = 1;
        } else if (i == KeyEvent.VK_2) {
            flag = 2;
        } else if (i == KeyEvent.VK_3) {
            flag = 3;
        } else if (i == KeyEvent.VK_4) {
            flag = 4;
        } else if (i == KeyEvent.VK_5) {
            flag = 5;
        } else if (i == KeyEvent.VK_6) {
            flag = 6;
        } else if (i == KeyEvent.VK_7) {
            flag = 7;
        } else if (i == KeyEvent.VK_8) {
            flag = 8;
        } else if (i == KeyEvent.VK_9) {
            flag = 9;
        } else if (i == KeyEvent.VK_F2) {
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
        } else if ((i == KeyEvent.VK_N) && e.isControlDown()) {
            newMap();
            repaint();
        } else if ((i == KeyEvent.VK_K) && e.isControlDown()) {
            flag = 10;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
