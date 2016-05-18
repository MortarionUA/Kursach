package ip4323.hommClass;

/**
 * Created by dima_ on 09.05.2016.
 */

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by dima_ on 09.05.2016.
 */
class BattleField extends Component implements Runnable, ActionListener, MouseListener, MouseMotionListener {


    private int flag = 0;

    private static final int tW = 64;
    private static final int tH = 64;

    private static final UnitImg map[][] =
            {
                    {
                            UnitImg.NT_SOLD1, UnitImg.NT_ARCH1, UnitImg.NT_ELEM1
                    },
                    {
                            UnitImg.NT_SOLD2, UnitImg.NT_ARCH2, UnitImg.NT_ELEM2
                    },
                    {
                            UnitImg.BL_SOLD1, UnitImg.BL_ARCH1, UnitImg.BL_ELEM1
                    }
            };

    private int[][] convMap2;
    private Tile map2[][] = new Tile[20][10];

    private Image unitset;
    private Image tileset;
    private Image factionset;

    private Thread t;
    private Graphics grph;
    private BufferedImage grphImage;

    private int lastX = 0;
    private int lastY = 0;

    protected int w, h;

    private ArrayList<Unit> units = new ArrayList<>();

    protected Frame f;

    public BattleField(Frame frame, int width, int height) {
        w = width;
        h = height;
        f = frame;

        enableEvents(AWTEvent.MOUSE_EVENT_MASK | AWTEvent.MOUSE_MOTION_EVENT_MASK);
        addMouseListener(this);
        addMouseMotionListener(this);

        unitset = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Resource/human units.png"));
        tileset = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Resource/tileset.png"));
        factionset = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Resource/fractions.png"));

        BattleMap bMap = new BattleMap();
        bMap.generateField();
        convMap2 = bMap.getFieldType();

        bmInit();
    }

    public void convertBMap(int oldMap[][]) {
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
                }
            }
        }
    }

    public void bmInit() {
        t = new Thread(this);
        t.start();
    }

    public void run() {
        repaint();
    }

    public void paint(Graphics g) {
        convertBMap(convMap2);
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
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
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

    protected void drawCircle(Graphics g, Factions f, int x, int y) {
        int mx = f.ordinal() % 4;
        int my = f.ordinal() / 4;
        g.drawImage(factionset, x, y, x + tW, y + tH, mx * tW, my * tH, mx * tW + tW, my * tH + tH, this);
    }

    public Dimension getPreferredSize() {
        return new Dimension(800, 600);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Graphics g = getGraphics();
        int inX, inY;
        inX = ((e.getX() / 64) * 64);
        inY = ((e.getY() / 64) * 64);
        drawCircle(g, Factions.BL_CIRCL, inX, inY);

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        Graphics g = getGraphics();
        int inX, inY;
        inX = ((e.getX() / 64) * 64);
        inY = ((e.getY() / 64) * 64);
        if ((lastX == inX / 64) && (lastY == inY / 64)) {
            drawCircle(g, Factions.RD_CIRCL, inX, inY);
        } else {
            lastX = inX / 64;
            lastY = inY / 64;
            repaint();
            drawCircle(g, Factions.RD_CIRCL, inX, inY);
        }
    }

    public void combatPrepare(Hero player1, Hero player2) {
        BattleMap bmap = new BattleMap();
        bmap.generateField();
        for (int i = 0; i < player1.getArmy().length; i++) {
            bmap.getFieldUnit()[1][i * 2] = player1.getArmy()[i];
            player1.getArmy()[i].setPosx(1);
            player1.getArmy()[i].setPosx(i * 2);
        }
        for (int i = 0; i < player2.getArmy().length; i++) {
            bmap.getFieldUnit()[18][i * 2] = player2.getArmy()[i];
            player2.getArmy()[i].setPosx(18);
            player2.getArmy()[i].setPosx(i * 2);
        }
        for (int i = 0; i < player1.getArmy().length; i++) {
            int tmpdmg = player1.getArmy()[i].getDmg() + player1.getDmgBonus();
            int tmpdef = player1.getArmy()[i].getDef() + player1.getDefBonus();
            int tmphp = player1.getArmy()[i].getHp() + player1.getHpBonus();
            int tmpovhp = player1.getArmy()[i].getOvhp() + player1.getHpBonus();
            player1.getArmy()[i].setDmg(tmpdmg);
            player1.getArmy()[i].setDef(tmpdef);
            player1.getArmy()[i].setHp(tmphp);
            player1.getArmy()[i].setOvhp(tmpovhp);
            player1.getArmy()[i].setPlayerHave(0);
        }
        for (int i = 0; i < player2.getArmy().length; i++) {
            int tmpdmg = player2.getArmy()[i].getDmg() + player2.getDmgBonus();
            int tmpdef = player2.getArmy()[i].getDef() + player2.getDefBonus();
            int tmphp = player2.getArmy()[i].getHp() + player2.getHpBonus();
            int tmpovhp = player2.getArmy()[i].getOvhp() + player2.getHpBonus();
            player2.getArmy()[i].setDmg(tmpdmg);
            player2.getArmy()[i].setDef(tmpdef);
            player2.getArmy()[i].setHp(tmphp);
            player2.getArmy()[i].setOvhp(tmpovhp);
            player2.getArmy()[i].setPlayerHave(1);
        }
        int tmplen = 0;
        for (int i = 0; i < player1.getArmy().length; i++) {
            units.add(player1.getArmy()[i]);
            tmplen++;
        }
        for (int i = 0; i < player2.getArmy().length; i++) {
            units.add(player2.getArmy()[i]);
            tmplen++;
        }
        Collections.sort(units, new CompareUnits());
    }

    public void turn() {
        for (int i=0; i < units.size(); i++) {

        }
    }

    class CompareUnits implements Comparator<Unit> {

        public int compare(Unit o1, Unit o2) {
            if (o1.getSpeed() > o2.getSpeed()) return 1;
            else if (o1.getSpeed() == o2.getSpeed()) return 0;
            else return -1;
        }
    }
}
