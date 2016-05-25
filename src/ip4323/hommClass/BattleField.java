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
import java.util.Date;

/**
 * Created by dima_ on 09.05.2016.
 */
class BattleField extends Component implements Runnable, ActionListener, MouseListener, MouseMotionListener {

    private int flag = 0;

    private static final int tW = 64;
    private static final int tH = 64;

    private int[][] convMap2;
    private Tile map2[][] = new Tile[20][10];
    private boolean[][] highMap = new boolean[20][10];

    private Image unitset;
    private Image tileset;
    private Image highlightset;
    private Image numberset;

    private Thread t;
    private Graphics grph;
    private BufferedImage grphImage;

    private int posX;
    private int posY;

    private Hero hero1;
    private Hero hero2;

    private UnitInfo ui = null;

    private int lastX = 0;
    private int lastY = 0;

    protected int w, h;

    private ArrayList<Unit> units = new ArrayList<>();
    private Numbers numbers[] = new Numbers[]{Numbers.zero, Numbers.one, Numbers.two, Numbers.three, Numbers.four, Numbers.five, Numbers.six, Numbers.seven, Numbers.eight, Numbers.nine};

    protected Frame f;

    public BattleField(Frame frame, int width, int height, Hero newHero1, Hero newHero2) {
        w = width;
        h = height;
        f = frame;
        hero1 = newHero1;
        hero2 = newHero2;

        enableEvents(AWTEvent.MOUSE_EVENT_MASK | AWTEvent.MOUSE_MOTION_EVENT_MASK);
        addMouseListener(this);
        addMouseMotionListener(this);

        unitset = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Resource/human units.png"));
        tileset = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Resource/tileset.png"));
        numberset = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Resource/numbers.png"));
        highlightset = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Resource/highlight.png"));

        BattleMap bMap = new BattleMap();
        bMap.generateField();
        convMap2 = bMap.getFieldType();

        combatPrepare(hero1, hero2);

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
        UnitImg unitImg[] = new UnitImg[units.size()];
        for(int i=0; i<units.size(); i++) {
            switch(units.get(i).getType()) {
                case 1: if (units.get(i).getPlayerHave() == 0) unitImg[i] = UnitImg.BL_SOLD1;
                    else unitImg[i] = UnitImg.RD_SOLD1;
                    break;
                case 2: if (units.get(i).getPlayerHave() == 0) unitImg[i] = UnitImg.BL_ARCH1;
                else unitImg[i] = UnitImg.RD_ARCH1;
                    break;
                case 3: if (units.get(i).getPlayerHave() == 0) unitImg[i] = UnitImg.BL_COLO1;
                else unitImg[i] = UnitImg.RD_COLO1;
                    break;
                case 4: if (units.get(i).getPlayerHave() == 0) unitImg[i] = UnitImg.BL_CATA1;
                else unitImg[i] = UnitImg.RD_CATA1;
                    break;
                case 5: if (units.get(i).getPlayerHave() == 0) unitImg[i] = UnitImg.BL_DRAG1;
                else unitImg[i] = UnitImg.RD_DRAG1;
                    break;
            }
            int tempX = units.get(i).getPosx() * tW;
            int tempY = units.get(i).getPosy() * tH;
            drawUnit(grph, unitImg[i], tempX, tempY);
            if(units.get(i).getCount()/1000 >= 1) {
                drawNumber(grph, numbers[units.get(i).getCount()/1000], tempX, tempY + 48);
                drawNumber(grph, numbers[units.get(i).getCount()%1000/100], tempX + 16, tempY + 48);
                drawNumber(grph, numbers[units.get(i).getCount()%100/10], tempX + 32, tempY + 48);
                drawNumber(grph, numbers[units.get(i).getCount()%10], tempX + 48, tempY + 48);
            } else if(units.get(i).getCount()/100 >= 1) {
                drawNumber(grph, numbers[units.get(i).getCount()/100], tempX + 16, tempY + 48);
                drawNumber(grph, numbers[units.get(i).getCount()%100/10], tempX + 32, tempY + 48);
                drawNumber(grph, numbers[units.get(i).getCount()%10], tempX + 48, tempY + 48);

            } else if(units.get(i).getCount()/10 >= 1) {
                drawNumber(grph, numbers[units.get(i).getCount()/10], tempX + 32, tempY + 48);
                drawNumber(grph, numbers[units.get(i).getCount()%10], tempX + 48, tempY + 48);

            } else {
                drawNumber(grph, numbers[units.get(i).getCount()], tempX + 48, tempY + 48);
            }
        }
        cleanhigh();
        drawhigh(units.get(flag).getPosx() * tW, units.get(flag).getPosy() * tH, units.get(flag).getSpeed());
        drawPath(grph);
        g.drawImage(grphImage, 0, 0, this);
    }

    public void cleanhigh() {
        for(int i=0; i<20; i++) {
            for(int j=0; j<10; j++) {
                highMap[i][j] = false;
            }
        }
    }

    protected void drawhigh(int x, int y, int moves) {
        int tempX = x/64;
        int tempY = y/64;
        if ((x >= 1280) || (y >= 640) || (x < 0) || (y < 0)) {return;}
        if ((map2[tempX][tempY] == Tile.MOUNT) || (moves < 1)) {return;}
        if ((map2[tempX][tempY] == Tile.HILL1) || (map2[tempX][tempY] == Tile.HILL2)) {
            moves--;
            moves--;
        } else {
            moves--;
        }
        highMap[tempX][tempY] = true;
        drawhigh(x-64, y, moves);
        drawhigh(x+64, y, moves);
        drawhigh(x, y-64, moves);
        drawhigh(x, y+64, moves);
    }

    protected void drawPath(Graphics g) {
        for(int i=0; i<20; i++) {
            for(int j=0; j<10; j++) {
                int x = i * tW;
                int y = j * tH;
                if (highMap[i][j] == true) {
                    g.drawImage(highlightset, x, y, x + tW, y + tH, 0, 0, tW, tH, this);
                }
            }
        }
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

    protected void drawNumber(Graphics g, Numbers n, int x, int y) {
        int numX = 16;
        int numY = 16;
        int mx = n.ordinal() % 10;
        int my = n.ordinal() / 10;
        g.drawImage(numberset, x, y, x + numX, y + numY, mx * numX, my * numY, mx * numX + numX, my * numY + numY, this);
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

    public Unit findUnit(int x, int y) {
        for (int i=0; i<units.size(); i++) {
            if ((units.get(i).getPosx() == x) && (units.get(i).getPosy() == y)) {
                return units.get(i);
            }
        }
        return null;
    }

    public void setUnit(int x, int y, Unit newUnit) {
        System.out.println(new Date().getTime());
        for (int i=0; i<units.size(); i++) {
            if ((units.get(i).getPosx() == x) && (units.get(i).getPosy() == y)) {
                units.set(i, newUnit);
                if(units.get(i) == null) {
                    units.remove(i);
                }
                break;
            }
        }

        System.out.println(new Date().getTime());
    }

    @Override
    public void mousePressed(MouseEvent e) {
        posX = e.getX()/64;
        posY = e.getY()/64;
        if ((e.getModifiers()== InputEvent.BUTTON3_MASK) && (findUnit(posX, posY) != null)){
            ui = new UnitInfo(findUnit(posX, posY));
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println(new Date().getTime());
        if (ui != null) {
            ui.disappear();
        }
        if ((e.getModifiers() == InputEvent.BUTTON1_MASK) && (highMap[posX][posY] == true) && ((findUnit(posX, posY) == null) || (findUnit(posX, posY) == units.get(flag)))) {
            units.get(flag).setPosx(posX);
            units.get(flag).setPosy(posY);
            if ((findUnit(posX-1, posY) != null) && (findUnit(posX-1, posY).getPlayerHave() != units.get(flag).getPlayerHave())) {
                setUnit(posX-1, posY, units.get(flag).attack(findUnit(posX-1, posY)));
            } else if ((findUnit(posX+1, posY) != null) && (findUnit(posX+1, posY).getPlayerHave() != units.get(flag).getPlayerHave())) {
                setUnit(posX+1, posY, units.get(flag).attack(findUnit(posX+1, posY)));
            } else if ((findUnit(posX, posY+1) != null) && (findUnit(posX, posY+1).getPlayerHave() != units.get(flag).getPlayerHave())) {
                setUnit(posX, posY+1, units.get(flag).attack(findUnit(posX, posY+1)));
            } else if ((findUnit(posX, posY-1) != null) && (findUnit(posX, posY-1).getPlayerHave() != units.get(flag).getPlayerHave())) {
                setUnit(posX, posY-1, units.get(flag).attack(findUnit(posX, posY-1)));
            } else {

            }
            turn();
        }
        System.out.println(new Date().getTime());
        repaint();
        System.out.println(new Date().getTime());
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
    }

    public void combatPrepare(Hero player1, Hero player2) {
        System.out.println(new Date().getTime());

        BattleMap bmap = new BattleMap();
        bmap.generateField();
        for (int i = 0; i < player1.getArmy().length; i++) {
            if (player1.getArmy()[i] != null) {
                int tmpdmg = player1.getArmy()[i].getDmg() + player1.getDmgBonus();
                int tmpdef = player1.getArmy()[i].getDef() + player1.getDefBonus();
                int tmpovhp = player1.getArmy()[i].getOvhp() + player1.getHpBonus();
                player1.getArmy()[i].setDmg(tmpdmg);
                player1.getArmy()[i].setDef(tmpdef);
                player1.getArmy()[i].setOvhp(tmpovhp);
                player1.getArmy()[i].setHp(player1.getArmy()[i].getOvhp());
                player1.getArmy()[i].setPlayerHave(0);
                player1.getArmy()[i].setPosx(0);
                player1.getArmy()[i].setPosy(i * 2);
                units.add(player1.getArmy()[i]);
            }
        }
        for (int i = 0; i < player2.getArmy().length; i++) {
            if (player2.getArmy()[i] != null) {
                int tmpdmg = player2.getArmy()[i].getDmg() + player2.getDmgBonus();
                int tmpdef = player2.getArmy()[i].getDef() + player2.getDefBonus();
                int tmpovhp = player2.getArmy()[i].getOvhp() + player2.getHpBonus();
                player2.getArmy()[i].setDmg(tmpdmg);
                player2.getArmy()[i].setDef(tmpdef);
                player2.getArmy()[i].setOvhp(tmpovhp);
                player2.getArmy()[i].setHp(player2.getArmy()[i].getOvhp());
                player2.getArmy()[i].setPlayerHave(1);
                player2.getArmy()[i].setPosx(19);
                player2.getArmy()[i].setPosy(i * 2);
                units.add(player2.getArmy()[i]);
            }
        }
        Collections.sort(units, new CompareUnits());
        System.out.println(new Date().getTime());

    }

    public void turn() {
        System.out.println(new Date().getTime());

        flag++;
        if (flag >= units.size()) flag = 0;
        for(int i=0; i<5; i++) {
            if ((hero1.getArmy()[i] != null) && (hero1.getArmy()[i].getCount() <= 0)) {
                hero1.getArmy()[i] = null;
            }
            if ((hero2.getArmy()[i] != null) && (hero2.getArmy()[i].getCount() <= 0)) {
                hero2.getArmy()[i] = null;
            }
        }
        int temp = 0;
        for(int i=0; i<5; i++) {
            if (hero1.getArmy()[i] == null) temp++;
            if (temp == 5) {
                f.dispose();
            }
        }
        temp = 0;
        for(int i=0; i<5; i++) {
            if (hero2.getArmy()[i] == null) temp++;
            if (temp == 5) {
                f.dispose();
            }
        }
        System.out.println(new Date().getTime());

    }

    class CompareUnits implements Comparator<Unit> {

        public int compare(Unit o1, Unit o2) {
            if (o1.getSpeed() < o2.getSpeed()) return 1;
            else if (o1.getSpeed() == o2.getSpeed()) return 0;
            else return -1;
        }
    }
}
