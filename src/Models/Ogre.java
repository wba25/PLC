package Models;

public class Ogre extends Demon{
    private int live;

    public Ogre(int x, int y, Sprite target) {
        super(x, y, target);
        this.live = 3;
    }

    @Override
    public void initDemon() {
        loadImage("/home/wellington/Desktop/PLC/Projeto/PLC/src/Assets/Game/Ogre.png");
        getImageDimensions();
    }

    public void decrementLive() {
        this.live--;
    }

    public int getLive() {
        return this.live;
    }
}
