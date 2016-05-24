package ip4323.hommClass;

/**
 * Created by dima_ on 24.05.2016.
 */
public class HeroToHeroCommand implements Command {
    private Map workingMap;
    private int flagHeroActive;
    private int posX;
    private int posY;

    public HeroToHeroCommand(Map workingMap, int flagHeroActive, int posX, int posY) {
        this.workingMap = workingMap;
        this.flagHeroActive = flagHeroActive;
        this.posX = posX;
        this.posY = posY;
    }

    @Override
    public void execute() {
        HeroToHeroFieldWindow hth = new HeroToHeroFieldWindow("herotohero", workingMap.getMapHero().get(flagHeroActive), workingMap.getMapHero().get(workingMap.findHero(posX, posY)));
    }
}
